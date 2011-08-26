--// rename location and user fields
-- Migration SQL that makes the change goes here.

ALTER TABLE "location" RENAME COLUMN "name" TO "description";

ALTER TABLE "user" RENAME COLUMN "main_name" TO "firstname";
ALTER TABLE "user" RENAME COLUMN "last_name" TO "lastname";


--//@UNDO
-- SQL to undo the change goes here.

ALTER TABLE "location" RENAME COLUMN "description" TO "name";

ALTER TABLE "user" RENAME COLUMN "firstname" TO "main_name";
ALTER TABLE "user" RENAME COLUMN "lastname" TO "last_name";
