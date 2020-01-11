create table if not exists rate (
    id SERIAL,
    user_id bigint not null,
    playing_field_id bigint not null,
    rating int not null,
    primary key(id),
    foreign key(user_id) references pf_user(id),
    foreign key (playing_field_id) references playing_field(id)
);

alter table playing_field add column rate float;
alter table playing_field add column number_of_votes int default 0;