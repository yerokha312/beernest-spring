DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;

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
    address         varchar(255),
    phone_number    varchar(255),
    primary key (contact_info_id)
);

create table customer
(
    id         bigserial,
    first_name varchar(255),
    last_name  varchar(255),
    dob        date,
    email      varchar(255),
    password   varchar(255),
    reg_date   timestamp(6),
    active     boolean default true,
    primary key (id),
    unique (email)
);

create table customer_contact_junction
(
    customer_id     bigint not null,
    contact_info_id bigint not null,
    constraint fk1exe45la00aorf2vul1wkghh8
        foreign key (contact_info_id) references contact_info,
    constraint fk6i86sqgqnrrngokohpxs2fc1f
        foreign key (customer_id) references customer
);

create table employee
(
    id         bigserial,
    first_name varchar(255),
    last_name  varchar(255),
    dob        date,
    email      varchar(255),
    password   varchar(255),
    authority  varchar(255),
    reg_date   timestamp(6),
    active     boolean default true,
    primary key (id),
    unique (email),
    constraint employee_authority_check
        check ((authority)::text = ANY ((ARRAY ['ADMIN'::character varying, 'OBSERVER'::character varying])::text[]))
);

create table orders
(
    order_id      bigserial,
    creation_date timestamp(6),
    total_price   numeric(38, 2),
    status        varchar(255) default 'PENDING',
    customer_id   bigint,
    address_id    bigint,
    delivered     boolean      default false,
    primary key (order_id),
    constraint fk63wqm7yt0l4sbsp24r6rgqgig
        foreign key (address_id) references contact_info,
    constraint fk624gtjin3po807j3vix093tlf
        foreign key (customer_id) references customer,
    constraint orders_status_check
        check ((status)::text = ANY
               ((ARRAY ['PENDING'::character varying, 'PAID'::character varying, 'CANCELED'::character varying, 'RETURNED'::character varying])::text[]))
);

create table substyle
(
    substyle_id bigserial,
    style       varchar(255),
    name        varchar(255),
    primary key (substyle_id),
    unique (name),
    constraint substyle_style_check
        check ((style)::text = ANY
               ((ARRAY ['ALE'::character varying, 'LAGER'::character varying, 'HYBRID'::character varying])::text[]))
);

create table beer
(
    beer_id        bigserial,
    beer_code      varchar(255),
    name           varchar(255),
    style          varchar(255),
    substyle_id    bigint,
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
        foreign key (substyle_id) references substyle,
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

