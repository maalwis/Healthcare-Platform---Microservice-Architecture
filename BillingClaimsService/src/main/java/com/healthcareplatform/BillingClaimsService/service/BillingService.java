package com.healthcareplatform.BillingClaimsService.service;

import com.healthcareplatform.BillingClaimsService.dto.ClaimDto;
import com.healthcareplatform.BillingClaimsService.dto.InvoiceDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BillingService {
    public List<ClaimDto> getAllClaims(){
        return null;
    };

    public ClaimDto getClaimById(UUID claimId){
        return null;
    };

    public ClaimDto submitClaim(@Valid ClaimDto claim, UUID claimID){
        return null;
    };

    public void denyClaim(UUID claimId){
        return;
    };

    public List<InvoiceDto> getAllInvoices(){
        return null;
    };

    public InvoiceDto createInvoice(@Valid InvoiceDto invoice){
        return null;
    };

    public List<InvoiceDto> getInvoicesById(UUID invoiceId) {
        return null;
    }
}
