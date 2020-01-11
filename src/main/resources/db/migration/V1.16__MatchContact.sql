create table if not exists match_contact (
    id bigint not null auto_increment,
    match_id bigint not null,
    phone_number varchar(50),
    email varchar(100),
    surname varchar(100),
    primary key(id),
    foreign key (match_id) references game(id)
);