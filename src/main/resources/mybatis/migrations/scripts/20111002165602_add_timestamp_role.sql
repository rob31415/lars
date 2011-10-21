--// add_timestamp_role
-- Migration SQL that makes the change goes here.

ALTER TABLE "role" ADD COLUMN "timestamp" timestamp;
ALTER TABLE "role" DROP CONSTRAINT pk_role CASCADE;
UPDATE "role" set "timestamp" = NOW() WHERE "timestamp" IS NULL;
ALTER TABLE "role" ADD CONSTRAINT pk_role PRIMARY KEY (id, "timestamp");
ALTER TABLE "role" ALTER COLUMN "timestamp" SET NOT NULL;


--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE "role" DROP COLUMN "timestamp";
ALTER TABLE "role" ADD CONSTRAINT pk_role PRIMARY KEY (id);

