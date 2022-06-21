package com.api.transactionscontrol.account;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountRequest {
  @NotNull(message = "Document number must not be null.")
  @Pattern(regexp = "[0-9]*$", message = "Document number must be an integer.")
  @Size(min=1, max=15, message = "Document number must be between 1 and 15 characters.")
  private String documentNumber;

  @NotNull(message = "Available credit limit must not be null.")
  private BigDecimal availableCreditLimit;

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public BigDecimal getAvailableCreditLimit() {
    return availableCreditLimit;
  }

  public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
    this.availableCreditLimit = availableCreditLimit;
  }
}
