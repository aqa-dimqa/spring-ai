alter table app.spaces
    add constraint unique_user_chat_group_title
        unique (user_id, title);