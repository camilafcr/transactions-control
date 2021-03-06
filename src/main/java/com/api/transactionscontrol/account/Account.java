package com.api.transactionscontrol.account;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "account_id")
  private Integer id;
  private String documentNumber;
  @Column(nullable = false)
  private BigDecimal availableCreditLimit;

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public BigDecimal getAvailableCreditLimit() {
    return availableCreditLimit;
  }

  public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
    this.availableCreditLimit = availableCreditLimit;
  }
}
