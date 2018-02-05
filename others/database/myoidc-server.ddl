-- ###############
--    create database , if need create, cancel the comment
-- ###############
-- create database if not exists myoidc_server default character set utf8;
-- use myoidc_server set default character = utf8;

-- ###############
--    grant privileges  to myoidc/myoidc
-- ###############
-- GRANT ALL PRIVILEGES ON myoidc.* TO myoidc@localhost IDENTIFIED BY "myoidc";

-- ###############
-- Domain:  User
-- ###############
DROP TABLE IF EXISTS user_;
CREATE TABLE `user_` (
  `id`              INT(11)      NOT NULL AUTO_INCREMENT,
  `uuid`            VARCHAR(255) NOT NULL UNIQUE,
  `create_time`     DATETIME,
  `archived`        TINYINT(1)            DEFAULT '0',
  `version`         INT(11)               DEFAULT 0,

  `username`        VARCHAR(255) NOT NULL UNIQUE,
  `password`        VARCHAR(255) NOT NULL,
  `phone`           VARCHAR(255),
  `email`           VARCHAR(255),
  `default_user`    TINYINT(1)            DEFAULT '0',
  `last_login_time` DATETIME,
  `creator_id`      INT(11),
  PRIMARY KEY (`id`),
  INDEX `uuid_index` (`uuid`),
  INDEX `creator_id_index` (`creator_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;


