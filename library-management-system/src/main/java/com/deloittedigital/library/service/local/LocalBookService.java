package com.deloittedigital.library.service.local;

import com.deloittedigital.library.exception.InvalidISBNException;
import com.deloittedigital.library.model.domain.Book;
import com.deloittedigital.library.model.domain.Category;
import com.deloittedigital.library.model.dto.SortFieldDTO;
import com.deloittedigital.library.service.IBookService;
import com.deloittedigital.library.service.ICategoryService;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.deloittedigital.library.exception.Constants.CATEGORY_NOT_FOUND;
import static com.deloittedigital.library.service.local.LocalCategoryService.categoryList;
import static java.util.Objects.isNull;

@Service
public class LocalBookService implements IBookService {
    private final ICategoryService categoryService;
    private List<Book> bookList;

    public LocalBookService(ICategoryService localCategoryService) {
        this.categoryService = localCategoryService;
        this.bookList = initBookList();
    }


    private List<Book> initBookList() {
        List<Book> books = new ArrayList<>();

        Book book1 = new Book();
        book1.setTitle("Murder on the Orient Express");
        book1.setAuthor("Agatha Christie");
        book1.setPublisher("Grove Press");
        book1.setLanguage("English");
        book1.setPublishedDate(LocalDate.of(2015, 1, 1));
        book1.setPages(300);
        book1.setNrCopies(5);
        book1.setBorrowDays(6);
        book1.setIsbn("9780008268879");
        book1.setCategory(categoryService.get(0L).get());
        books.add(book1);

        Book book2 = new Book();
        book2.setTitle("And Then There Were None");
        book2.setAuthor("Agatha Christie");
        book2.setPublisher("Harper");
        book2.setLanguage("English");
        book2.setPublishedDate(LocalDate.of(2011, 6, 30));
        book2.setPages(240);
        book2.setNrCopies(4);
        book2.setBorrowDays(5);
        book2.setIsbn("0062073486");
        book2.setCategory(categoryService.get(0L).get());
        books.add(book2);

        Book book3 = new Book();
        book3.setTitle("Pride and Prejudice");
        book3.setAuthor("Jane Austen");
        book3.setPublisher("Wordsworth");
        book3.setLanguage("English");
        book3.setPublishedDate(LocalDate.of(1992, 6, 16));
        book3.setPages(352);
        book3.setNrCopies(4);
        book3.setBorrowDays(6);
        book3.setIsbn("9781853260001");
        book3.setCategory(categoryService.get(1L).get());
        books.add(book3);

        Book book4 = new Book();
        book4.setTitle("Anna Karenina");
        book4.setAuthor("Lev Tolstoi");
        book4.setPublisher("Polirom");
        book4.setLanguage("English");
        book4.setPublishedDate(LocalDate.of(2016, 6, 12));
        book4.setPages(936);
        book4.setNrCopies(2);
        book4.setBorrowDays(10);
        book4.setIsbn("9789734627943");
        book4.setCategory(categoryService.get(1L).get());
        books.add(book4);

        Book book5 = new Book();
        book5.setTitle("Bird Box: Orbeste");
        book5.setAuthor("Josh Malerman");
        book5.setPublisher("Corint");
        book5.setLanguage("Romanian");
        book5.setPublishedDate(LocalDate.of(2019, 9, 15));
        book5.setPages(352);
        book5.setNrCopies(4);
        book5.setBorrowDays(5);
        book5.setIsbn("9786067935646");
        book5.setCategory(categoryList.get(2));
        books.add(book5);
        return books;
    }


    public List<Book> getAll(String author, String publisher, String title, String language, LocalDate published, Boolean available, Integer page, SortFieldDTO sorted) {
        return bookList.stream()
                .filter(book -> {
                            if (author != null) {
                                return book.getAuthor().equals(author);
                            } else {
                                return true;
                            }
                        }
                )
                .filter(book -> {
                            if (publisher != null) {
                                return book.getPublisher().equals(publisher);
                            } else {
                                return true;
                            }
                        }
                )
                .filter(book -> {
                            if (title != null) {
                                return book.getTitle().equals(title);
                            } else {
                                return true;
                            }
                        }
                )
                .filter(book -> {
                            if (language != null) {
                                return book.getLanguage().equals(language);
                            } else {
                                return true;
                            }
                        }
                )
                .filter(book -> {
                            if (available != null) {
                                if (available) {
                                    return book.getAvailableCopies() > 0;
                                } else {
                                    return book.getAvailableCopies() == 0;
                                }
                            } else {
                                return true;
                            }
                        }
                )
                .filter(book -> {
                            if (published != null) {
                                return book.getPublishedDate().isEqual(published);
                            } else {
                                return true;
                            }
                        }
                )
                .collect(Collectors.toList());
    }

    public Optional<Book> get(Long id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book add(Long categoryId, Book book) {
        Category category = categoryService.get(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CATEGORY_NOT_FOUND));
        book.setCategory(category);
        ISBNValidator isbnValidator = new ISBNValidator();
        if (!isbnValidator.isValid(book.getIsbn())) {
            throw new InvalidISBNException();
        }

        if (isNull(book.getAvailableCopies())) {
            book.setAvailableCopies(book.getNrCopies());
        }
        book.setId(bookList.size() + 1L);
        bookList.add(book);

        return book;
    }

    public Book update(Book book, Category category) {

        Book foundBook = bookList.stream().filter(b -> b.getId().equals(book.getId())).findFirst().get();
        int foundBookIndex = bookList.indexOf(foundBook);

        foundBook.setCategory(category);

        bookList.set(foundBookIndex, foundBook);

        return foundBook;
    }

    public void delete(Book book) {
        bookList.remove(book);
    }

    public List<Book> getAllBooksBorrowedByUser(Long userId) {
        return bookList.stream()
                .filter(book -> book.getBorrows().stream()
                        .anyMatch(borrow -> borrow.getUser().getId().equals(userId)))
                .collect(Collectors.toList());
    }

    public List<Book> getAvailableBooks() {
        return bookList.stream()
                .filter(book -> book.getAvailableCopies() > 0)
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooksByCategory(Long categoryId) {
        return bookList.stream()
                .filter(book -> book.getCategory().getId().equals(categoryId))
                .collect(Collectors.toList());
    }
}
