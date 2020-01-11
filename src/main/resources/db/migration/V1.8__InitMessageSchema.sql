create table message (
  id bigint not null auto_increment,
    sender_id bigint not null,
    receiver_id bigint not null,
    content varchar(5000) not null,
    sended TIMESTAMP,
    primary key(id),
  foreign key (sender_id) references user (id),
      foreign key (receiver_id) references user (id)
)