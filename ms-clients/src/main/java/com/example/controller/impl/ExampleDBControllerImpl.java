package com.example.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.ExampleDBController;
import com.example.dto.category.CategoryDTO;
import com.example.service.ExampleDBService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExampleDBControllerImpl implements ExampleDBController {

    private final ExampleDBService exampleDBService;

    @Override
    public ResponseEntity<CategoryDTO> createCategoryController(@RequestBody @Valid CategoryDTO categoryDTO) {
        return new ResponseEntity<>(exampleDBService.addCategory(categoryDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> deleteCategoryController(@PathVariable @NotBlank Long id) {
        exampleDBService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryController(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(exampleDBService.getCategory(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryDTO> editCategoryController(@RequestBody @Valid CategoryDTO categoryEntity,
            @PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(exampleDBService.putCategory(id, categoryEntity), HttpStatus.OK);
    }

}
