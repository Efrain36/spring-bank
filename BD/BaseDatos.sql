-- Create the Person table in the client-service schema
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

-- Create the Account table in the account-service schema
CREATE SCHEMA IF NOT EXISTS "account-service";
CREATE TABLE IF NOT EXISTS "account-service"."account" (
    "id" SERIAL PRIMARY KEY,
    "number" UUID NOT NULL UNIQUE,
    "type" VARCHAR(255) NOT NULL,
    "client_id" INTEGER NOT NULL,
    "balance" DOUBLE PRECISION NOT NULL,
    "status" BOOLEAN NOT NULL
    );

-- Create the Transaction table in the account-service schema
CREATE TABLE IF NOT EXISTS "account-service"."transaction" (
    "id" SERIAL PRIMARY KEY,
    "amount" DOUBLE PRECISION NOT NULL,
    "previous_balance" DOUBLE PRECISION NOT NULL,
    "final_balance" DOUBLE PRECISION NOT NULL,
    "date" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "account_id" INTEGER NOT NULL,
    FOREIGN KEY ("account_id") REFERENCES "account-service"."account" ("id")
    );
