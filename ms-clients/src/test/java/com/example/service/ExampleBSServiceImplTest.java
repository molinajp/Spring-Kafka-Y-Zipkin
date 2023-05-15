package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.converter.CategoryConverter;
import com.example.dto.category.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.repository.CategoryRepository;
import com.example.service.impl.ExampleDBServiceImpl;
import com.example.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class ExampleBSServiceImplTest {

    public static final String CATEGORY_NAME = "Category1";
    public static final String CATEGORY_DESCRIPTION = "category description";
    public CategoryDTO categoryDTO;
    public CategoryEntity categoryEntity;

    @InjectMocks
    ExampleDBServiceImpl exampleDBServiceImpl;

    @Mock
    CategoryConverter categoryConverter;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    public void initMocks() {
        categoryDTO = TestUtils.buildCategoryDTO();
        categoryEntity = TestUtils.buildCategoryEntity();
    }

    @DisplayName("Test for Add Category")
    @Test
    public void addCategoryTest() {

        doReturn(categoryEntity).when(categoryConverter).dtoToEntity(categoryDTO);
        doReturn(categoryEntity).when(categoryRepository).save(categoryEntity);

        var result = exampleDBServiceImpl.addCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(result.getName(), CATEGORY_NAME);
        assertEquals(result.getDescription(), CATEGORY_DESCRIPTION);

    }

    @DisplayName("Test for get Category")
    @Test()
    public void getCategoryTest() {

        doReturn(Optional.of(categoryEntity)).when(categoryRepository).findById(1L);
        doReturn(categoryDTO).when(categoryConverter).entityToDto(categoryEntity);

        var result = exampleDBServiceImpl.getCategory(1L);

        assertNotNull(result);
        assertEquals(result.getName(), CATEGORY_NAME);
        assertEquals(result.getDescription(), CATEGORY_DESCRIPTION);

    }

    @DisplayName("Test for put Category")
    @Test
    public void putCategoryTest() {

        var categoryDTOAlt = CategoryDTO.builder().name("Category Name2").description("Description 2").build();

        doReturn(Optional.of(categoryEntity)).when(categoryRepository).findById(1L);
        doReturn(categoryEntity).when(categoryRepository).save(categoryEntity);

        var result = exampleDBServiceImpl.putCategory(1L, categoryDTOAlt);

        assertNotNull(result);
        assertEquals(result.getName(), "Category Name2");
        assertEquals(result.getDescription(), "Description 2");

    }

    @DisplayName("Test for delete Category")
    @Test
    public void deleteCategoryTest() {

        var auxEntity = CategoryEntity.builder().id(2L).name("Aux Category").description("Aux Description").build();

        doReturn(Optional.of(auxEntity)).when(categoryRepository).findById(2L);

        exampleDBServiceImpl.deleteCategory(2L);

        verify(categoryRepository).delete(auxEntity);

    }

    @DisplayName("Test for exception")
    @Test
    public void exceptionTest() {

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not Found.")).when(categoryRepository)
                .findById(1L);

        assertThrows(ResponseStatusException.class, () -> exampleDBServiceImpl.getCategory(1L));

    }

}
