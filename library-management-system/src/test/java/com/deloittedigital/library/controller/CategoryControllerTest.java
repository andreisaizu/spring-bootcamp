package com.deloittedigital.library.controller;

import com.deloittedigital.library.model.domain.Category;
import com.deloittedigital.library.model.dto.ApiError;
import com.deloittedigital.library.model.dto.CategoryDTO;
import com.deloittedigital.library.service.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.deloittedigital.library.exception.ErrorType.CATEGORY_NOT_FOUND;
import static com.deloittedigital.library.service.local.LocalCategoryService.categoryList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ICategoryService iCategoryService;

    private static ObjectMapper objectMapper;
    private static ModelMapper modelMapper;

    @BeforeAll
    static void setUp() {
        modelMapper = new ModelMapper();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    @Test
    void testCategoryList() throws Exception {
        when(iCategoryService.getAll()).thenReturn(categoryList);
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isNotEmpty());
    }

    @Test
    void testGetCategoryById_happyPath() throws Exception {
        Category category = categoryList.get(0);
        CategoryDTO expectedCategory = modelMapper.map(category, CategoryDTO.class);

        when(iCategoryService.get(1L)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCategory)));

    }

    @Test
    void testGetCategoryById_whenCategoryNotFound_throwsException() throws Exception {
        ApiError expectedResponse = ApiError.builder()
                .errorCode(CATEGORY_NOT_FOUND.getErrorCode())
                .reason(CATEGORY_NOT_FOUND.getReason())
                .build();
        when(iCategoryService.get(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void testUpdateCategory_happyPath() throws Exception {
        Category category = categoryList.get(0);
        CategoryDTO inputCategory = modelMapper.map(category, CategoryDTO.class);

        Category updatedCategory = categoryList.get(1);
        CategoryDTO expectedCategory = modelMapper.map(updatedCategory, CategoryDTO.class);

        when(iCategoryService.get(1L)).thenReturn(Optional.of(category));
        when(iCategoryService.update(any(Category.class))).thenReturn(updatedCategory);

        mockMvc.perform(
                put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputCategory)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCategory)));

    }

    @Test
    void testUpdateCategory_whenCategoryNotFound_throwsException() throws Exception {
        ApiError expectedResponse = ApiError.builder()
                .errorCode(CATEGORY_NOT_FOUND.getErrorCode())
                .reason(CATEGORY_NOT_FOUND.getReason())
                .build();
        when(iCategoryService.get(1L)).thenReturn(Optional.empty());

        mockMvc.perform(
                put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CategoryDTO())))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void testDeleteCategory_happyPath() throws Exception {
        Category category = categoryList.get(0);

        when(iCategoryService.get(1L)).thenReturn(Optional.of(category));
        doNothing().when(iCategoryService).delete(category);

        mockMvc.perform(
                delete("/categories/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    void testDeleteCategory_whenCategoryNotFound_throwsException() throws Exception {
        when(iCategoryService.get(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNotFound());
    }
}