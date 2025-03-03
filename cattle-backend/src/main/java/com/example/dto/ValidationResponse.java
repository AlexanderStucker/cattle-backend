package com.example.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ValidationResponse(long id, boolean valid) {}
