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

    public static CustomResponseStatusException badRequest(Object content){
        return new CustomResponseStatusException(content, HttpStatus.BAD_REQUEST);
    }

    public static CustomResponseStatusException notFound(Object content){
        return new CustomResponseStatusException(content, HttpStatus.NOT_FOUND);
    }
}
