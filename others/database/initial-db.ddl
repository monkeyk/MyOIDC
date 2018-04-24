


--
--  Insert default user: admin/admin
truncate user_;
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
truncate user_privilege;
insert into user_privilege(uuid,create_time,user_id,privilege) values ('HJvLBVf1FuOBfEi782TIfoJOxLIKFKPdCTJGlX5ulo',now(),22,'UNITY');
insert into user_privilege(uuid,create_time,user_id,privilege) values ('YAHsOvr8Z57UKeyiRPDn5IpS8HVZ87gEvlfUy8ynAW',now(),23,'MOBILE');



-- initial oauth client details test data
-- 'unity-client'   support browser, js(flash) visit,  secret:  unity
-- 'mobile-client'  only support mobile-device visit,  secret:  mobile
truncate  oauth_client_details;
insert into oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types,
 web_server_redirect_uri,authorities, access_token_validity,
 refresh_token_validity, additional_information, create_time, archived, trusted)
values
  ('unity-client','myoidc-resource', '$2a$10$QQTKDdNfj9sPjak6c8oWaumvTsa10MxOBOV6BW3DvLWU6VrjDfDam', 'read','authorization_code,refresh_token,implicit',
                  'http://localhost:8080/myoidc-server/unity/dashboard','ROLE_CLIENT',null,
                  null,null, now(), 0, 0),
  ('mobile-client','myoidc-resource', '$2a$10$uLvpxfvm3CuUyjIvYq7a9OUmd9b3tHFKrUaMyU/jC01thrTdkBDVm', 'read','password,refresh_token',
                   null,'ROLE_CLIENT',null,
                   null,null, now(), 0, 0);

