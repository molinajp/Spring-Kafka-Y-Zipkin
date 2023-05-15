package com.example.utils;

import com.example.dto.category.CategoryDTO;
import com.example.entity.CategoryEntity;

public final class TestUtils {

    public static final String CATEGORY_NAME = "Category1";
    public static final String CATEGORY_DESCRIPTION = "category description";

    public static CategoryDTO buildCategoryDTO() {
        final CategoryDTO dto = new CategoryDTO();
        dto.setName(CATEGORY_NAME);
        dto.setDescription(CATEGORY_DESCRIPTION);
        return dto;
    }
    
    public static CategoryEntity buildCategoryEntity() {
        final CategoryEntity entity = new CategoryEntity();
        entity.setId(1L);
        entity.setName(CATEGORY_NAME);
        entity.setDescription(CATEGORY_DESCRIPTION);
        return entity;
    }

}
