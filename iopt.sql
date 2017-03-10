-- Database: iopt

DROP DATABASE iopt;

CREATE DATABASE iopt
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Russian_Russia.1251'
       LC_CTYPE = 'Russian_Russia.1251'
       CONNECTION LIMIT = -1;

CREATE TABLE scripts (id SERIAL, content TEXT);

INSERT INTO scripts  values (0, 'test content');