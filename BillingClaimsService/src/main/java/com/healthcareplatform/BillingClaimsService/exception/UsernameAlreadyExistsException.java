package com.healthcareplatform.BillingClaimsService.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String username) {
    super("Username '%s' is already in use.".formatted(username));
  }
}