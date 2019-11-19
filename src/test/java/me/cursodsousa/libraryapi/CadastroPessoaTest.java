package me.cursodsousa.libraryapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CadastroPessoaTest {

    @Test
    @DisplayName("Deve criar um cadastro de pessoas")
    public void criarCadastroTest(){
        CadastroPessoas cadastro = new CadastroPessoas();
        assertThat(cadastro.getPessoas()).isEmpty();
    }

    @Test
    @DisplayName("Deve cadastrar uma pessoa com sucesso.")
    public void cadastrarPessoaTest(){

        CadastroPessoas cadastro = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Zé");
        cadastro.cadastrar(pessoa);

        assertThat(cadastro.getPessoas())
                .isNotEmpty()
                .hasSize(1)
                .contains(pessoa);
    }

    @Test
    @DisplayName("Deve lançar erro ao cadastrar uma pessoa sem informacoes.")
    public void cadastrarPessoaInformacoesInvalidasTest(){

        CadastroPessoas cadastro = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();
        Throwable error = catchThrowable(() -> cadastro.cadastrar(pessoa));

        assertThat(error)
                .isInstanceOf(CadastroInvalido.class)
                .hasMessage("Informe o nome da pessoa.");
    }

    @Test
    @DisplayName("Deve remover uma pessoa do cadastro")
    public void removerPessoaTest(){

        CadastroPessoas cadastro = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Zé");
        cadastro.cadastrar(pessoa);

        cadastro.remover(pessoa);

        assertThat(cadastro.getPessoas()).isEmpty();

    }

}
