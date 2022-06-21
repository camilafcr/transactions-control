CREATE TABLE account
(
  account_id integer NOT NULL PRIMARY KEY,
  document_number varchar(15) NOT NULL,
  available_credit_limit numeric(13,2) DEFAULT 0 NOT NULL
);

CREATE TABLE operation_type
(
  operation_type_id integer NOT NULL PRIMARY KEY,
  description varchar(100) NOT NULL
);

CREATE TABLE transaction
(
  transaction_id integer NOT NULL PRIMARY KEY,
  amount numeric(19,2) NOT NULL,
  event_date timestamp NOT NULL,
  account_id integer NOT NULL,
  operation_type_id integer NOT NULL,
  CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (account_id),
  CONSTRAINT fk_operation_type_id FOREIGN KEY (operation_type_id) REFERENCES operation_type (operation_type_id)
);

INSERT INTO operation_type
SELECT 1, 'COMPRA A VISTA'
 WHERE NOT EXISTS(SELECT 1 FROM operation_type WHERE operation_type_id = 1);

INSERT INTO operation_type
SELECT 2, 'COMPRA PARCELADA'
 WHERE NOT EXISTS(SELECT 1 FROM operation_type WHERE operation_type_id = 2);

INSERT INTO operation_type
SELECT 3, 'SAQUE'
 WHERE NOT EXISTS(SELECT 1 FROM operation_type WHERE operation_type_id = 3);

INSERT INTO operation_type
SELECT 4, 'PAGAMENTO'
 WHERE NOT EXISTS(SELECT 1 FROM operation_type WHERE operation_type_id = 4);

INSERT INTO operation_type
SELECT 5, 'teste camila'
 WHERE NOT EXISTS(SELECT 1 FROM operation_type WHERE operation_type_id = 5);