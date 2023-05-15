package com.example.converter;

import com.example.dto.category.CategoryDTO;
import com.example.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryDTO entityToDto(CategoryEntity categoryEntity) {

        return CategoryDTO.builder().name(categoryEntity.getName()).description(categoryEntity.getDescription())
                .build();

    }

    public CategoryEntity dtoToEntity(CategoryDTO categoryDTO) {
        return CategoryEntity.builder().name(categoryDTO.getName()).description(categoryDTO.getDescription()).build();
    }
}
