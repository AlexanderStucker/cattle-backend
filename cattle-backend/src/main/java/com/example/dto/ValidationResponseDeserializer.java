package com.example.dto;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ValidationResponseDeserializer extends JsonbDeserializer<ValidationResponse> {
    public ValidationResponseDeserializer() {
        super(ValidationResponse.class);
    }
}
