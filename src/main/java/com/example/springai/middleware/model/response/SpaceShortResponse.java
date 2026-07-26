package com.example.springai.middleware.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record SpaceShortResponse(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("title")
        String title,

        @JsonProperty("chats")
        List<ChatShortResponse> chats,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt

) implements Serializable {

}
