alter table message add column  conversation_id bigint not null;
alter table message add constraint  FK_conversation_id foreign key(conversation_id) references conversation(id);