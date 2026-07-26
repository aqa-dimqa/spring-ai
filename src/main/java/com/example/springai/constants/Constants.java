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
        public static final String MESSAGE_TABLE = "chat_messages";
        public static final String DOCUMENT_TABLE = "documents";
        public static final String DOCUMENT_GROUP_TABLE = "document_groups";
        public static final String DOCUMENT_DOCUMENT_GROUP_TABLE = "document_document_groups";
        public static final String CHAT_DOCUMENT_GROUP_TABLE = "chat_document_groups";
        public static final String CHAT_DOCUMENT_TABLE = "chat_documents";

    }

}
