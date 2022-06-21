package com.api.transactionscontrol.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientAvailableLimitCreditException extends RuntimeException {

  public InsufficientAvailableLimitCreditException() {
    super("Available limit credit is insufficient.");
  }
}
