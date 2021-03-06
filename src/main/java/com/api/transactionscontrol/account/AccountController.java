package com.api.transactionscontrol.account;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/accounts")
@Api(value = "v1/accounts")
public class AccountController {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AccountMapper accountMapper;

  @ApiOperation(value = "Create an account for a client.")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Account createAccount(@RequestBody @Valid AccountRequest request) {
    return accountRepository.save(accountMapper.mapDtoToEntity(request));
  }

  @ApiOperation(value = "Get an account from a client.")
  @GetMapping("/{id}")
  public Account findAccount(@PathVariable("id") Integer id) {
    return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
  }

  @ExceptionHandler(AccountNotFoundException.class)
  public ResponseEntity<String> exceptionAccountNotFound(
      AccountNotFoundException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
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
}
