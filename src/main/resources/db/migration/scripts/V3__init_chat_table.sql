create table if not exists app.chats
(
    id            uuid         not null unique,
    user_id       uuid         not null,
    chat_group_id uuid,
    is_active     bool         not null,
    title         varchar(50),
    max_messages  serial       not null,
    position      serial      not null,
    created_at    timestamp(3) not null,
    updated_at    timestamp(3) not null,
    primary key (id)
);