 # --- !Ups

 CREATE TABLE users (
     id serial NOT NULL,
     avatar_link VARCHAR(256),
     first_name VARCHAR(64) NOT NULL,
     join_date DATE NOT NULL,
     last_name VARCHAR(64) NOT NULL,
     pass_hash VARCHAR(64) NOT NULL,
     username VARCHAR(64) NOT NULL,
     PRIMARY KEY (id)
 );

 CREATE TABLE posts (
     id serial NOT NULL,
     content TEXT NOT NULL,
     create_time TIMESTAMP NOT NULL,
     description VARCHAR(128) NOT NULL,
     edit_time TIMESTAMP,
     public_time TIMESTAMP,
     title VARCHAR(128) NOT NULL,
     user_id INTEGER NOT NULL REFERENCES users (id),
     PRIMARY KEY (id)
 );

 CREATE TABLE comments (
     id serial NOT NULL,
     content VARCHAR(256) NOT NULL,
     create_time TIMESTAMP NOT NULL,
     edit_time TIMESTAMP,
     pcomment_id INTEGER REFERENCES comments (id),
     post_id INTEGER NOT NULL REFERENCES posts (id),
     user_id INTEGER NOT NULL REFERENCES users (id),
     PRIMARY KEY (id)
 );



 CREATE TABLE tokens (
     id serial NOT NULL,
     create_time TIMESTAMP NOT NULL,
     expired_time TIMESTAMP NOT NULL,
     value VARCHAR(64) NOT NULL,
     user_id INTEGER NOT NULL REFERENCES users (id),
     PRIMARY KEY (id)
 );

 # --- !Downs
