package com.healthcareplatform.BillingClaimsService.insuranceClaim;

import com.healthcareplatform.BillingClaimsService.dto.InsuranceClaimRequest;
import com.healthcareplatform.BillingClaimsService.dto.InsuranceClaimResponse;
import com.healthcareplatform.BillingClaimsService.exception.ResourceNotFoundException;
import com.healthcareplatform.BillingClaimsService.invoice.Invoice;
import com.healthcareplatform.BillingClaimsService.invoice.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuranceClaimService {

    @Autowired
    private final InsuranceClaimRepository claimRepository;
    @Autowired
    private final InvoiceRepository invoiceRepository;

    public InsuranceClaimService(
            InsuranceClaimRepository claimRepository,
            InvoiceRepository invoiceRepository
    ) {
        this.claimRepository = claimRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional(readOnly = true)
    public List<InsuranceClaimResponse> getAllClaims() {
        return claimRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InsuranceClaimResponse getClaimById(Long id) {
        return mapToDto(findClaim(id));
    }

    @Transactional
    public InsuranceClaimResponse createClaim(InsuranceClaimRequest req) {
        Invoice invoice = invoiceRepository.findById(req.getInvoiceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not found: " + req.getInvoiceId()));

        InsuranceClaim claim = new InsuranceClaim();
        claim.setInvoice(invoice);
        claim.setInsurer(req.getInsurer());
        claim.setDetails(req.getDetails());
        claim.setClaimStatus("SUBMITTED");
        claim.setSubmittedAt(LocalDateTime.now());

        return mapToDto(claimRepository.save(claim));
    }

    @Transactional
    public InsuranceClaimResponse updateStatus(Long id, String status) {
        InsuranceClaim claim = findClaim(id);
        claim.setClaimStatus(status);
        return mapToDto(claimRepository.save(claim));
    }

    @Transactional
    public void deleteClaim(Long id) {
        InsuranceClaim claim = findClaim(id);
        claimRepository.delete(claim);
    }

    private InsuranceClaim findClaim(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Claim not found with id: " + id));
    }

    private InsuranceClaimResponse mapToDto(InsuranceClaim claim) {
        return new InsuranceClaimResponse(
                claim.getId(),
                claim.getInvoice().getId(),
                claim.getInsurer(),
                claim.getClaimStatus(),
                claim.getDetails(),
                claim.getSubmittedAt()
        );
    }
}

