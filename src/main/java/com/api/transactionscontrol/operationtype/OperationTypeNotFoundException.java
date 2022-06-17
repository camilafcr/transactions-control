package com.api.transactionscontrol.operationtype;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OperationTypeNotFoundException extends RuntimeException {

  public OperationTypeNotFoundException(Integer id) {
    super("Operation type " + id  + " not found");
  }
}
