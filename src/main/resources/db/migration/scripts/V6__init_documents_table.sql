create table if not exists documents
(
    id            uuid         not null unique,
    user_id       uuid         not null,
    file_name     varchar(255) not null,
    content_hash  varchar(64)  not null,
    document_type varchar(10)  not null,
    chunk_count   integer,
    loaded_at     timestamp(3) not null default current_timestamp,
    primary key (id)
);

alter table app.chats
    owner to postgres;

alter table documents
    add constraint unique_document
        unique (file_name, content_hash);

create index if not exists idx_documents_filename
    on documents (file_name);