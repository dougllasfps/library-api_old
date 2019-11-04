package me.cursodsousa.libraryapi.api.resource;

import lombok.RequiredArgsConstructor;
import me.cursodsousa.libraryapi.api.dto.BookDTO;
import me.cursodsousa.libraryapi.api.dto.BookDTOConverter;
import me.cursodsousa.libraryapi.api.exception.CustomResponseStatusException;
import me.cursodsousa.libraryapi.api.response.ErrorResponse;
import me.cursodsousa.libraryapi.exception.BusinessException;
import me.cursodsousa.libraryapi.model.entity.Book;
import me.cursodsousa.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
    private final BookDTOConverter dtoConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@Valid @RequestBody BookDTO dto){
        Book book = dtoConverter.toEntity(dto);
        book = service.save(book);
        return dtoConverter.toDTO(book);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions( MethodArgumentNotValidException ex ) {
        return new ErrorResponse(ex.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleBusinessExceptions( BusinessException ex ) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(CustomResponseStatusException.class)
    public ResponseEntity handleCustomResponseStatusException(CustomResponseStatusException ex ) {
        return new ResponseEntity(ex.getContent(), ex.getStatus());
    }
}