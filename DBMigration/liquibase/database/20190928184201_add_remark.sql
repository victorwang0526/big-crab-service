ALTER TABLE t_card ADD remark VARCHAR(255) NULL;
ALTER TABLE t_card
  MODIFY COLUMN remark VARCHAR(255) AFTER description;