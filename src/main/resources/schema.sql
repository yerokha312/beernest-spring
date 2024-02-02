-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;
--
-- GRANT ALL ON SCHEMA public TO postgres;
-- GRANT ALL ON SCHEMA public TO public;
--
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
    primary key (brand_id),
    unique (name)
);

create table contact_info
(
    contact_info_id bigserial,
    phone_number    varchar(255),
    address         varchar(255),
    is_active       boolean default true,
    primary key (contact_info_id)
);

create table customer
(
    user_id    bigserial,
    first_name varchar(255),
    last_name  varchar(255),
    dob        date,
    email      varchar(255),
    password   varchar(255),
    reg_date   timestamp(6),
    active     boolean,
    primary key (user_id),
    unique (email)
);

create table customer_contact_junction
(
    customer_id     bigint not null,
    contact_info_id bigint not null,
    primary key (contact_info_id, customer_id),
    constraint fk1exe45la00aorf2vul1wkghh8
        foreign key (contact_info_id) references contact_info,
    constraint fk6i86sqgqnrrngokohpxs2fc1f
        foreign key (customer_id) references customer
);

create table employee
(
    user_id    bigserial,
    first_name varchar(255),
    last_name  varchar(255),
    dob        date,
    email      varchar(255),
    password   varchar(255),
    reg_date   timestamp(6),
    active     boolean,
    primary key (user_id),
    unique (email)
);

insert into employee (user_id, first_name, last_name, dob, email, password, reg_date, active)
VALUES (10000, 'admin', 'test', '2024-01-13', 'admin@test.ru',
        '$2a$10$qQGmKSkxTutAFsG02GseKOLVhwbp/JEMT54bBnWKUc.YyOWHLpeLq', '2024-01-13', true),
       (10001, 'observer', 'test', '2024-01-13', 'observer@test.ru',
        '$2a$10$5UERl8ptShF3doTo27bG2uvVRqLpfUt1N7WDdD9bAzzra4JqfAKbq', '2024-01-13', true);


create table orders
(
    order_id        bigserial,
    creation_date   timestamp(6),
    total_price     numeric(38, 2),
    status          varchar(255),
    customer_id     bigint,
    contact_info_id bigint,
    delivered       boolean,
    primary key (order_id),
    constraint fkli3ua5uggpx5jjoer07pan8bg
        foreign key (contact_info_id) references contact_info,
    constraint fk624gtjin3po807j3vix093tlf
        foreign key (customer_id) references customer,
    constraint orders_status_check
        check ((status)::text = ANY
               ((ARRAY ['PENDING'::character varying, 'PAID'::character varying, 'CANCELED'::character varying, 'RETURNED'::character varying])::text[]))
);

create table role
(
    role_id   serial,
    authority varchar(255),
    primary key (role_id)
);

insert into role (role_id, authority)
values (1, 'CUSTOMER'),
       (2, 'OBSERVER'),
       (3, 'ADMIN');

create table customer_role_junction
(
    user_id bigint  not null,
    role_id integer not null,
    primary key (role_id, user_id),
    constraint fkj0b2u85lnckr69tgekgicfsiw
        foreign key (role_id) references role,
    constraint fk9jk8y1c2brvb2sfslptp11ogi
        foreign key (user_id) references customer
);

create table employee_role_junction
(
    user_id bigint  not null,
    role_id integer not null,
    primary key (role_id, user_id),
    constraint fk7u33y66dsk2sdptyimrkxboxy
        foreign key (role_id) references role,
    constraint fk6p9dhva3kr8obtfieo6gua2ru
        foreign key (user_id) references employee
);

insert into employee_role_junction (user_id, role_id)
values (10000, 3),
       (10000, 2),
       (10001, 2);

create table sub_style
(
    sub_style_id bigserial,
    name         varchar(255),
    style        varchar(255),
    primary key (sub_style_id),
    unique (name),
    constraint sub_style_style_check
        check ((style)::text = ANY
               ((ARRAY ['ALE'::character varying, 'LAGER'::character varying, 'HYBRID'::character varying])::text[]))
);

create table beer
(
    beer_id        bigserial,
    beer_code      varchar(255),
    name           varchar(255),
    style          varchar(255),
    sub_style_id   bigint,
    brand_id       bigint,
    alcohol        double precision,
    container      varchar(255),
    size           integer,
    purchase_price numeric(38, 2),
    selling_price  numeric(38, 2),
    country        varchar(255),
    description_id bigint,
    sold_amount    bigint,
    stock_amount   integer,
    primary key (beer_id),
    unique (description_id),
    unique (beer_code),
    constraint fk65rhv8kyel4a8ekerdc6cw4nc
        foreign key (description_id) references beer_description,
    constraint fkd9t6c85c90sicbgpnsdip3ifa
        foreign key (brand_id) references brand,
    constraint fkcq60efkmr5lh0xc57h7qbj1ga
        foreign key (sub_style_id) references sub_style,
    constraint beer_container_check
        check ((container)::text = ANY ((ARRAY ['CAN'::character varying, 'BOTTLE'::character varying])::text[])),
    constraint beer_style_check
        check ((style)::text = ANY
               ((ARRAY ['ALE'::character varying, 'LAGER'::character varying, 'HYBRID'::character varying])::text[]))
);

create table order_item
(
    order_item_id bigserial,
    order_id      bigint,
    beer_id       bigint,
    quantity      integer,
    primary key (order_item_id),
    constraint fke6tx11auahxtxajs3kal2c42
        foreign key (beer_id) references beer,
    constraint fkt4dc2r9nbvbujrljv3e23iibt
        foreign key (order_id) references orders
);

