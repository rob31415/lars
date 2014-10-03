--// modify user_location
-- Migration SQL that makes the change goes here.

DROP TABLE user_location;

CREATE TABLE user2location
(
  id_a bigint,
  "timestamp_a" timestamp without time zone,
  id_b bigint,
  "timestamp_b" timestamp without time zone
--  CONSTRAINT pk_user_location PRIMARY KEY (id_a , id_b , "timestamp_a", "timestamp_b" )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user2location
  OWNER TO rob;

--//@UNDO
-- SQL to undo the change goes here.


