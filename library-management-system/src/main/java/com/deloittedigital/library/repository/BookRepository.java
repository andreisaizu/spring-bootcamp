package com.deloittedigital.library.repository;

import com.deloittedigital.library.model.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthor(String author); //Simple Derived Query

    List<Book> findByAuthorAndPublishedDateBefore(String author, LocalDate publishedDate); //Multiple clauses

    List<Book> findByTitleIgnoreCaseStartingWith(String title); //Operators for String parameters

    List<Book> findByAvailableCopiesGreaterThan(Integer copies); //Comparison Condition

    List<Book> findByCategoryIdOrderByTitle(Long categoryId); //Sorting Derived Query Results

    @Query(value = "Select b from Book b where b.language = ?1")
    List<Book> queryAllBooksByLanguage(String language);

    @Query(value = "Select b from Book b where b.availableCopies > 0 and b.author like %:author")
    List<Book> queryAllAvailableBooksByAuthor(String author);

    @Query(value = "Select * from books where publisher = :publisher",
            nativeQuery = true)
    List<Book> queryAllBooksByPublisher(String publisher);

    @Query(value = "Select * from books where id in (select book_id from borrows where user_id = ?1)",
            nativeQuery = true)
    List<Book> queryAllBooksBorrowedByUserId(Long userId);

}

