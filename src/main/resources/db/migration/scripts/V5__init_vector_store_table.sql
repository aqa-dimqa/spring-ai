create table if not exists app.vector_store
(
    id        uuid not null unique,
    content   text,
    metadata  jsonb,
    embedding app.vector(1024),
    primary key (id)
);

create index if not exists vector_store_hnsw_index
    on app.vector_store using hnsw(embedding app.vector_cosine_ops)