create sequence if not exists restaurants_seq;

create table if not exists restaurants
(
    id bigint not null default nextval('restaurants_seq'),
    name varchar(255) not null,
    address varchar(255) not null,
    status varchar(64) not null,
    constraint restaurants_pk primary key (id)
    );

comment on table restaurants is 'Restaurants';
comment on column restaurants.id is 'Restaurant ID';
comment on column restaurants.name is 'Restaurant name';
comment on column restaurants.address is 'Restaurant address';
comment on column restaurants.status is 'Restaurant status';