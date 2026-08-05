create table if not exists app.chats
(
    id           uuid         not null unique,
    user_id      uuid         not null,
    space_id     uuid,
    title        varchar(50),
    max_messages serial       not null,
    is_active    bool         not null,
    created_at   timestamp(3) not null,
    updated_at   timestamp(3) not null,
    primary key (id)
);

alter table app.chats
    owner to postgres;

alter table app.chats
    add constraint fk_chats_space_id
        foreign key (space_id) references app.spaces (id)
            on delete cascade