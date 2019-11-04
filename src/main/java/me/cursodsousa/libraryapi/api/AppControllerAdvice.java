package me.cursodsousa.libraryapi.api;

import me.cursodsousa.libraryapi.api.exception.CustomResponseStatusException;
import me.cursodsousa.libraryapi.api.response.ErrorResponse;
import me.cursodsousa.libraryapi.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex ) {
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
