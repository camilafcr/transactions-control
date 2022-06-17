package com.api.transactionscontrol.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTransactionException extends RuntimeException {
  public InvalidTransactionException(String message) {
    super("Transaction Invalid. Cause: " + message);
  }
}
