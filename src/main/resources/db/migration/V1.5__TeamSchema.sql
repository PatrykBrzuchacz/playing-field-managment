create table team
(
    id bigint not null auto_increment,
    team_name varchar(100) not null,
    match_id bigint not null,
    is_full bit(1) not null default 0,
    primary key(id),
    foreign key(match_id) references game(id)
);

create table team_user (
    team_id bigint not null,
    user_id bigint not null,
    foreign key(team_id) references team(id),
    foreign key(user_id) references user(id)
);

