CREATE TABLE `role`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `description` varchar(255),
    `name`        varchar(255),
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `username`   varchar(255),
    `password`   varchar(255),
    `banned`     BIT(1) NOT NULL DEFAULT 0,
    `active`     BIT(1) NOT NULL DEFAULT 1,
    `position`  varchar(100),
    `id_role`    bigint,
    `last_login` TIMESTAMP       default null,
    `registered` TIMESTAMP       default null,
    `phone_number` varchar(20),
    `email` varchar(100),
    `avatar` LONGBLOB,
    `age` int default null,
    `first_name` varchar(100),
    `last_name` varchar(100),
    `city` varchar(100),
    PRIMARY KEY (`id`),
    foreign key (id_role) references role (id) on delete cascade
);
