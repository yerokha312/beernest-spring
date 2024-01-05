create table beer_description
(
    beer_description_id bigserial,
    description         varchar(255),
    primary key (beer_description_id)
);

create table brand
(
    brand_id bigserial,
    name     varchar(255),
    primary key (brand_id)
);

create table customer
(
    id         bigserial,
    first_name varchar(255),
    last_name  varchar(255),
    dob        date,
    email      varchar(255),
    phone      varchar(255),
    password   varchar(255),
    active     boolean,
    primary key (id),
    unique (email),
    unique (phone)
);

create table employee
(
    id         bigserial,
    first_name varchar(255),
    last_name  varchar(255),
    dob        date,
    email      varchar(255),
    phone      varchar(255),
    password   varchar(255),
    active     boolean,
    primary key (id),
    unique (email),
    unique (phone)
);

create table orders
(
    order_id      bigserial,
    creation_date date,
    total_price   numeric(38, 2),
    customer_id   bigint,
    employee_id   bigint,
    status        varchar(255),
    primary key (order_id),
    unique (customer_id),
    unique (employee_id),
    constraint fk624gtjin3po807j3vix093tlf
        foreign key (customer_id) references customer,
    constraint fkog5v9ga2g2ukytypn4ocip6b4
        foreign key (employee_id) references employee
);

create table style
(
    style_id bigserial,
    name     varchar(255),
    primary key (style_id)
);

create table substyle
(
    substyle_id bigserial,
    style_id    bigint,
    name        varchar(255),
    primary key (substyle_id),
    unique (name),
    constraint fkfqigqqyf3sih7hmrbfmpueb2w
        foreign key (style_id) references style
);

create table beer
(
    beer_id        bigserial,
    name           varchar(255),
    style_id       bigint,
    substyle_id    bigint,
    brand_id       bigint,
    alcohol        double precision,
    container      smallint,
    size           integer,
    country        varchar(255),
    description_id bigint,
    stock_amount   integer,
    primary key (beer_id),
    unique (description_id),
    constraint fkd9t6c85c90sicbgpnsdip3ifa
        foreign key (brand_id) references brand,
    constraint fk65rhv8kyel4a8ekerdc6cw4nc
        foreign key (description_id) references beer_description,
    constraint fkocdpyrc4nwpnck9repdt6tg7o
        foreign key (style_id) references style,
    constraint fkcq60efkmr5lh0xc57h7qbj1ga
        foreign key (substyle_id) references substyle,
    constraint beer_container_check
        check ((container >= 0) AND (container <= 1))
);

create table order_item
(
    order_item_id bigserial,
    order_id      bigint,
    beer_id       bigint,
    quantity      integer,
    primary key (order_item_id),
    unique (beer_id),
    constraint fke6tx11auahxtxajs3kal2c42
        foreign key (beer_id) references beer,
    constraint fkt4dc2r9nbvbujrljv3e23iibt
        foreign key (order_id) references orders
);

