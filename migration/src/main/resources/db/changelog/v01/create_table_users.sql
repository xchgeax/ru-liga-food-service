create table users
(
    username      varchar,
    password      varchar,
    enabled       varchar,
    priority      integer
);

create table authorities
(
    username  varchar,
    authority varchar
);