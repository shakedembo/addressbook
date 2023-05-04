package com.example.demo.addressbook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;


public record KafkaMessage<T>(
        @JsonProperty T data,

        @JsonProperty
        OffsetDateTime pit,
        @JsonProperty String command,
        @JsonProperty CommandResult result,
        @JsonProperty String comment) {

}
