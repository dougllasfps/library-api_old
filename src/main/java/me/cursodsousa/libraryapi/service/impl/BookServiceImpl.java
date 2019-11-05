package me.cursodsousa.libraryapi.service.impl;

import lombok.RequiredArgsConstructor;
import me.cursodsousa.libraryapi.model.entity.Book;
import me.cursodsousa.libraryapi.model.repository.BookRepository;
import me.cursodsousa.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    @Transactional
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    @Transactional
    public Book update(Book book) {
        return repository.save(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return repository.findById(id);
    }
}
