package com.healthcareplatform.BillingClaimsService.invoice;

import com.healthcareplatform.BillingClaimsService.dto.InvoiceItemResponse;
import com.healthcareplatform.BillingClaimsService.dto.InvoiceRequest;
import com.healthcareplatform.BillingClaimsService.dto.InvoiceResponse;
import com.healthcareplatform.BillingClaimsService.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Retrieve all invoices
     */
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a single invoice by ID
     */
    @Transactional(readOnly = true)
    public InvoiceResponse getInvoiceById(Long id) {
        Invoice inv = findInvoice(id);
        return mapToDto(inv);
    }

    /**
     * Create a new invoice with items
     */
    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest req) {
        Invoice invoice = Invoice.builder()
                .patientId(req.getPatientId())
                .dateIssued(req.getDateIssued())
                .status(req.getStatus() != null ? req.getStatus() : "PENDING")
                .build();

        // add items
        req.getItems().forEach(dto -> {
            InvoiceItem item = InvoiceItem.builder()
                    .description(dto.getDescription())
                    .amount(dto.getAmount())
                    .build();
            invoice.addItem(item);
        });

        // calculate total
        BigDecimal total = invoice.getItems().stream()
                .map(InvoiceItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        invoice.setTotalAmount(total);

        Invoice saved = invoiceRepository.save(invoice);
        return mapToDto(saved);
    }

    /**
     * Update invoice status
     */
    @Transactional
    public InvoiceResponse updateStatus(Long id, String status) {
        Invoice inv = findInvoice(id);
        inv.setStatus(status);
        Invoice saved = invoiceRepository.save(inv);
        return mapToDto(saved);
    }

    /**
     * Delete invoice by ID
     */
    @Transactional
    public void deleteInvoice(Long id) {
        Invoice inv = findInvoice(id);
        invoiceRepository.delete(inv);
    }

    /**
     * Helper to find or throw
     */
    private Invoice findInvoice(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
    }

    /**
     * Map entity to DTO
     */
    private InvoiceResponse mapToDto(Invoice inv) {
        List<InvoiceItemResponse> items = inv.getItems().stream()
                .map(i -> new InvoiceItemResponse(i.getDescription(), i.getAmount()))
                .collect(Collectors.toList());

        return new InvoiceResponse(
                inv.getId(),
                inv.getPatientId(),
                inv.getDateIssued(),
                inv.getTotalAmount(),
                inv.getStatus(),
                items
        );
    }
}

