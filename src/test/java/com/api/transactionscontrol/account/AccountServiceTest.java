package com.api.transactionscontrol.account;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.api.transactionscontrol.operationtype.OperationType;
import com.api.transactionscontrol.operationtype.OperationTypeEnum;
import com.api.transactionscontrol.transaction.Transaction;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

  @InjectMocks
  private AccountService accountService;

  @Mock
  private AccountRepository accountRepository;

  @Test
  public void shouldThrowInsufficientAvailableLimitCreditException() {
    Account account = new Account();
    account.setDocumentNumber("123");
    account.setId(1);
    account.setAvailableCreditLimit(BigDecimal.valueOf(1));

    Assert.assertThrows(InsufficientAvailableLimitCreditException.class,
        () -> accountService.updateAvailableCreditLimit(BigDecimal.TEN, account));

  }

  @Test
  public void shouldUpdateAvailableCreditLimit() {
    Account account = new Account();
    account.setDocumentNumber("123");
    account.setId(1);
    account.setAvailableCreditLimit(BigDecimal.valueOf(100));

    when(accountRepository.save(any())).thenReturn(account);

    accountService.updateAvailableCreditLimit(BigDecimal.TEN, account);

  }
}
