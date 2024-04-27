CREATE SCHEMA IF NOT EXISTS "client-service";
CREATE TABLE IF NOT EXISTS "client-service"."person" (
    "id" SERIAL PRIMARY KEY,
    "identification" VARCHAR(255) NOT NULL UNIQUE,
    "name" VARCHAR(255) NOT NULL,
    "gender" VARCHAR(255),
    "age" INTEGER NOT NULL,
    "address" VARCHAR(255),
    "phone" VARCHAR(255)
    );

-- Create the Client table in the client-service schema
CREATE TABLE IF NOT EXISTS "client-service"."client" (
    "id" SERIAL PRIMARY KEY,
    "password" VARCHAR(255) NOT NULL,
    "status" BOOLEAN NOT NULL,
    "person_id" INTEGER,
    FOREIGN KEY ("person_id") REFERENCES "client-service"."person" ("id")
    );