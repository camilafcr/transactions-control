package com.api.transactionscontrol.transaction;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

public class TransactionRequest {

  @NotNull(message = "AccountId must be not null.")
  private Integer accountId;

  @NotNull(message = "OperationTypeId must be not null.")
  private Integer operationTypeId;

  @NotNull(message = "Amount must be not null.")
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

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public void setOperationTypeId(Integer operationTypeId) {
    this.operationTypeId = operationTypeId;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
