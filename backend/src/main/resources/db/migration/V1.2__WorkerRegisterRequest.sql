create table worker_request (
    id bigint auto_increment,
    status varchar(50),
    file_name varchar(255),
    file_type varchar(100),
    proof_of_work LONGBLOB,
#     user_id bigint,
    playing_field_id bigint,
    PRIMARY KEY (id),
#     foreign key(user_id) references user(id),
    foreign key(playing_field_id) references playing_field(id) ON DELETE CASCADE
                            );