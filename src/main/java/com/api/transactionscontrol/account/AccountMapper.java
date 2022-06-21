package com.api.transactionscontrol.account;

import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

  public Account mapDtoToEntity(AccountRequest accountRequest) {
    Account account = new Account();
    account.setDocumentNumber(accountRequest.getDocumentNumber());
    account.setAvailableCreditLimit(accountRequest.getAvailableCreditLimit());

    return account;
  }
}
