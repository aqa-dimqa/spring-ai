create extension vector;
create table if not exists vector_store
(
    id        uuid not null unique,
    content   text,
    metadata  json,
    embedding vector(1024),
    primary key (id)
)