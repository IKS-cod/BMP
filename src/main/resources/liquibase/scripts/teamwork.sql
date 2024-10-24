-- liquibase formatted sql

-- changeset ismirnov:1
create TABLE product (
	id bigint NOT NULL PRIMARY KEY,
	productName character varying(255) NOT NULL,
	productId character varying(255) NOT NULL,
	productText character varying(255) NOT NULL,
	rule JSONB
	);
-- changeset ismirnov:2
create TABLE product1 (
    id BIGINT NOT NULL PRIMARY KEY,
    query VARCHAR(255) NOT NULL,
    arguments JSONB,
    negate BOOLEAN NOT NULL
);
-- changeset ismirnov:3
create TABLE stats (
   rule_id BIGINT NOT NULL PRIMARY KEY,
    count int NOT NULL,
);
