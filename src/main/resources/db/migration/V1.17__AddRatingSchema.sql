create table if not exists rate (
    id bigint not null auto_increment,
    user_id bigint not null,
    playing_field_id bigint not null,
    rating int not null,
    primary key(id),
    foreign key(user_id) references user(id),
    foreign key (playing_field_id) references playing_field(id)
);

alter table playing_field add column rate float;
alter table playing_field add column number_of_votes int default 0;