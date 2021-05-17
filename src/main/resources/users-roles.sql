CREATE TABLE users
(
    username VARCHAR(50) NOT NULL,
    password VARCHAR(68) NOT NULL,
    enabled TINYINT(1) NOT NULL,
    PRIMARY KEY(username)
);

INSERT INTO users (username, password, enabled) VALUES('admin','$2a$10$cRqfrdolNVFW6sAju0eNEOE0VC29aIyXwfsEsY2Fz2axy3MnH8ZGa',1);

CREATE TABLE authorities
(
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(68) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO authorities VALUES('admin','ROLE_ADMIN');
INSERT INTO authorities VALUES('admin','ROLE_USER');