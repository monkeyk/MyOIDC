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

-- ###############
-- Domain:  Privilege
-- ###############
DROP TABLE IF EXISTS user_privilege;
CREATE TABLE user_privilege (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `uuid`        VARCHAR(255) NOT NULL UNIQUE,
  `create_time` DATETIME,
  `archived`    TINYINT(1)            DEFAULT '0',
  `version`     INT(11)               DEFAULT 0,

  user_id       INT(11),
  privilege     VARCHAR(255),
  PRIMARY KEY (`id`),
  INDEX `uuid_index` (`uuid`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8;



--
--  Oauth sql  -- MYSQL
--
-- oauth_client_details
Drop table  if exists oauth_client_details;
create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information TEXT,
  create_time timestamp default now(),
  archived tinyint(1) default '0',
  trusted tinyint(1) default '0',
  autoapprove VARCHAR (255) default 'false'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- oauth_access_token
Drop table  if exists oauth_access_token;
create table oauth_access_token (
  create_time timestamp default now(),
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BLOB,
  refresh_token VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- oauth_refresh_token
Drop table  if exists oauth_refresh_token;
create table oauth_refresh_token (
  create_time timestamp default now(),
  token_id VARCHAR(255),
  token BLOB,
  authentication BLOB
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- oauth_code
Drop table  if exists oauth_code;
create table oauth_code (
  create_time timestamp default now(),
  code VARCHAR(255),
  authentication BLOB
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- Add indexes
create index token_id_index on oauth_access_token (token_id);
create index authentication_id_index on oauth_access_token (authentication_id);
create index user_name_index on oauth_access_token (user_name);
create index client_id_index on oauth_access_token (client_id);
create index refresh_token_index on oauth_access_token (refresh_token);

create index token_id_index on oauth_refresh_token (token_id);

create index code_index on oauth_code (code);




