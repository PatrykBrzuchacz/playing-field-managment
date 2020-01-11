alter table game add column booked_date timestamp;
alter table game add column is_code_filled bit(1) default false;