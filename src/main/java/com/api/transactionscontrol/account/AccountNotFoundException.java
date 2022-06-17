package com.api.transactionscontrol.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {
  public AccountNotFoundException(Integer id) {
    super("Account "+ id + " not found");
  }
}
