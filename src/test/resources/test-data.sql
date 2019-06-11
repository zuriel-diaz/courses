DELETE FROM roles;
INSERT INTO roles (id, name, is_active) VALUES (1,'teacher',1);
INSERT INTO roles (id, name, is_active) VALUES (2,'student',1);
INSERT INTO roles (id, name, is_active) VALUES (3,'admin',1);

DELETE FROM payment_types;
INSERT INTO payment_types (id, name) VALUES (1,'tdc');
INSERT INTO payment_types (id, name) VALUES (2,'spei');
