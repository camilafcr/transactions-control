package com.api.transactionscontrol.operationtype;

public enum OperationTypeEnum {
  COMPRA_A_VISTA(1),
  COMPRA_PARCELADA(2),
  SAQUE(3),
  PAGAMENTO(4);

  private int operationTypeId;

  OperationTypeEnum(int operationTypeId) {
    this.operationTypeId = operationTypeId;
  }

  public int getOperationTypeId() {
    return operationTypeId;
  }
}
