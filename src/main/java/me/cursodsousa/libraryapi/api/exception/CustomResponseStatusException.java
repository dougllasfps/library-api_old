package me.cursodsousa.libraryapi.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomResponseStatusException extends RuntimeException{

    private Object content;
    private HttpStatus status;

    public CustomResponseStatusException(Object content, HttpStatus status) {
        this.content = content;
        this.status = status;
    }
}
