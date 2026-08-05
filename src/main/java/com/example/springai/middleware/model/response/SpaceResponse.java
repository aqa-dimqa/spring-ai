package com.example.springai.middleware.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record SpaceResponse(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("user_id")
        UUID userId,

        @JsonProperty("title")
        String title,

        @JsonProperty("active")
        boolean isActive,

        @JsonProperty("chats")
        List<ChatShortResponse> chats,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt

) {

    @Builder
    @JsonCreator
    public SpaceResponse {
        if (chats == null)
            chats = new ArrayList<>();
    }

}
