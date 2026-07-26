create table if not exists document_groups
(
    id         uuid         not null unique,
    user_id    uuid         not null unique,
    title      varchar(255) not null,
    created_at timestamp(3) not null,
    updated_at timestamp(3) not null,
    primary key (id)
)