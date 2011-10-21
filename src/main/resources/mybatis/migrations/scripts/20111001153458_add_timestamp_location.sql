--// add_timestamp_location
-- Migration SQL that makes the change goes here.

ALTER TABLE "location" ADD COLUMN "timestamp" timestamp;
ALTER TABLE "location" DROP CONSTRAINT pk_location CASCADE;
UPDATE "location" set "timestamp" = NOW() WHERE "timestamp" IS NULL;
ALTER TABLE "location" ADD CONSTRAINT pk_location PRIMARY KEY (id, "timestamp");
ALTER TABLE "location" ALTER COLUMN "timestamp" SET NOT NULL;


--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE "location" DROP COLUMN "timestamp";
ALTER TABLE "location" ADD CONSTRAINT pk_location PRIMARY KEY (id);

