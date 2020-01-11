create table ban(
    id SERIAL,
    user_id bigint not null,
    playing_field_id bigint not null,
    ban_date timestamp,
    primary key(id),
    foreign key(user_id) references pf_user(id),
    foreign key(playing_field_id) references  playing_field(id)
);