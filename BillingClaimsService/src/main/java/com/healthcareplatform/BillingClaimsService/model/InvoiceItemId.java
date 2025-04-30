package com.healthcareplatform.BillingClaimsService.model;


import java.io.Serializable;
import java.util.UUID;

public class InvoiceItemId implements Serializable {
    private UUID invoiceId;
    private String description;

}