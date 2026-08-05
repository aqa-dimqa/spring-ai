create table if not exists app.vector_store
(
    id        uuid not null unique,
    content   text,
    metadata  json,
    embedding vector(1024),
    primary key (id)
);

alter table app.chat_messages
    owner to postgres;

create index if not exists vector_store_hnsw_index
    on app.vector_store using hnsw (embedding vector_cosine_ops)