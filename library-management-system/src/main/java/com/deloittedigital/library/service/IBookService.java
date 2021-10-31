package com.deloittedigital.library.service;

import com.deloittedigital.library.model.domain.Book;
import com.deloittedigital.library.model.domain.Category;
import com.deloittedigital.library.model.dto.SortFieldDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> getAll(String author, String publisher, String title, String language, LocalDate published, Boolean available, Integer page, SortFieldDTO sorted);
    Optional<Book> get(Long id);
    Book add(Long categoryId, Book book);
    Book update(Book book, Category category);
    void delete(Book book);
    List<Book> getAllBooksBorrowedByUser(Long userId);
    List<Book> getAllBooksByCategory(Long categoryId);
}
