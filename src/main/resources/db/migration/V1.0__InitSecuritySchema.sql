CREATE TABLE role
(
    id         SERIAL,
    description varchar(255),
    name        varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE pf_user
(
    id         SERIAL,
    username   varchar(255),
    password   varchar(255),
    banned     BOOLEAN NOT NULL DEFAULT false,
    active     BOOLEAN NOT NULL DEFAULT true,
    position  varchar(100),
    id_role    bigint,
    last_login TIMESTAMP       default null,
    registered TIMESTAMP       default null,
    phone_number varchar(20),
    email varchar(100),
    avatar BYTEA,
    age int default null,
    first_name varchar(100),
    last_name varchar(100),
    city varchar(100),
    PRIMARY KEY (id),
    foreign key (id_role) references role (id) on delete cascade
);
