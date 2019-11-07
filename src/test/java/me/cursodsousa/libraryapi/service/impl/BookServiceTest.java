package me.cursodsousa.libraryapi.service.impl;

import me.cursodsousa.libraryapi.exception.BusinessException;
import me.cursodsousa.libraryapi.model.entity.Book;
import me.cursodsousa.libraryapi.model.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
public class BookServiceTest {

    @SpyBean
    BookServiceImpl service;

    @MockBean
    BookRepository repository;

    @Autowired
    MessageSource messageSource;

    Book validBook;

    @BeforeEach
    public void beforeEach(){
        validBook = Book.builder().title("A").author("A").isbn("A").build();
    }

    @Test
    @DisplayName("Deve salvar um livro com sucesso")
    public void shouldSaveABook(){

        when(repository.findByIsbn(validBook.getIsbn())).thenReturn(Optional.empty());


        when(repository.save(validBook)).thenReturn(
                Book.builder()
                        .author(validBook.getAuthor())
                        .isbn(validBook.getIsbn())
                        .title(validBook.getTitle())
                        .id(1l).build()
        );

        Book book = service.save(validBook);

        assertThat(book.getId()).isNotNull();
        assertThat(book.getTitle()).isEqualTo(validBook.getTitle());
        assertThat(book.getIsbn()).isEqualTo(validBook.getIsbn());
        assertThat(book.getAuthor()).isEqualTo(validBook.getAuthor());
    }

    @Test
    @DisplayName("Deve lancar erro quando já houver um livro cadastrado para o mesmo isbn")
    public void shouldNotSaveABookWhenISBNIsAlreadyUsed(){
        when(repository.findByIsbn(validBook.getIsbn())).thenReturn(Optional.of(Book.builder().build()));

        Throwable error = catchThrowable(() -> service.save(validBook));
        String errorMessage = messageSource.getMessage("book.isbn.duplicado", null, null);
        assertThat(error).isInstanceOf(BusinessException.class).hasMessage(errorMessage);
    }

}