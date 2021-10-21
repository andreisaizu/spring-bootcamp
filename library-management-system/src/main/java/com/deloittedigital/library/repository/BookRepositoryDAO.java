package com.deloittedigital.library.repository;

import com.deloittedigital.library.model.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class BookRepositoryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Book create(Book book) {
        entityManager.persist(book);
        entityManager.flush();
        return book;
    }

    @Transactional
    public Book update(Book book) {
        entityManager.merge(book);
        entityManager.flush();
        return book;
    }

    @Transactional
    public void delete(Long id) {
        Book foundBook = findById(id);
        if(foundBook != null) {
            entityManager.remove(foundBook);
            entityManager.flush();
        }
    }

    public Book findById(Long id) {
        return entityManager.find(Book.class, id);
    }

    public Book findBookByTitle(String title) {
        return entityManager.createQuery("Select b from Book b where b.title like :title", Book.class)
                .setParameter("title", "%" + title + "%").getSingleResult();

    }

    public Book findBookByISBN(String isbn) {
        return entityManager.createQuery("Select b from Book b where b.ISBN like :isbn", Book.class)
                .setParameter("isbn", isbn).getSingleResult();

    }

    public List<Book> getAvailableBooks() {
        return entityManager.createQuery("Select b from Book b where b.availableCopies > 0", Book.class).getResultList();
    }


}


