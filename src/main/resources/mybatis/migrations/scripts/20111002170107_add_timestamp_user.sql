--// add_timestamp_user
-- Migration SQL that makes the change goes here.

ALTER TABLE "user" ADD COLUMN "timestamp" timestamp;
ALTER TABLE "user" DROP CONSTRAINT pk_user CASCADE;
UPDATE "user" SET "timestamp" = NOW() WHERE "timestamp" IS NULL;
ALTER TABLE "user" ADD CONSTRAINT pk_user PRIMARY KEY (id, "timestamp");
ALTER TABLE "user" ALTER COLUMN "timestamp" SET NOT NULL;


--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE "user" DROP COLUMN "timestamp";
ALTER TABLE "user" ADD CONSTRAINT pk_user PRIMARY KEY (id);

