package com.api.transactionscontrol.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountRequest {
  @NotNull(message = "Document number must be not null.")
  @Pattern(regexp = "[0-9]*$", message = "Document number must be an integer.")
  @Size(min=1, max=15, message = "Document number must be between 1 and 15 characters.")
  private String documentNumber;

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
