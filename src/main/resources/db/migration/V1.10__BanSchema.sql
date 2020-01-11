create table ban(
    id bigint not null auto_increment,
    user_id bigint not null,
    playing_field_id bigint not null,
    ban_date timestamp,
    primary key(id),
    foreign key(user_id) references user(id),
    foreign key(playing_field_id) references  playing_field(id)
);