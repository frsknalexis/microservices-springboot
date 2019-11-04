INSERT INTO tbl_usuarios (username, password, enabled, nombre, apellido, email) VALUES('andres', '$2a$10$zGcBB9KY5lotXV82jByk7ucYFdG6lbhid3YGD7pV.GPPkBjZzIi5y', true, 'Andres', 'Guzman', 'profesor@bolsadeideas.com');
INSERT INTO tbl_usuarios (username, password, enabled, nombre, apellido, email) VALUES('alexis', '$2a$10$MHHLsf5FrgQxkgasL5BetOw225aFBOV9a6GYrfTw04MdMRvdUq5bC', true, 'Alexis', 'Manuel', 'alexisgutierrezf.1997@gmail.com');

INSERT INTO tbl_roles (nombre) VALUES('ROLE_USER');
INSERT INTO tbl_roles (nombre) VALUES('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2, 1);