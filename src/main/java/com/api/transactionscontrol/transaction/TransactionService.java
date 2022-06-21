package com.api.transactionscontrol.transaction;

import com.api.transactionscontrol.account.AccountService;
import com.api.transactionscontrol.operationtype.OperationType;
import com.api.transactionscontrol.operationtype.OperationTypeEnum;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountService accountService;

  public Transaction createTransaction(Transaction transaction) {
    checkTransaction(transaction.getAmount());

    if (!transaction.getOperationType().getId()
        .equals(OperationTypeEnum.PAGAMENTO.getOperationTypeId())) {
      transaction.setAmount(transaction.getAmount().multiply(BigDecimal.valueOf(-1)));
    }

    accountService.updateAvailableCreditLimit(transaction.getAmount().abs(), transaction.getAccount());
    return transactionRepository.save(transaction);
  }

  private void checkTransaction(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) == 0) {
      throw new InvalidTransactionException("Amount must be different from '0'.");
    }

    /*if (operationType.getId().equals(OperationTypeEnum.PAGAMENTO.getOperationTypeId())) {
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
    }*/
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
