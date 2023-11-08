create table if not exists orders
(
    id uuid not null ,
    customer_id bigint not null,
    restaurant_id bigint not null,
    status varchar(64) not null,
    courier_id int,
    timestamp timestamptz not null default now(),
    constraint order_pk primary key (id),
    constraint customer_fk foreign key(customer_id) references customers(id),
    constraint restaurant_fk foreign key(restaurant_id) references restaurants(id),
    constraint courier_fk foreign key(courier_id) references couriers(id)
    );

comment on table orders is 'Orders';
comment on column orders.id is 'Order ID';
comment on column orders.customer_id is 'Customer ID';
comment on column orders. restaurant_id is 'Restaurant ID';
comment on column orders.status is 'Order status';
comment on column orders.courier_id is 'Courier ID';
comment on column orders.timestamp is 'Date';