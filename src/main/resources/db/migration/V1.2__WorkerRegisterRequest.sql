create table worker_request
(
    id               Serial,
    status           varchar(50),
    file_name        varchar(255),
    file_type        varchar(100),
    proof_of_work    BYTEA,
    user_id bigint,
    playing_field_id bigint,
    PRIMARY KEY (id),
    foreign key(user_id) references pf_user(id),
    foreign key (playing_field_id) references playing_field (id) ON DELETE CASCADE
);