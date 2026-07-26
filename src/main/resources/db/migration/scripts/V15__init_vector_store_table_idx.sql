create index if not exists vector_store_hnsw_index
        ON vector_store USING hnsw(embedding vector_cosine_ops)