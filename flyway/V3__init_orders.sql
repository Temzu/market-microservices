DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS order_items CASCADE;

create table orders
(
    id         bigserial primary key,
    price      numeric(8, 2) not null,
    user_id    bigint not null,
    address    varchar(255) not null,
    phone      varchar(32) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    price             numeric(8, 2) not null,
    price_per_product numeric(8, 2) not null,
    product_id        bigint references products (id),
    order_id          bigint references orders (id),
    quantity          int,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (price, user_id, address, phone) values
    (28, 1, '111', '89048882233');

insert into order_items (price, price_per_product, product_id, order_id, quantity) values
    (28, 28, 2, 1, 1);