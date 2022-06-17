package com.api.transactionscontrol.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.api.transactionscontrol.account.Account;
import com.api.transactionscontrol.account.AccountNotFoundException;
import com.api.transactionscontrol.account.AccountRepository;
import com.api.transactionscontrol.operationtype.OperationType;
import com.api.transactionscontrol.operationtype.OperationTypeEnum;
import com.api.transactionscontrol.operationtype.OperationTypeNotFoundException;
import com.api.transactionscontrol.operationtype.OperationTypeRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private OperationTypeRepository operationTypeRepository;

  @InjectMocks
  private TransactionMapper transactionMapper;

  @Test
  public void shouldMapTransactionDtoToEntity() {
    Account account = new Account();
    account.setId(1);
    account.setDocumentNumber(123);

    OperationType operationType = new OperationType();
    operationType.setId(OperationTypeEnum.PAGAMENTO.getOperationTypeId());
    operationType.setDescription(OperationTypeEnum.PAGAMENTO.name());

    when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
    when(operationTypeRepository.findById(anyInt())).thenReturn(Optional.of(operationType));

    Transaction transaction = transactionMapper.mapDtoToEntity(buildRequest());

    assertEquals(BigDecimal.TEN, transaction.getAmount());
    assertEquals(account.getId(), transaction.getAccount().getId());
    assertEquals(account.getDocumentNumber(), transaction.getAccount().getDocumentNumber());
    assertEquals(OperationTypeEnum.PAGAMENTO.getOperationTypeId(),
        transaction.getOperationType().getId());
    assertEquals(OperationTypeEnum.PAGAMENTO.name(),
        transaction.getOperationType().getDescription());

  }

  @Test
  public void shouldNotMapWhenAccountNotFound() {
    when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

    AccountNotFoundException accountNotFoundException = assertThrows(AccountNotFoundException.class,
        () -> transactionMapper.mapDtoToEntity(buildRequest()));

    assertEquals("Account 1 not found", accountNotFoundException.getMessage());
  }

  @Test
  public void shouldNotMapWhenOperationTypeNotFound() {
    when(accountRepository.findById(anyInt())).thenReturn(Optional.of(new Account()));
    when(operationTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

    OperationTypeNotFoundException operationTypeNotFoundException = assertThrows(
        OperationTypeNotFoundException.class,
        () -> transactionMapper.mapDtoToEntity(buildRequest()));

    assertEquals("Operation type 4 not found", operationTypeNotFoundException.getMessage());
  }

  private TransactionRequest buildRequest() {
    TransactionRequest request = new TransactionRequest();
    request.setAmount(BigDecimal.TEN);
    request.setAccountId(1);
    request.setOperationTypeId(OperationTypeEnum.PAGAMENTO.getOperationTypeId());

    return request;
  }
}
