package com.healthcareplatform.BillingClaimsService.controller;

import com.healthcareplatform.BillingClaimsService.dto.ClaimDto;
import com.healthcareplatform.BillingClaimsService.dto.InvoiceDto;
import com.healthcareplatform.BillingClaimsService.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
/**
 * REST controller for managing invoices and insurance claims.
 */
@RestController
@RequestMapping("/api/v1/billing")
public class BillingClaimsController {

    private final BillingService billingService;

    public BillingClaimsController(BillingService billingService) {
        this.billingService = billingService;
    }

    /**
     * Retrieve a list of all claims.
     *
     * TODO: Delegate to BillingService to retrieve all claims
     *
     * @return ResponseEntity containing a list of ClaimDto objects and HTTP 200 status.
     */
    @GetMapping("/claims")
    public ResponseEntity<List<ClaimDto>> getAllClaims() {
        List<ClaimDto> claims = billingService.getAllClaims();
        return ResponseEntity.ok(claims);
    }

    /**
     * Retrieve details for a specific claim by ID.
     *
     * TODO: Delegate to BillingService to fetch claim by ID
     *
     * @param claimId Unique identifier of the claim (path variable)
     * @return ResponseEntity containing ClaimDto and HTTP 200 status if found;
     *         otherwise exception is propagated.
     */
    @GetMapping("/claims/{claimId}")
    public ResponseEntity<ClaimDto> getClaimById(@PathVariable UUID claimId) {
        ClaimDto claim = billingService.getClaimById(claimId);
        return ResponseEntity.ok(claim);
    }

    /**
     * Submit a new insurance claim.
     *
     * TODO: Delegate to BillingService to submit a claim
     *
     * @param claim Payload containing claim data (validated request body)
     * @return ResponseEntity containing created ClaimDto and HTTP 201 status.
     */
    @PostMapping("/claims/{claimId}/submit")
    public ResponseEntity<ClaimDto> submitClaim(@Valid @RequestBody ClaimDto claim, @PathVariable UUID claimId) {
        ClaimDto created = billingService.submitClaim(claim, claimId);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Deny an existing claim by ID.
     *
     * TODO: Delegate to BillingService to deny the claim
     *
     * @param claimId Unique identifier of the claim (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful denial.
     */
    @PostMapping("/claims/{claimId}/deny")
    public ResponseEntity<Void> denyClaim(@PathVariable UUID claimId) {
        billingService.denyClaim(claimId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve a list of all invoices.
     *
     * TODO: Delegate to BillingService to retrieve all invoices
     *
     * @return ResponseEntity containing a list of InvoiceDto objects and HTTP 200 status.
     */
    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<InvoiceDto> invoices = billingService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    /**
     * Retrieve details for a specific invoice by ID.
     *
     * TODO: Delegate to BillingService to fetch claim by ID
     *
     * @param invoiceId Unique identifier of the invoice (path variable)
     * @return ResponseEntity containing InvoiceDto and HTTP 200 status if found;
     *         otherwise exception is propagated.
     */
    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesById(@PathVariable UUID invoiceId) {
        List<InvoiceDto> invoices = billingService.getInvoicesById(invoiceId);
        return ResponseEntity.ok(invoices);
    }

    /**
     * Create a new invoice.
     *
     * TODO: Delegate to BillingService to generate a new invoice
     *
     * @param invoice Payload containing invoice data (validated request body)
     * @return ResponseEntity containing created InvoiceDto and HTTP 201 status.
     */
    @PostMapping("/invoices")
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto invoice) {
        InvoiceDto created = billingService.createInvoice(invoice);
        return ResponseEntity.status(201).body(created);
    }
}
