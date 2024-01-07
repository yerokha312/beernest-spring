CREATE TABLE beer_description
(
    beer_description_id bigserial,
    description         varchar(255),
    PRIMARY KEY (beer_description_id)
);

CREATE TABLE brand
(
    brand_id bigserial,
    name     varchar(255),
    PRIMARY KEY (brand_id),
    UNIQUE (name)
);

CREATE TABLE customer
(
    id         bigserial,
    first_name varchar(255),
    last_name  varchar(255),
    dob        date,
    email      varchar(255),
    phone      varchar(255),
    password   varchar(255),
    active     boolean DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (phone)
);

CREATE TABLE employee
(
    id         bigserial,
    first_name varchar(255),
    last_name  varchar(255),
    dob        date,
    email      varchar(255),
    phone      varchar(255),
    password   varchar(255),
    active     boolean DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (phone)
);

CREATE TABLE orders
(
    order_id      bigserial,
    creation_date date,
    customer_id   bigint,
    employee_id   bigint,
    total_price   numeric(38, 2),
    status        varchar(255),
    PRIMARY KEY (order_id),
    UNIQUE (customer_id),
    UNIQUE (employee_id),
    CONSTRAINT fk624gtjin3po807j3vix093tlf
        FOREIGN KEY (customer_id) REFERENCES customer,
    CONSTRAINT fkog5v9ga2g2ukytypn4ocip6b4
        FOREIGN KEY (employee_id) REFERENCES employee
);

CREATE TABLE substyle
(
    substyle_id bigserial,
    style       varchar(20),
    name        varchar(255),
    PRIMARY KEY (substyle_id),
    UNIQUE (name)
);

CREATE TABLE beer
(
    beer_id        bigserial,
    beer_code      varchar(20),
    name           varchar(255),
    style          varchar(20),
    substyle_id    bigint,
    brand_id       bigint,
    alcohol        double precision,
    container      varchar(20),
    size           integer,
    purchase_price numeric(10, 2),
    selling_price  numeric(10, 2),
    country        varchar(255),
    description_id bigint,
    sold_amount    bigint,
    stock_amount   integer,
    PRIMARY KEY (beer_id),
    UNIQUE (description_id),
    UNIQUE (beer_code),
    CONSTRAINT fk65rhv8kyel4a8ekerdc6cw4nc
        FOREIGN KEY (description_id) REFERENCES beer_description,
    CONSTRAINT fkd9t6c85c90sicbgpnsdip3ifa
        FOREIGN KEY (brand_id) REFERENCES brand,
    CONSTRAINT fkcq60efkmr5lh0xc57h7qbj1ga
        FOREIGN KEY (substyle_id) REFERENCES substyle
);

CREATE TABLE order_item
(
    order_item_id bigserial,
    order_id      bigint,
    beer_id       bigint,
    quantity      integer,
    PRIMARY KEY (order_item_id),
    UNIQUE (beer_id),
    CONSTRAINT fke6tx11auahxtxajs3kal2c42
        FOREIGN KEY (beer_id) REFERENCES beer,
    CONSTRAINT fkt4dc2r9nbvbujrljv3e23iibt
        FOREIGN KEY (order_id) REFERENCES orders
);

