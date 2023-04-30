package com.example.demo.addressbook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;


public record KafkaMessage<T>(
        @JsonProperty T data,
        @JsonProperty LocalDateTime pit,
        @JsonProperty String command,
        @JsonProperty CommandResult result,
        @JsonProperty String comment) {

}
