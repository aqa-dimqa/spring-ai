alter table documents
    add constraint unique_document
        unique (user_id, file_name, content_hash);