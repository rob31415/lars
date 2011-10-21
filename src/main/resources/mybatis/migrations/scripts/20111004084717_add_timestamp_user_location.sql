--// add_timestamp_user_location
-- Migration SQL that makes the change goes here.

ALTER TABLE "user_location" DROP COLUMN "id";
ALTER TABLE "user_location" ADD COLUMN "timestamp" timestamp;
--ALTER TABLE "user_location" DROP CONSTRAINT pk_user_location CASCADE;
UPDATE "user_location" set "timestamp" = NOW() WHERE "timestamp" IS NULL;
ALTER TABLE "user_location" ADD CONSTRAINT pk_user_location PRIMARY KEY (id_user, id_location, "timestamp");
ALTER TABLE "user_location" ALTER COLUMN "timestamp" SET NOT NULL;

--ALTER TABLE "user_location" ADD CONSTRAINT fk_user FOREIGN KEY (id_user)
--     REFERENCES "user" (id) MATCH SIMPLE
--     ON UPDATE NO ACTION ON DELETE NO ACTION;
     
--ALTER TABLE "user_location" ADD CONSTRAINT fk_location FOREIGN KEY (id_location)
--     REFERENCES "location" (id) MATCH SIMPLE
--     ON UPDATE NO ACTION ON DELETE NO ACTION;



--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE "user_location" ADD COLUMN "id";
ALTER TABLE "user_location" DROP COLUMN "timestamp";
ALTER TABLE "user_location" ADD CONSTRAINT pk_user_location PRIMARY KEY (id);

--ALTER TABLE "user_location" ADD CONSTRAINT fk_user FOREIGN KEY (id_user)
--     REFERENCES "user" (id) MATCH SIMPLE
--     ON UPDATE NO ACTION ON DELETE NO ACTION;
     
--ALTER TABLE "user_location" ADD CONSTRAINT fk_location FOREIGN KEY (id_location)
--     REFERENCES "location" (id) MATCH SIMPLE
--     ON UPDATE NO ACTION ON DELETE NO ACTION;

