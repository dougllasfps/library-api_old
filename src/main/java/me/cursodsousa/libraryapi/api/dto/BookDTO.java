package me.cursodsousa.libraryapi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    @NotEmpty(message = "{book.title.required}")
    private String title;

    @NotEmpty(message = "{book.isbn.required}")
    private String isbn;

    @NotEmpty(message = "{book.author.required}")
    private String author;
}
