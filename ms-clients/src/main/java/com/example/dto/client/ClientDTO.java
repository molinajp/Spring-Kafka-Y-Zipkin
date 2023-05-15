package com.example.dto.client;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record ClientDTO(@NotBlank(message = "The first name is mandatory") String name,
                        @NotBlank(message = "The lastname is mandatory") String lastName,
                        @NotBlank(message = "The DNI is mandatory") String dni) {
}