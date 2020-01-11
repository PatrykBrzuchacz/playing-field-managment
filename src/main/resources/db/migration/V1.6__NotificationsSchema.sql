create table notifications (
    id bigint not null auto_increment,
    title varchar(255) not null,
    date_and_time timestamp not null,
    displayed bit(1) default false,
    received bigint not null,
    sender bigint not null,
    primary key (id),
    foreign key(received) references user(id),
    foreign key (sender) references  user(id)
)