create table message (
  id SERIAL,
    sender_id bigint not null,
    receiver_id bigint not null,
    content varchar(5000) not null,
    sended TIMESTAMP,
    primary key(id),
  foreign key (sender_id) references pf_user (id),
      foreign key (receiver_id) references pf_user (id)
)