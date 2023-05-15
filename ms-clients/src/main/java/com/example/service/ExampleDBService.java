package com.example.service;

import com.example.dto.category.CategoryDTO;

public interface ExampleDBService {

    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(Long id);

    CategoryDTO putCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);

}
