create sequence if not exists customers_seq;

create table if not exists customers
(
    id bigint not null default nextval('customers_seq'),
    phone varchar(16) not null,
    email varchar(64) not null,
    address varchar(255),
    constraint customers_pk primary key (id)
    );

comment on table customers is 'Clients';
comment on column customers.id is 'Client ID';
comment on column customers.phone is 'Client phone number';
comment on column customers.email is 'Client email address';
comment on column customers.address is 'Client address';