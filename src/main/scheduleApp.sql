CREATE SCHEMA `app_schedule`;

USE `app_schedule`;

CREATE TABLE `user`
(
    `id`       BIGINT AUTO_INCREMENT NOT NULL,
    `email`    VARCHAR(50) UNIQUE    NOT NULL,
    `name`     VARCHAR(50)           NOT NULL,
    `reg_date` DATE                  NOT NULL,
    `mod_date` DATE                  NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `schedule`
(
    `id`        BIGINT AUTO_INCREMENT NOT NULL,
    `contents`  VARCHAR(200)          NOT NULL,
    `password`  VARCHAR(50)           NOT NULL,
    `mod_date`  DATE                  NOT NULL,
    `fk_userid` BIGINT                NOT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `schedule`
    ADD FOREIGN KEY (fk_userid)
        REFERENCES `user` (id) ON UPDATE CASCADE ON DELETE CASCADE;