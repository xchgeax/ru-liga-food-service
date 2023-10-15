create sequence if not exists restaurant_menu_items_seq;

create table if not exists restaurant_menu_items
(
    id bigint not null default nextval('restaurant_menu_items_seq'),
    restaurant_id bigint not null,
    name varchar(64) not null,
    price int not null,
    image varchar(512),
    description varchar(512),
    constraint restaurant_menu_items_pk primary key (id),
    constraint restaurant_fk foreign key(restaurant_id) references restaurants(id)
    );

comment on table restaurant_menu_items is 'Restaurant menu';
comment on column restaurant_menu_items.id is 'Menu item ID';
comment on column restaurant_menu_items.restaurant_id is 'Restaurant ID';
comment on column restaurant_menu_items.name is 'Name';
comment on column restaurant_menu_items.price is 'Price';
comment on column restaurant_menu_items.image is 'Image';
comment on column restaurant_menu_items.description is 'Description';