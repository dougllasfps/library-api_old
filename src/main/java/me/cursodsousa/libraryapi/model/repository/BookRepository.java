package me.cursodsousa.libraryapi.model.repository;

import me.cursodsousa.libraryapi.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
