-- create carrot table
CREATE TABLE carrot.carrot
(
  id VARCHAR(32) PRIMARY KEY,
  name VARCHAR(32),
  price INT,
  weight INT,
  created_at TIMESTAMP,
  last_modified_at TIMESTAMP,
  rvn INT
);