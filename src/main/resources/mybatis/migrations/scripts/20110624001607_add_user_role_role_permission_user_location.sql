--// add user_role role_permission user_location
-- Migration SQL that makes the change goes here.


CREATE TABLE user_role
(
 id bigserial NOT NULL,
 id_user bigint NOT NULL,
 id_role bigint NOT NULL,
 CONSTRAINT pk_user_role PRIMARY KEY (id),
 CONSTRAINT fk_user FOREIGN KEY (id_user)
     REFERENCES "user" (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION,
 CONSTRAINT fk_role FOREIGN KEY (id_role)
     REFERENCES "role" (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
 OIDS=FALSE
);
ALTER TABLE user_role OWNER TO rob;


CREATE TABLE role_permission
(
 id bigserial NOT NULL,
 id_role bigint NOT NULL,
 id_permission character varying NOT NULL,
 CONSTRAINT pk_role_permission PRIMARY KEY (id),
 CONSTRAINT fk_role FOREIGN KEY (id_role)
     REFERENCES "role" (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION,
 CONSTRAINT fk_permission FOREIGN KEY (id_permission)
     REFERENCES "permission" (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
 OIDS=FALSE
);
ALTER TABLE role_permission OWNER TO rob;


CREATE TABLE user_location
(
 id bigserial NOT NULL,
 id_user bigint NOT NULL,
 id_location bigint NOT NULL,
 CONSTRAINT pk_user_location PRIMARY KEY (id),
 CONSTRAINT fk_user FOREIGN KEY (id_user)
     REFERENCES "user" (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION,
 CONSTRAINT fk_location FOREIGN KEY (id_location)
     REFERENCES "location" (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
 OIDS=FALSE
);
ALTER TABLE user_location OWNER TO rob;


--//@UNDO
-- SQL to undo the change goes here.

DROP TABLE "user_role";
DROP TABLE "role_permission";
DROP TABLE "user_location";
