create table playing_field
(
    id            bigint not null auto_increment,
    api_id        varchar(255),
    name          varchar(100),
    lat           double,
    lng           double,
    address       varchar(100),
    is_registered bit(1) default false,
    user_id       bigint,
    primary key (id),
    foreign key (user_id) references user (id)
);