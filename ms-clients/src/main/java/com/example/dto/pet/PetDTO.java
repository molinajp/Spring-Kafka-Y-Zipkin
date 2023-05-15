package com.example.dto.pet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDTO {

    private Long id;
    private CategoryDTO category;
    private String name;
    private List<String> photoUrls;
    private List<TagsDTO> tags;
    private String status;

}
