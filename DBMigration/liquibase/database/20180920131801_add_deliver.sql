
CREATE TABLE t_deliver
(
    id NVARCHAR(32) PRIMARY KEY NOT NULL,

    card_number NVARCHAR(255) NOT NULL,

    order_id NVARCHAR(255) NOT NULL,
    mailno NVARCHAR(255) NOT NULL,
    d_company NVARCHAR(255) NOT NULL,
    d_contact NVARCHAR(255) NOT NULL,
    d_tel NVARCHAR(255) NOT NULL,
    d_province NVARCHAR(255) NOT NULL,
    d_city NVARCHAR(255) NOT NULL,
    d_county NVARCHAR(255) NOT NULL,
    d_address NVARCHAR(255) NOT NULL,

    status NVARCHAR(32),
    deliver_at DATETIME,  -- use expected deliver time
    sendstarttime DATETIME,  -- expect courier arrive time
    real_deliver_at DATETIME,  -- the cargo real deliver time
    received_at DATETIME,  -- receiver receive the cargo time
    created_at DATETIME,   -- can use for redeem at
    last_modified_at DATETIME,
    rvn int
);
CREATE UNIQUE INDEX t_deliver_id_uindex ON t_deliver (id);