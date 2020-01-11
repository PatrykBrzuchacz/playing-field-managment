create table conversation (
                              id bigint not null auto_increment,
                              user_one bigint not null,
                              user_two bigint not null,
                              last_message_date TIMESTAMP,
                              primary key(id)
);
