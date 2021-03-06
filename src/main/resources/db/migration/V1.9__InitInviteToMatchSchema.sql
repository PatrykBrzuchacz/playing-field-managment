create table invite (
    id SERIAL,
    sender_id bigint not null,
    receiver_id bigint not null,
    match_id bigint not null,
    status varchar(50),
    primary key(id),
foreign key(sender_id) references pf_user(id),
    foreign key(receiver_id) references pf_user(id),
    foreign key(match_id) references game(id)
)