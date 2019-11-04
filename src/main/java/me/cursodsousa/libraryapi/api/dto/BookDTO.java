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

    @NotEmpty(message = "{field.title.required}")
    private String title;

    @NotEmpty(message = "{field.isbn.required}")
    private String isbn;

    @NotEmpty(message = "{field.author.required}")
    private String author;
}
