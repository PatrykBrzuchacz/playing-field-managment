create table friends
(
    id bigint not null auto_increment,
    user_one bigint not null,
    user_two bigint not null,
    primary key (id),
    foreign key (user_one) references user (id),
    foreign key (user_two) references user (id)
);

create table friends_request
(
    id       bigint      not null auto_increment,
    sender_id bigint not null,
    receiver_id bigint not null,
    request_status   varchar(50) not null,
    primary key (id),
    foreign key (sender_id) references user (id),
    foreign key (receiver_id) references user (id)
);