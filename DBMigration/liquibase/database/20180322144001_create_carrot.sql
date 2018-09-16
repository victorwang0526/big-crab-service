--
-- create database bigcrab default charset utf8 collate utf8_general_ci;

-- create user 'bigcrab'@'%' identified by 'bigcrab';

-- grant all on bigcrab.* to 'bigcrab'@'%';

-- flush privileges;

CREATE TABLE t_user
(
    id NVARCHAR(32) PRIMARY KEY,
    login_name NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    created_at DATETIME,
    last_modified_at DATETIME,
    last_login_at DATETIME,
    last_login_ip DATETIME
);

-- admin:admin123
insert into t_user(id, login_name, password, created_at)
VALUES ('160afeaf15064e42874d62b1ab325079', 'admin', '0192023A7BBD73250516F069DF18B500', now());


-- 登录session表
create table t_login_session(
  id NVARCHAR(32) primary key,
  user_id NVARCHAR(32) not null,
  token NVARCHAR(32) not null,

  CREATED_AT datetime,
  LAST_MODIFIED_AT datetime,
  RVN int
);

CREATE TABLE t_card
(
    id NVARCHAR(32) NOT NULL,
    card_number NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    status NVARCHAR(32) NOT NULL,
    last_error_at DATETIME,
    error_times INT,
    redeem_at DATETIME,
    deliver_at DATETIME,
    created_at DATETIME,
    last_modified_at DATETIME
);
CREATE UNIQUE INDEX t_card_id_uindex ON t_card (id);
CREATE UNIQUE INDEX t_card_card_number_uindex ON t_card (card_number);
--
-- CREATE TABLE t_deliver
-- (
--     id NVARCHAR(32) PRIMARY KEY NOT NULL,
--     card_number NVARCHAR(255) NOT NULL,
--     province NVARCHAR(255),
--     city NVARCHAR(255),
--     street NVARCHAR(255),
--     address NVARCHAR(255),
--     status NVARCHAR(32),
--     deliver_at DATETIME,
--     real_deliver_at DATETIME,
--     created_at DATETIME,
--     last_modified_at DATETIME
-- );
-- CREATE UNIQUE INDEX t_deliver_id_uindex ON t_deliver (id);