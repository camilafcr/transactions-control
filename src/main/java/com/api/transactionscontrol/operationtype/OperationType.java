package com.api.transactionscontrol.operationtype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operation_type")
public class OperationType {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "operation_type_id")
  private Integer id;

  private String description;

  public Integer getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }
}
