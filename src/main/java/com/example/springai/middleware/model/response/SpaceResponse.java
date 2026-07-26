package com.example.springai.middleware.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record SpaceResponse(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("user_id")
        UUID userId,

        @JsonProperty("title")
        String title,

        @JsonProperty("position")
        int position,

        @JsonProperty("chats")
        List<ChatShortResponse> chats,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt

) implements Serializable {

}
