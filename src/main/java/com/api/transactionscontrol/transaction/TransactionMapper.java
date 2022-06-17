package com.api.transactionscontrol.transaction;

import com.api.transactionscontrol.account.AccountNotFoundException;
import com.api.transactionscontrol.account.AccountRepository;
import com.api.transactionscontrol.operationtype.OperationTypeNotFoundException;
import com.api.transactionscontrol.operationtype.OperationTypeRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  @Autowired
  private OperationTypeRepository operationTypeRepository;

  @Autowired
  private AccountRepository accountRepository;

  public Transaction mapDtoToEntity(TransactionRequest request) {
    Transaction transaction = new Transaction();
    transaction.setAmount(request.getAmount());
    transaction.setEventDate(LocalDateTime.now());
    transaction.setAccount(accountRepository.findById(request.getAccountId())
        .orElseThrow(() -> new AccountNotFoundException(request.getAccountId())));
    transaction.setOperationType(operationTypeRepository.findById(request.getOperationTypeId())
        .orElseThrow(() -> new OperationTypeNotFoundException(request.getOperationTypeId())));

    return transaction;
  }
}
