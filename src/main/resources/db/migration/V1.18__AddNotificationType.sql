alter table notifications add column notification_type varchar(100) not null;
alter table notifications add column entity_id bigint;