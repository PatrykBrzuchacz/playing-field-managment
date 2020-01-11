create table notifications (
    id SERIAL,
    title varchar(255) not null,
    date_and_time timestamp not null,
    displayed BOOLEAN default false,
    received bigint not null,
    sender bigint not null,
    primary key (id),
    foreign key(received) references pf_user(id),
    foreign key (sender) references  pf_user(id)
)