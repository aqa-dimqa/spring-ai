create table if not exists app.spaces
(
    id         uuid         not null unique,
    user_id    uuid         not null,
    title      varchar(255) not null,
    position   serial       not null,
    created_at timestamp(3),
    updated_at timestamp(3),
    primary key (id)
);