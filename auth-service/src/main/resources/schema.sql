create table if not exists oauth2_registered_client
(
    id                            varchar(100)                            not null
        primary key,
    client_id                     varchar(100)                            not null,
    client_id_issued_at           timestamp     default CURRENT_TIMESTAMP not null,
    client_secret                 varchar(200)  default NULL::character varying,
    client_secret_expires_at      timestamp,
    client_name                   varchar(200)                            not null,
    client_authentication_methods varchar(1000)                           not null,
    authorization_grant_types     varchar(1000)                           not null,
    redirect_uris                 varchar(1000) default NULL::character varying,
    scopes                        varchar(1000)                           not null,
    client_settings               varchar(2000)                           not null,
    token_settings                varchar(2000)                           not null
);

alter table if exists oauth2_registered_client
    owner to postgres;

create table if not exists users
(
    username varchar(50)          not null
        primary key,
    password varchar(100)         not null,
    enabled  boolean default true not null
);

alter table if exists users
    owner to postgres;

create table if not exists authorities
(
    username  varchar(50) not null
        references users,
    authority varchar(50) not null
);

alter table if exists authorities
    owner to postgres;


