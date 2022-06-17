package com.api.transactionscontrol.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.api.transactionscontrol.account.Account;
import com.api.transactionscontrol.operationtype.OperationType;
import com.api.transactionscontrol.operationtype.OperationTypeEnum;
import com.api.transactionscontrol.transaction.InvalidTransactionException;
import com.api.transactionscontrol.transaction.Transaction;
import com.api.transactionscontrol.transaction.TransactionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

  @InjectMocks
  private TransactionService transactionService;

  @Test
  public void shouldThrowInvalidTransactionExceptionWhenOperationIsNotPositive() {
    OperationType operationType = new OperationType();
    operationType.setId(OperationTypeEnum.PAGAMENTO.getOperationTypeId());
    operationType.setDescription(OperationTypeEnum.PAGAMENTO.name());

    InvalidTransactionException exception = assertThrows(
        InvalidTransactionException.class, () -> transactionService
            .createTransaction(buildTransaction(operationType, BigDecimal.valueOf(-50))));

    assertEquals("Transaction Invalid. Cause: OperationType is PAGAMENTO, amount must be positive.", exception.getMessage());
  }

  @Test
  public void shouldThrowInvalidTransactionExceptionWhenOperationIsNotNegative() {
    OperationType operationType = new OperationType();
    operationType.setId(OperationTypeEnum.SAQUE.getOperationTypeId());
    operationType.setDescription(OperationTypeEnum.SAQUE.name());

    InvalidTransactionException exception = assertThrows(
        InvalidTransactionException.class, () -> transactionService
            .createTransaction(buildTransaction(operationType, BigDecimal.valueOf(50))));

    assertEquals("Transaction Invalid. Cause: OperationType is SAQUE, amount must be negative.", exception.getMessage());
  }

  private Transaction buildTransaction(OperationType operationType, BigDecimal amount) {

    Account account = new Account();
    account.setDocumentNumber(123);
    account.setId(1);

    Transaction transaction = new Transaction();
    transaction.setOperationType(operationType);
    transaction.setAccount(account);
    transaction.setEventDate(LocalDateTime.now());
    transaction.setAmount(amount);

    return transaction;
  }
}
