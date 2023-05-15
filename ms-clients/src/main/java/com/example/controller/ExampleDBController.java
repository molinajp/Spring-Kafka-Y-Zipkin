package com.example.controller;

import com.example.dto.category.CategoryDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "ExampleDB")
public interface ExampleDBController {

    @PostMapping(path = "/categories")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDTO.class))))
    ResponseEntity<CategoryDTO> createCategoryController(CategoryDTO categoryDTO);

    @DeleteMapping(path = "/categories/{id}")
    @ApiResponses(value = @ApiResponse(responseCode = "202", description = "OK"))
    ResponseEntity<Long> deleteCategoryController(Long id);

    @GetMapping(path = "/categories/{id}")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDTO.class))))
    ResponseEntity<CategoryDTO> getCategoryController(Long id);

    @PutMapping(path = "/categories/{id}")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDTO.class))))
    ResponseEntity<CategoryDTO> editCategoryController(CategoryDTO categoryEntity, Long id);

}
