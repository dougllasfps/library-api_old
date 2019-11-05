package me.cursodsousa.libraryapi.service;

import me.cursodsousa.libraryapi.model.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    Book update(Book book);

    void delete(Long id);

    List<Book> findAll();

    Optional<Book> getById(Long id);
}
