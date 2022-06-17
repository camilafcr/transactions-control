package com.api.transactionscontrol.transaction;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/transactions" )
@Api(value="v1/transactions")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionMapper transactionMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value="Create a new transaction.")
  public Transaction newTransaction(@RequestBody @Valid TransactionRequest request) {
    return transactionService.createTransaction(transactionMapper.mapDtoToEntity(request));
  }

  @ExceptionHandler
  public ResponseEntity<String> exceptionOperationTypeNotFound(Exception exception) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<String> exceptionInvalidTransaction(
      InvalidTransactionException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
  }
}
