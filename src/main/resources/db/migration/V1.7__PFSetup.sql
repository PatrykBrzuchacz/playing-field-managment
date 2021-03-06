create table playing_field_setup (
                         id SERIAL,
team_size int not null,
description varchar(3000) not null,
playing_field_photo BYTEA,
playing_field_id bigint,
name varchar(100),
primary key(id),
foreign key(playing_field_id) references playing_field(id)
);