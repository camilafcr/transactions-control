package com.api.transactionscontrol.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.transactionscontrol.account.Account;
import com.api.transactionscontrol.account.AccountController;
import com.api.transactionscontrol.account.AccountMapper;
import com.api.transactionscontrol.account.AccountRepository;
import com.api.transactionscontrol.account.AccountRequest;
import com.api.transactionscontrol.operationtype.OperationType;
import com.api.transactionscontrol.operationtype.OperationTypeEnum;
import com.api.transactionscontrol.operationtype.OperationTypeRepository;
import com.api.transactionscontrol.transaction.InvalidTransactionException;
import com.api.transactionscontrol.transaction.Transaction;
import com.api.transactionscontrol.transaction.TransactionController;
import com.api.transactionscontrol.transaction.TransactionMapper;
import com.api.transactionscontrol.transaction.TransactionRepository;
import com.api.transactionscontrol.transaction.TransactionRequest;
import com.api.transactionscontrol.transaction.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TransactionController transactionController;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TransactionRepository transactionRepository;

  @MockBean
  private TransactionService transactionService;

  @MockBean
  private TransactionMapper transactionMapper;

  @Test
  public void shouldCreateNewTransaction() throws Exception {
    Transaction transaction = buildEntity();

    when(transactionMapper.mapDtoToEntity(ArgumentMatchers.any())).thenReturn(transaction);
    when(transactionRepository.save(ArgumentMatchers.any())).thenReturn(transaction);

    mockMvc.perform(MockMvcRequestBuilders.post("/v1/transactions" )
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(buildRequest())))
        .andExpect(status().isCreated());
  }

  private TransactionRequest buildRequest() {
    TransactionRequest request = new TransactionRequest();
    request.setAmount(BigDecimal.TEN);
    request.setOperationTypeId(OperationTypeEnum.PAGAMENTO.getOperationTypeId());
    request.setAccountId(1);

    return request;
  }

  private Transaction buildEntity() {
    Account account = new Account();
    account.setDocumentNumber(123);
    account.setId(1);

    OperationType operationType = new OperationType();
    operationType.setId(OperationTypeEnum.PAGAMENTO.getOperationTypeId());
    operationType.setDescription(OperationTypeEnum.PAGAMENTO.name());

    Transaction entity = new Transaction();
    entity.setId(1);
    entity.setAmount(BigDecimal.TEN);
    entity.setEventDate(LocalDateTime.now());
    entity.setAccount(account);
    entity.setOperationType(operationType);

    return entity;
  }
}
