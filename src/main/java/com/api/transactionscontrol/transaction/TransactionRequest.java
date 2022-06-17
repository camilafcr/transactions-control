package com.api.transactionscontrol.transaction;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

public class TransactionRequest {

  @NotNull
  private Integer accountId;

  @NotNull
  private Integer operationTypeId;

  @NotNull
  private BigDecimal amount;

  public Integer getAccountId() {
    return accountId;
  }

  public Integer getOperationTypeId() {
    return operationTypeId;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
