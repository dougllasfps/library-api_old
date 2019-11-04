package me.cursodsousa.libraryapi.api.dto;

import me.cursodsousa.libraryapi.model.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookDTOConverter implements DTOConverter<Book, BookDTO> {

    @Override
    public Book toEntity(BookDTO dto) {
        return Book
                    .builder()
                    .id(dto.getId())
                    .author(dto.getAuthor())
                    .isbn(dto.getIsbn())
                    .title(dto.getTitle())
                    .build();
    }

    @Override
    public BookDTO toDTO(Book book) {
        return BookDTO
                .builder()
                .id(book.getId())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .build();
    }
}
