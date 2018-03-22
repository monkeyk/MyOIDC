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
Drop table  if exists user_privilege;
CREATE TABLE user_privilege (
  user_id int(11),
  privilege varchar(255),
  KEY user_id_index (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--
--  Oauth sql  -- MYSQL
--

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


Drop table  if exists oauth_refresh_token;
create table oauth_refresh_token (
  create_time timestamp default now(),
  token_id VARCHAR(255),
  token BLOB,
  authentication BLOB
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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




--
--  Insert default user: admin/admin
INSERT INTO user_ (id, uuid, create_time, archived, version, username, password, phone, email, default_user)
VALUES
  (21, 'wR4XwW4UdCbfOWuMCYj8lafxApKZHtgl6uls55Ij2i', now(), 0, 0, 'admin', '$2a$10$XWN7zOvSLDiyxQnX01KMXuf5NTkkuAUtt23YxUMWaIPURcR7bdULi', NULL,
   'admin@qc8.me', 1);

-- unity/unity
INSERT INTO user_ (id, uuid, create_time, archived, version, username, password, phone, email, default_user)
VALUES
  (22, 'fFVLrIx6MgVwXDhKUHE23KR3w0KqOulHjSNyf6rC04', now(), 0, 0, 'unity', '$2a$10$gq3eUch/h.eHt20LpboSXeeZinzSLBk49K5KD.Ms4/1tOAJIsrrfq', NULL,
   'unity@qc8.me', 0);

-- mobile/mobile
INSERT INTO user_ (id, uuid, create_time, archived, version, username, password, phone, email, default_user)
VALUES
  (23, 'Ajlt9ZwVyGUvxrJCdKlFA4AataAVKVgH6gxYeCxD6J', now(), 0, 0, 'mobile', '$2a$10$BOmMzLDaoiIQ4Q1pCw6Z4u0gzL01B8bNL.0WUecJ2YxTtHVRIA8Zm', NULL,
   'mobile@qc8.me', 0);



-- user-privilege
insert into user_privilege(user_id,privilege) values (22,'UNITY');
insert into user_privilege(user_id,privilege) values (23,'MOBILE');


-- initial oauth client details test data
-- 'unity-client'   support browser, js(flash) visit
-- 'mobile-client'  only support mobile-device visit
truncate  oauth_client_details;
insert into oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types,
 web_server_redirect_uri,authorities, access_token_validity,
 refresh_token_validity, additional_information, create_time, archived, trusted)
values
  ('unity-client','unity-resource', 'unity', 'read,write','authorization_code,refresh_token,implicit',
                  null,'ROLE_CLIENT',null,
                  null,null, now(), 0, 0),
  ('mobile-client','mobile-resource', 'mobile', 'read,write','password,refresh_token',
                   null,'ROLE_CLIENT',null,
                   null,null, now(), 0, 0);


