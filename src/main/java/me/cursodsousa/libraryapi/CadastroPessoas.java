package me.cursodsousa.libraryapi;

import java.util.ArrayList;
import java.util.List;

public class CadastroPessoas {

    private List<Pessoa> pessoas;

    public CadastroPessoas() {
        this.pessoas = new ArrayList<>();
    }

    public List<Pessoa> getPessoas() {
        return this.pessoas;
    }

    public void cadastrar(Pessoa pessoa) {
        if(pessoa.getNome() == null){
            throw new CadastroInvalido("Informe o nome da pessoa.");
        }
        this.pessoas.add(pessoa);
    }

    public void remover(Pessoa pessoa) {
        this.pessoas.remove(pessoa);
    }
}
