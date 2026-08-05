create table if not exists app.chat_messages
(
    id         uuid         not null unique,
    user_id    uuid         not null,
    chat_id    uuid         not null,
    role       varchar(50)  not null,
    number     serial       not null,
    content    text,
    created_at timestamp(3) not null,
    updated_at timestamp(3) not null,
    primary key (id)
);

alter table app.chat_messages
    owner to postgres;

alter table app.chat_messages
    add constraint fk_chat_messages_chat_id
        foreign key (chat_id) references app.chats (id)
            on delete cascade;