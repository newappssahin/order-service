CREATE SCHEMA IF NOT EXISTS orders;

CREATE TABLE if not exists product
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    feature VARCHAR(255) NOT NULL
);

CREATE TABLE if not exists customer_order
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(255) NOT NULL,
    last_name            VARCHAR(255) NOT NULL,
    email                VARCHAR(100) NOT NULL,
    phone_number         VARCHAR(100) NOT NULL,
    installation_address VARCHAR(255) NOT NULL,
    installation_date    datetime     NOT NULL,
    product_id           INT          NOT NULL,
    status               VARCHAR(50)  NOT NULL,
    rejection_reason     VARCHAR(255),
    deleted              boolean      NOT NULL,
    created_date         datetime     NOT NULL,
    modified_date        datetime,
    FOREIGN KEY (product_id) REFERENCES product (id)
);