-- Database: iopt


---------------
-- Execute once
---------------
CREATE DATABASE iopt
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Russian_Russia.1251'
       LC_CTYPE = 'Russian_Russia.1251'
       CONNECTION LIMIT = -1;

------------------------
-- Execute every testing
------------------------

DROP TABLE IF EXISTS scripts;
CREATE TABLE scripts (id SERIAL, content TEXT);
INSERT INTO scripts VALUES (0, 'function run(name) { print(''I will work with, '' + name);};');


SELECT
  model.id       AS model_id,
  model.name     AS model_name,
  object.name    AS object_name,
  property.name  AS property_name,
  property.value AS property_value
FROM model, object, property, script
WHERE
  script.id_property = property.id
  AND property.id_object = object.id
  AND object.id_model = model.id
  AND script.id = 1;