package com.api.transactionscontrol.transaction;

import com.api.transactionscontrol.account.AccountNotFoundException;
import com.api.transactionscontrol.account.InsufficientAvailableLimitCreditException;
import com.api.transactionscontrol.operationtype.OperationTypeNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/transactions")
@Api(value = "v1/transactions")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionMapper transactionMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a new transaction.")
  public Transaction newTransaction(@RequestBody @Valid TransactionRequest request) {
    return transactionService.createTransaction(transactionMapper.mapDtoToEntity(request));
  }

  @ExceptionHandler({AccountNotFoundException.class, OperationTypeNotFoundException.class})
  public ResponseEntity<String> exceptionIdNotFound(Exception exception) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }

  @ExceptionHandler(InvalidTransactionException.class)
  public ResponseEntity<String> exceptionInvalidTransaction(
      InvalidTransactionException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidFormatException.class)
  public String invalidFormatException() {
    return "Invalid format in attribute of request body.";
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationException(MethodArgumentNotValidException exception) {
    Map<String, String> exceptions = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach(error -> {
      String columnName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      exceptions.put(columnName, errorMessage);
    });

    return exceptions;
  }

  @ExceptionHandler(InsufficientAvailableLimitCreditException.class)
  public String insufficientAvailableLimitCreditException(
      InsufficientAvailableLimitCreditException exception
  ) {
    return exception.getMessage();
  }
}
