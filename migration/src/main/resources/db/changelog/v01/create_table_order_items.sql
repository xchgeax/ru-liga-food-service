create sequence if not exists order_items_seq;

create table if not exists order_items
(
    id bigint not null default nextval('order_items_seq'),
    order_id uuid not null,
    restaurant_menu_item bigint not null,
    price int not null,
    quantity int not null,
    constraint order_items_pk primary key (id),
    constraint order_fk foreign key(order_id) references orders(id),
    constraint restaurant_menu_item_fk foreign key (restaurant_menu_item) references restaurant_menu_items (id)
    );

comment on table order_items is 'Order items';
comment on column order_items.id is 'Menu item ID';
comment on column order_items.order_id is 'Order ID';
comment on column order_items. restaurant_menu_item is 'Menu item ID';
comment on column order_items.price is 'Price';
comment on column order_items.quantity is 'Quantity';
