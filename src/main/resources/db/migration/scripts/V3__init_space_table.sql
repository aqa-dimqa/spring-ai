create table if not exists app.spaces
(
    id         uuid         not null unique,
    user_id    uuid         not null,
    title      varchar(50),
    is_active  bool         not null,
    created_at timestamp(3) not null,
    updated_at timestamp(3) not null,
    primary key (id)
);

alter table app.spaces
    owner to postgres;