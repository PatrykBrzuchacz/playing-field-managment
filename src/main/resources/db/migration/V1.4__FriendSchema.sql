create table friends
(
    id SERIAL,
    user_one bigint not null,
    user_two bigint not null,
    primary key (id),
    foreign key (user_one) references pf_user (id),
    foreign key (user_two) references pf_user (id)
);

create table friends_request
(
    id       SERIAL,
    sender_id bigint not null,
    receiver_id bigint not null,
    request_status   varchar(50) not null,
    primary key (id),
    foreign key (sender_id) references pf_user (id),
    foreign key (receiver_id) references pf_user (id)
);