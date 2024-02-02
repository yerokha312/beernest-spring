CREATE TABLE beer_description
(
    beer_description_id IDENTITY PRIMARY KEY,
    description         VARCHAR(255)
);

CREATE TABLE brand
(
    brand_id IDENTITY PRIMARY KEY,
    name     VARCHAR(255),
    UNIQUE (name)
);

CREATE TABLE contact_info
(
    contact_info_id IDENTITY PRIMARY KEY,
    phone_number    VARCHAR(255),
    address         VARCHAR(255),
    is_active       BOOLEAN DEFAULT TRUE
);

CREATE TABLE customer
(
    user_id    IDENTITY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    dob        DATE,
    email      VARCHAR(255),
    password   VARCHAR(255),
    reg_date   TIMESTAMP(6),
    active     BOOLEAN,
    PRIMARY KEY (user_id),
    UNIQUE (email)
);

CREATE TABLE customer_contact_junction
(
    customer_id     BIGINT NOT NULL,
    contact_info_id BIGINT NOT NULL,
    PRIMARY KEY (contact_info_id, customer_id),
    CONSTRAINT fk1exe45la00aorf2vul1wkghh8 FOREIGN KEY (contact_info_id) REFERENCES contact_info,
    CONSTRAINT fk6i86sqgqnrrngokohpxs2fc1f FOREIGN KEY (customer_id) REFERENCES customer
);

CREATE TABLE employee
(
    user_id    IDENTITY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    dob        DATE,
    email      VARCHAR(255),
    password   VARCHAR(255),
    reg_date   TIMESTAMP(6),
    active     BOOLEAN,
    PRIMARY KEY (user_id),
    UNIQUE (email)
);

INSERT INTO employee (user_id, first_name, last_name, dob, email, password, reg_date, active)
VALUES (10000, 'admin', 'test', '2024-01-13', 'admin@test.ru',
        '$2a$10$qQGmKSkxTutAFsG02GseKOLVhwbp/JEMT54bBnWKUc.YyOWHLpeLq', '2024-01-13', true),
       (10001, 'observer', 'test', '2024-01-13', 'observer@test.ru',
        '$2a$10$5UERl8ptShF3doTo27bG2uvVRqLpfUt1N7WDdD9bAzzra4JqfAKbq', '2024-01-13', true);

CREATE TABLE orders
(
    order_id        IDENTITY,
    creation_date   TIMESTAMP(6),
    total_price     NUMERIC(38, 2),
    status          VARCHAR(255),
    customer_id     BIGINT,
    contact_info_id BIGINT,
    delivered       BOOLEAN,
    PRIMARY KEY (order_id),
    CONSTRAINT fkli3ua5uggpx5jjoer07pan8bg FOREIGN KEY (contact_info_id) REFERENCES contact_info,
    CONSTRAINT fk624gtjin3po807j3vix093tlf FOREIGN KEY (customer_id) REFERENCES customer,
    CONSTRAINT orders_status_check CHECK (status IN ('PENDING', 'PAID', 'CANCELED', 'RETURNED'))
);

CREATE TABLE role
(
    role_id   IDENTITY PRIMARY KEY,
    authority VARCHAR(255)
);

INSERT INTO role (role_id, authority)
values (1, 'CUSTOMER'),
       (2, 'OBSERVER'),
       (3, 'ADMIN');

CREATE TABLE customer_role_junction
(
    user_id BIGINT  NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (role_id, user_id),
    CONSTRAINT fkj0b2u85lnckr69tgekgicfsiw FOREIGN KEY (role_id) REFERENCES role,
    CONSTRAINT fk9jk8y1c2brvb2sfslptp11ogi FOREIGN KEY (user_id) REFERENCES customer
);

CREATE TABLE employee_role_junction
(
    user_id BIGINT  NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk7u33y66dsk2sdptyimrkxboxy FOREIGN KEY (role_id) REFERENCES role,
    CONSTRAINT fk6p9dhva3kr8obtfieo6gua2ru FOREIGN KEY (user_id) REFERENCES employee
);

INSERT INTO employee_role_junction (user_id, role_id)
values (10000, 3),
       (10000, 2),
       (10001, 2);

CREATE TABLE sub_style
(
    sub_style_id IDENTITY PRIMARY KEY,
    name         VARCHAR(255),
    style        VARCHAR(255),
    UNIQUE (name),
    CONSTRAINT sub_style_style_check CHECK (style IN ('ALE', 'LAGER', 'HYBRID'))
);

CREATE TABLE beer
(
    beer_id        IDENTITY,
    beer_code      VARCHAR(255),
    name           VARCHAR(255),
    style          VARCHAR(255),
    sub_style_id   BIGINT,
    brand_id       BIGINT,
    alcohol        DOUBLE,
    container      VARCHAR(255),
    size           INTEGER,
    purchase_price NUMERIC(38, 2),
    selling_price  NUMERIC(38, 2),
    country        VARCHAR(255),
    description_id BIGINT,
    sold_amount    BIGINT,
    stock_amount   INTEGER,
    PRIMARY KEY (beer_id),
    UNIQUE (description_id),
    UNIQUE (beer_code),
    CONSTRAINT fk65rhv8kyel4a8ekerdc6cw4nc FOREIGN KEY (description_id) REFERENCES beer_description,
    CONSTRAINT fkd9t6c85c90sicbgpnsdip3ifa FOREIGN KEY (brand_id) REFERENCES brand,
    CONSTRAINT fkcq60efkmr5lh0xc57h7qbj1ga FOREIGN KEY (sub_style_id) REFERENCES sub_style,
    CONSTRAINT beer_container_check CHECK (container IN ('CAN', 'BOTTLE')),
    CONSTRAINT beer_style_check CHECK (style IN ('ALE', 'LAGER', 'HYBRID'))
);

CREATE TABLE order_item
(
    order_item_id IDENTITY,
    order_id      BIGINT,
    beer_id       BIGINT,
    quantity      INTEGER,
    PRIMARY KEY (order_item_id),
    CONSTRAINT fke6tx11auahxtxajs3kal2c42 FOREIGN KEY (beer_id) REFERENCES beer,
    CONSTRAINT fkt4dc2r9nbvbujrljv3e23iibt FOREIGN KEY (order_id) REFERENCES orders
);
