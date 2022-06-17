package com.api.transactionscontrol.account;

import javax.validation.constraints.NotNull;

public class AccountRequest {
  @NotNull
  private Integer documentNumber;

  public Integer getDocumentNumber() {
    return documentNumber;
  }
}
