package com.healthcareplatform.BillingClaimsService.service;

import com.healthcareplatform.BillingClaimsService.dto.ClaimDto;
import com.healthcareplatform.BillingClaimsService.dto.InvoiceDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface BillingService {
    List<ClaimDto> getAllClaims();

    ClaimDto getClaimById(UUID claimId);

    ClaimDto submitClaim(@Valid ClaimDto claim);

    void denyClaim(UUID claimId);

    List<InvoiceDto> getAllInvoices();

    InvoiceDto createInvoice(@Valid InvoiceDto invoice);
}
