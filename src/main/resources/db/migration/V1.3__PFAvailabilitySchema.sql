create table playing_field_availability
(
    id               bigint not null auto_increment,
    opened_date_pf   DATE   not null,
    closed_date_pf   DATE   not null,
    opened_time_pf   TIME   not null,
    closed_time_pf   TIME   not null,
    match_time       int    not null,
    playing_field_id bigint not null,
    primary key (id),
    foreign key (playing_field_id) references playing_field (id)
);

create table reservation (
    id bigint not null auto_increment,
    email varchar(100),
    phone_number varchar(20),
    last_name varchar(50),
    primary key(id)
);

create table game(
    id bigint not null auto_increment,
    match_date TIMESTAMP not null,
    from_time TIME not null,
    to_time TIME not null,
    user_id bigint,
    reservation_id bigint,
    playing_field_availability_id bigint not null,
    is_full BIT(1) NOT NULL DEFAULT 0,
    is_private bit(1) not null default 0,
    is_booked bit(1) not null default 0,
    primary key(id),
    foreign key(user_id) references user(id),
    foreign key(reservation_id) references reservation(id),
    foreign key(playing_field_availability_id) references playing_field_availability(id)
);