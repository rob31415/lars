--// add user role permission location
-- Migration SQL that makes the change goes here.

CREATE TABLE "user"
(
  id bigserial NOT NULL,
  main_name character varying NOT NULL,
  last_name character varying NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "user" OWNER TO rob;


CREATE TABLE "role"
(
  id bigserial NOT NULL,
  description character varying NOT NULL,
  CONSTRAINT pk_role PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "role" OWNER TO rob;


CREATE TABLE "permission"
(
  id character varying NOT NULL,
  description character varying NOT NULL,
  granted boolean NOT NULL,
  CONSTRAINT pk_permission PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "permission" OWNER TO rob;


CREATE TABLE "location"
(
  id bigserial NOT NULL,
  name character varying NOT NULL,
  CONSTRAINT pk_location PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "location" OWNER TO rob;



--//@UNDO
-- SQL to undo the change goes here.

DROP TABLE "user";
DROP TABLE "role";
DROP TABLE "permission";
DROP TABLE "location";
