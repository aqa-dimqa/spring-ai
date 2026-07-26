package com.example.springai.middleware.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record DocumentShortResponse(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("title")
        String title

) implements Serializable {
}
