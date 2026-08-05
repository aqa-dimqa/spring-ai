package com.example.springai.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Db {

        public static final String APP_SCHEMA = "app";
        public static final String SPACE_TABLE = "spaces";
        public static final String CHAT_TABLE = "chats";
        public static final String DOCUMENT_TABLE = "documents";
        public static final String MESSAGE_TABLE = "chat_messages";

    }

}
