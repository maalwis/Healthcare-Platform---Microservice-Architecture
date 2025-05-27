package com.healthcareplatform.BillingClaimsService.insuranceClaim;

import com.healthcareplatform.BillingClaimsService.dto.InsuranceClaimRequest;
import com.healthcareplatform.BillingClaimsService.dto.InsuranceClaimResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/billing/claims")
public class InsuranceClaimController {

    private final InsuranceClaimService claimService;

    @Autowired
    public InsuranceClaimController(InsuranceClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public ResponseEntity<List<InsuranceClaimResponse>> getAll() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceClaimResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    @PostMapping
    public ResponseEntity<InsuranceClaimResponse> create(
            @Valid @RequestBody InsuranceClaimRequest req) {
        return ResponseEntity.ok(claimService.createClaim(req));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InsuranceClaimResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(claimService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }
}
