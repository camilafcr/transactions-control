package com.api.transactionscontrol.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.api.transactionscontrol.account.Account;
import com.api.transactionscontrol.account.AccountMapper;
import com.api.transactionscontrol.account.AccountRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {

  @InjectMocks
  private AccountMapper accountMapper;

  @Test
  public void shouldMapAccountRequestToEntity() {
    AccountRequest accountRequest = new AccountRequest();
    accountRequest.setDocumentNumber(123);

    Account account = accountMapper.mapDtoToEntity(accountRequest);

    assertEquals(accountRequest.getDocumentNumber(), account.getDocumentNumber());
  }
}
