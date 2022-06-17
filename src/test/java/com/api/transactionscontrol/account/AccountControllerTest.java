package com.api.transactionscontrol.account;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.transactionscontrol.account.Account;
import com.api.transactionscontrol.account.AccountController;
import com.api.transactionscontrol.account.AccountMapper;
import com.api.transactionscontrol.account.AccountRepository;
import com.api.transactionscontrol.account.AccountRequest;
import com.api.transactionscontrol.transaction.TransactionController;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AccountController accountController;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AccountRepository accountRepository;

  @MockBean
  private AccountMapper accountMapper;

  @Test
  public void shouldCreateNewAccount() throws Exception {
    AccountRequest request = new AccountRequest();
    request.setDocumentNumber(123);

    when(accountRepository.save(ArgumentMatchers.any())).thenReturn(new Account());

    mockMvc.perform(MockMvcRequestBuilders.post("/v1/accounts" )
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldFindAccount() throws Exception {
    Account account = new Account();
    account.setId(1);
    account.setDocumentNumber(123);

    when(accountRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(account));

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/1" )
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{'id': 1,'documentNumber': 123}" ));
  }

  @Test
  public void shouldNotFindAccount() throws Exception {
    when(accountRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Account 1 not found"));
  }

}
