package me.cursodsousa.libraryapi.api.response;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ErrorResponse {

    private List<String> errors;

    public ErrorResponse(BindingResult result) {
        this();
        Optional.ofNullable(result).ifPresent( r -> {
            if(r.hasErrors()){
                r.getAllErrors().forEach( error -> this.addError(error.getDefaultMessage() ));
            }
        });
    }

    public ErrorResponse() {
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(String msg) {
        this();
        this.addError(msg);
    }

    public void addError(String error){
        this.errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }
}
