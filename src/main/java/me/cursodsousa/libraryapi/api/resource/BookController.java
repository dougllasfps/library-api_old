package me.cursodsousa.libraryapi.api.resource;

import me.cursodsousa.libraryapi.api.dto.BookDTO;
import me.cursodsousa.libraryapi.api.dto.BookDTOConverter;
import me.cursodsousa.libraryapi.api.dto.DTOConverter;
import me.cursodsousa.libraryapi.api.response.ErrorResponse;
import me.cursodsousa.libraryapi.model.entity.Book;
import me.cursodsousa.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static me.cursodsousa.libraryapi.api.exception.CustomResponseStatusException.badRequest;
import static me.cursodsousa.libraryapi.api.exception.CustomResponseStatusException.notFound;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;
    private BookDTOConverter dtoConverter;

    public BookController(BookService service) {
        this.service = service;
        dtoConverter = new BookDTOConverter();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create( @Valid @RequestBody BookDTO dto ){
        Book book = dtoConverter.toEntity(dto);
        book = service.save(book);
        return dtoConverter.toDTO(book);
    }

    @PutMapping("{id}")
    public BookDTO update( @PathVariable Long id, @Valid @RequestBody BookDTO dto ){
        return service.getById(id).map( book -> {
            Book entity = dtoConverter.toEntity(dto);
            entity.setId(book.getId());
            entity = service.update(entity);
            return dtoConverter.toDTO(entity);
        }).orElseThrow(() ->  badRequest(new ErrorResponse("Livro não encontrado para o id informado")));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Long id ){
        service.delete(id);
    }

    @GetMapping("{id}")
    public BookDTO getById( @PathVariable Long id ){
        return service.getById(id)
                .map( b -> dtoConverter.toDTO(b) )
                .orElseThrow(() -> notFound( new ErrorResponse("Entidade não encontrada") ));
    }

}
