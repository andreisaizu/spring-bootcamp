package com.deloittedigital.library.service;

import com.deloittedigital.library.model.domain.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAll();

    Optional<Category> get(Long id);

    Category add(Category category);

    Category update(Category category);

    void delete(Category category);
}
