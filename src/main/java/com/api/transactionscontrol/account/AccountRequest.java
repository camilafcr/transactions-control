package com.api.transactionscontrol.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AccountRequest {
  @NotNull(message = "Document number must be not null.")
  @Pattern(regexp = "[0-9]*$", message = "Document number must be a number.")
  private String documentNumber;

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
