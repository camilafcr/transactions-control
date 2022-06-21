package com.api.transactionscontrol.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.transactionscontrol.account.Account;
import com.api.transactionscontrol.account.AccountRepository;
import com.api.transactionscontrol.account.AccountService;
import com.api.transactionscontrol.operationtype.OperationType;
import com.api.transactionscontrol.operationtype.OperationTypeEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

  @InjectMocks
  private TransactionService transactionService;

  @Mock
  private AccountService accountService;

  @Mock
  private TransactionRepository transactionRepository;

  @Test
  public void shouldCreateTransaction() {
    OperationType operationType = new OperationType();
    operationType.setId(OperationTypeEnum.SAQUE.getOperationTypeId());
    operationType.setDescription(OperationTypeEnum.SAQUE.name());

    Account account = new Account();
    account.setDocumentNumber("123");
    account.setId(1);
    account.setAvailableCreditLimit(BigDecimal.valueOf(100));

    Transaction transaction = buildTransaction(operationType, BigDecimal.TEN);

    when(accountService.updateAvailableCreditLimit(any(), any())).thenReturn(account);
    when(transactionRepository.save(any())).thenReturn(transaction);

    transactionService.createTransaction(transaction);

    verify(accountService, times(1)).updateAvailableCreditLimit(any(), any());
    verify(transactionRepository, times(1)).save(any());
  }

  @Test
  public void shouldThrowInvalidTransactionExceptionWhenAmountIsEqualZero() {
    OperationType operationType = new OperationType();
    operationType.setId(OperationTypeEnum.PAGAMENTO.getOperationTypeId());
    operationType.setDescription(OperationTypeEnum.PAGAMENTO.name());

    InvalidTransactionException exception = assertThrows(
        InvalidTransactionException.class, () -> transactionService
            .createTransaction(buildTransaction(operationType, BigDecimal.ZERO)));

    assertEquals("Transaction Invalid. Cause: Amount must be different from '0'.",
        exception.getMessage());
  }

  @Test
  public void shouldThrowInvalidTransactionExceptionWhenOperationIsNotPositive() {
    OperationType operationType = new OperationType();
    operationType.setId(OperationTypeEnum.PAGAMENTO.getOperationTypeId());
    operationType.setDescription(OperationTypeEnum.PAGAMENTO.name());

    InvalidTransactionException exception = assertThrows(
        InvalidTransactionException.class, () -> transactionService
            .createTransaction(buildTransaction(operationType, BigDecimal.valueOf(-50))));

    assertEquals("Transaction Invalid. Cause: OperationType is PAGAMENTO, amount must be positive.",
        exception.getMessage());
  }

  @Test
  public void shouldThrowInvalidTransactionExceptionWhenOperationIsNotNegative() {
    OperationType operationType = new OperationType();
    operationType.setId(OperationTypeEnum.SAQUE.getOperationTypeId());
    operationType.setDescription(OperationTypeEnum.SAQUE.name());

    InvalidTransactionException exception = assertThrows(
        InvalidTransactionException.class, () -> transactionService
            .createTransaction(buildTransaction(operationType, BigDecimal.valueOf(50))));

    assertEquals("Transaction Invalid. Cause: OperationType is SAQUE, amount must be negative.",
        exception.getMessage());
  }

  private Transaction buildTransaction(OperationType operationType, BigDecimal amount) {

    Account account = new Account();
    account.setDocumentNumber("123");
    account.setId(1);
    account.setAvailableCreditLimit(BigDecimal.valueOf(100));

    Transaction transaction = new Transaction();
    transaction.setOperationType(operationType);
    transaction.setAccount(account);
    transaction.setEventDate(LocalDateTime.now());
    transaction.setAmount(amount);

    return transaction;
  }
}
