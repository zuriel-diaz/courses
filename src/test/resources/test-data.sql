DELETE FROM roles;
INSERT INTO roles (id, name, is_active) VALUES (1,'teacher',1);
INSERT INTO roles (id, name, is_active) VALUES (2,'student',1);
INSERT INTO roles (id, name, is_active) VALUES (3,'admin',1);

DELETE FROM payment_types;
INSERT INTO payment_types (id, name) VALUES (1,'tdc');
INSERT INTO payment_types (id, name) VALUES (2,'spei');

DELETE FROM users;
INSERT INTO users (id, first_name, last_name, genre, birth_date, bio, email, password, role_id, is_active, phone_number) VALUES (1,'user','default','M','1981-01-01','teacher','default_user@gmail.com','password',1,true,'333-2122454');