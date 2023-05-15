package com.example.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    @NotBlank(message = "The category name is mandatory")
    private String name;
    @NotBlank(message = "The category description is mandatory")
    private String description;

}
