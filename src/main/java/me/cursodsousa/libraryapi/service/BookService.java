package me.cursodsousa.libraryapi.service;

import me.cursodsousa.libraryapi.model.entity.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    Book update(Book book);

    void delete(Long id);

    List<Book> findAll();

}
