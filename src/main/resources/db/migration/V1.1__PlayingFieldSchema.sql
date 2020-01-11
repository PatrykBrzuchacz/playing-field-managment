create table playing_field
(
    id           SERIAL,
    api_id        varchar(255),
    name          varchar(100),
    lat           double precision,
    lng           double precision,
    address       varchar(100),
    is_registered BOOLEAN default false,
    user_id       bigint,
    primary key (id),
    foreign key (user_id) references pf_user (id)
);