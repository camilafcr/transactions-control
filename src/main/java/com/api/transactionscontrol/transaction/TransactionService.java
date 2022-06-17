package com.api.transactionscontrol.transaction;

import com.api.transactionscontrol.operationtype.OperationType;
import com.api.transactionscontrol.operationtype.OperationTypeEnum;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  public Transaction createTransaction(Transaction transaction) {
    checkTransaction(transaction.getOperationType(), transaction.getAmount());

    return transactionRepository.save(transaction);
  }

  private void checkTransaction(OperationType operationType, BigDecimal amount) {
    if (operationType.getId().equals(OperationTypeEnum.PAGAMENTO.getOperationTypeId())) {
      if (amount.compareTo(BigDecimal.ZERO) != 1) {
        throw new InvalidTransactionException(
            "OperationType is " + OperationTypeEnum.PAGAMENTO.name()
                + ", amount must be positive." );
      }
    } else {
      if (amount.compareTo(BigDecimal.ZERO) != -1) {
        throw new InvalidTransactionException(
            "OperationType is " + getOperationTypeName(operationType.getId())
                + ", amount must be negative." );
      }
    }
  }

  private String getOperationTypeName(Integer operationTypeId) {
    if (operationTypeId.equals(OperationTypeEnum.COMPRA_A_VISTA.getOperationTypeId())) {
      return OperationTypeEnum.COMPRA_A_VISTA.name();
    } else if (operationTypeId.equals(OperationTypeEnum.COMPRA_PARCELADA.getOperationTypeId())) {
      return OperationTypeEnum.COMPRA_PARCELADA.name();
    } else {
      return OperationTypeEnum.SAQUE.name();
    }
  }
}
