package me.cursodsousa.libraryapi.service.impl;

import lombok.RequiredArgsConstructor;
import me.cursodsousa.libraryapi.model.entity.Book;
import me.cursodsousa.libraryapi.model.repository.BookRepository;
import me.cursodsousa.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public Book update(Book book) {
        return repository.save(book);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }
}
