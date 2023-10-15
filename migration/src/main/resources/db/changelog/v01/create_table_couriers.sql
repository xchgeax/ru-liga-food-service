create sequence if not exists couriers_seq;

create table if not exists couriers
(
    id bigint not null default nextval('couriers_seq'),
    phone       varchar(16) not null,
    status      varchar(16) not null,
    coordinates varchar(20) not null,
    constraint couriers_pk PRIMARY KEY (id)
);

comment on table couriers is 'Couriers';
comment on column couriers.id is 'Courier ID';
comment on column couriers.phone is 'Courier phone number';
comment on column couriers.status is 'Courier status';
comment on column couriers.coordinates is 'Courier coordinates';