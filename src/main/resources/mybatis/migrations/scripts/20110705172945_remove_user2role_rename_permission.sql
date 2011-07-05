--// remove user2role rename permission
-- Migration SQL that makes the change goes here.

--//permission will become action


DROP TABLE "role_permission";
DROP TABLE "permission";


CREATE TABLE "action"
(
  id character varying NOT NULL,
  description character varying NOT NULL,
  granted boolean NOT NULL,
  CONSTRAINT pk_action PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "action" OWNER TO rob;


CREATE TABLE role_action
(
 id bigserial NOT NULL,
 id_role bigint NOT NULL,
 id_action character varying NOT NULL,
 CONSTRAINT pk_role_action PRIMARY KEY (id),
 CONSTRAINT fk_role FOREIGN KEY (id_role)
     REFERENCES "role" (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION,
 CONSTRAINT fk_action FOREIGN KEY (id_action)
     REFERENCES "action" (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
 OIDS=FALSE
);
ALTER TABLE role_action OWNER TO rob;


-- make user-role many2many a one2many

DROP TABLE "user_role";

ALTER TABLE "user" ADD COLUMN role_id bigserial NOT NULL;
ALTER TABLE "user" ADD FOREIGN KEY (role_id) REFERENCES role(id);

--//@UNDO
-- SQL to undo the change goes here.


