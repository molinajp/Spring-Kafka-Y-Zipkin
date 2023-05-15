package com.example.service.impl;

import com.example.converter.CategoryConverter;
import com.example.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.dto.category.CategoryDTO;
import com.example.service.ExampleDBService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExampleDBServiceImpl implements ExampleDBService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private static final String FAILURE_MESSAGE = "Category not found.";

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        categoryRepository.save(categoryConverter.dtoToEntity(categoryDTO));
        return categoryDTO;
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FAILURE_MESSAGE));
        return categoryConverter.entityToDto(category);
    }

    @Override
    public CategoryDTO putCategory(Long id, CategoryDTO categoryDTO) {
        var categoryEdit = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FAILURE_MESSAGE));
        categoryEdit.setName(categoryDTO.getName());
        categoryEdit.setDescription(categoryDTO.getDescription());
        categoryRepository.save(categoryEdit);
        return categoryDTO;
    }

    @Override
    public void deleteCategory(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, FAILURE_MESSAGE));
        categoryRepository.delete(category);
    }
}
