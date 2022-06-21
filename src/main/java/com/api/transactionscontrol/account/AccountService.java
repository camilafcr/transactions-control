package com.api.transactionscontrol.account;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  public Account updateAvailableCreditLimit(BigDecimal amount, Account account) {

    if (!checkAvailableLimit(amount, account)) {
      throw new InsufficientAvailableLimitCreditException();
    }

    account.setAvailableCreditLimit(account.getAvailableCreditLimit().subtract(amount));

    return accountRepository.save(account);
  }

  private boolean checkAvailableLimit(BigDecimal amount, Account account) {
    if (account.getAvailableCreditLimit().compareTo(amount) == -1) {
      return false;
    }

    return true;
  }
}
