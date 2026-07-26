package com.example.springai.middleware.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record ChatRequest(

        @NotNull
        @JsonProperty("user_id")
        UUID userId,

        @JsonProperty("title")
        String title

) implements Serializable {
}
