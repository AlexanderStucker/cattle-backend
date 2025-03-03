package com.example.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ValidationRequest(long id, String name, String description) {}
