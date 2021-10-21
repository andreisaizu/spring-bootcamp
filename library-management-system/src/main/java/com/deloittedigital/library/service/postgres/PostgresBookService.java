package com.deloittedigital.library.service.postgres;

import com.deloittedigital.library.exception.InvalidISBNException;
import com.deloittedigital.library.model.domain.Book;
import com.deloittedigital.library.model.domain.Category;
import com.deloittedigital.library.model.dto.SortFieldDTO;
import com.deloittedigital.library.repository.BookRepository;
import com.deloittedigital.library.service.IBookService;
import com.deloittedigital.library.service.ICategoryService;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.deloittedigital.library.exception.Constants.CATEGORY_NOT_FOUND;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Profile("postgres")
@Service
public class PostgresBookService implements IBookService {

    private final BookRepository bookRepository;
    private final ICategoryService categoryService;

    public PostgresBookService(BookRepository bookRepository, ICategoryService categoryService) {
        this.categoryService = categoryService;
        this.bookRepository = bookRepository;
        this.bookRepository.deleteAll();
        this.saveBooks();
    }



    public List<Book> getAll(String author, String publisher, String title, String language, LocalDate published, Boolean available, Integer page, SortFieldDTO sorted) {
        if (!isEmpty(author) && published != null) {
            return bookRepository.findByAuthorAndPublishedDateBefore(author, published);
        }
        if (!isEmpty(author) && available != null && available) {
            return bookRepository.queryAllAvailableBooksByAuthor(author);
        }
        if (!isEmpty(author)) {
            return bookRepository.findByAuthor(author);
        }
        if (!isEmpty(publisher)) {
            return bookRepository.queryAllBooksByPublisher(publisher);
        }
        if (!isEmpty(title)) {
            return bookRepository.findByTitleIgnoreCaseStartingWith(title);
        }
        if (!isEmpty(language)) {
            return bookRepository.queryAllBooksByLanguage(language);
        }
        if (!isEmpty(page)) {
            Pageable pageable = PageRequest.of(page, 3); //The page number is 0 based!!
            return bookRepository.findAll(pageable).toList();
        }
        if (!isEmpty(sorted)) {
            return bookRepository.findAll(Sort.by(Sort.Direction.ASC, sorted.getName()));
        }
        return bookRepository.findAll();
    }

    public Optional<Book> get(Long id) {
        return bookRepository.findById(id);
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
        return bookRepository.save(book);
    }

    public Book update(Book book, Category category) {
        ISBNValidator isbnValidator = new ISBNValidator();
        if (!isbnValidator.isValid(book.getIsbn())) {
            throw new InvalidISBNException();
        }

        book.setCategory(category);

        return bookRepository.save(book);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public List<Book> getAllBooksBorrowedByUser(Long userId) {
        return bookRepository.queryAllBooksBorrowedByUserId(userId);
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }

    public List<Book> getAllBooksByCategory(Long categoryId) {
        return bookRepository.findByCategoryIdOrderByTitle(categoryId);
    }


    private List<Book> saveBooks() {
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
        book1.setCategory(categoryService.getAll().get(0));
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
        book2.setCategory(categoryService.getAll().get(0));
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
        book3.setCategory(categoryService.getAll().get(1));
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
        book4.setCategory(categoryService.getAll().get(1));
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
        book5.setCategory(categoryService.getAll().get(2));
        books.add(book5);

        return bookRepository.saveAll(books);
    }
}
