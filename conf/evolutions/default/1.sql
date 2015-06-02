# --- !Ups

create table "CAT" ("NAME" VARCHAR NOT NULL PRIMARY KEY,"COLOR" VARCHAR NOT NULL);
create table "user" (
     id int NOT NULL PRIMARY KEY,
     avatar_link VARCHAR(256),
     first_name VARCHAR(64) NOT NULL,
     join_date DATE NOT NULL,
     last_name VARCHAR(64) NOT NULL,
     pass_hash VARCHAR(64) NOT NULL,
     username VARCHAR(64) NOT NULL,
);


# --- !Downs

drop table "CAT";
drop table "users";


