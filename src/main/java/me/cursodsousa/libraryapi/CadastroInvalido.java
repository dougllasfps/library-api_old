package me.cursodsousa.libraryapi;

public class CadastroInvalido extends RuntimeException{

    public CadastroInvalido(String message) {
        super(message);
    }
}
