package me.cursodsousa.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.cursodsousa.libraryapi.api.dto.BookDTO;
import me.cursodsousa.libraryapi.api.dto.BookDTOConverter;
import me.cursodsousa.libraryapi.exception.BusinessException;
import me.cursodsousa.libraryapi.model.entity.Book;
import me.cursodsousa.libraryapi.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    static final String API_ROUTE = "/api/books";

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService service;

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    public void creatingAValidBookTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(validBook());

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(validBook());

        MockHttpServletRequestBuilder request = post(API_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform( request )
            .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Deve ocorrer erro ao tentar salvar um livro com ISBN já cadastrado ")
    public void testCreatingDuplicatedISBN() throws Exception {
        String json = new ObjectMapper().writeValueAsString(validDto());

        String errorMessage = "Já existe um livro cadastrado para o ISBN informado.";
        BDDMockito
                .given(service.save(Mockito.any(Book.class)))
                .willThrow(new BusinessException(errorMessage));

        MockHttpServletRequestBuilder request =
                 post(API_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform( request )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errors", hasSize(1)) )
            .andExpect(jsonPath("errors[0]").value(errorMessage))
            ;

    }

    @Test
    @DisplayName("Deve ocorrer erro de validação ao tentar salvar um livro inválido")
    public void invalidBookCreatingTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(BookDTO.builder().build());

        MockHttpServletRequestBuilder request =
                post(API_ROUTE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json);

        mvc
                .perform( request )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)) )
        ;

    }

    @Test
    @DisplayName("Deve atualizar um livro válido ")
    public void updatingAValidBookTest() throws Exception {
        Book book = validBook();
        BookDTO dto = validDto();

        BDDMockito.given( service.getById(Mockito.anyLong()))
                  .willReturn(Optional.of(book));

        BDDMockito.given( service.update(book) )
                .willReturn(book);

        String json = new ObjectMapper().writeValueAsString(dto);
        MockHttpServletRequestBuilder request =
                put(API_ROUTE.concat("/1"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json);
        mvc
            .perform( request )
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(dto.getId()) )
            .andExpect(jsonPath("title").value(dto.getTitle()) )
            .andExpect(jsonPath("author").value(dto.getAuthor()) )
            .andExpect(jsonPath("isbn").value(dto.getIsbn()) )
        ;
    }

    @Test
    @DisplayName("Deve ocorrer erro ao tentar atualizar um livro inexistente")
    public void inexistentBookUpdateTest() throws Exception{
        BookDTO dto = validDto();
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = put(API_ROUTE.concat("/1"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(jsonPath("errors", hasSize(1)))
            .andExpect(jsonPath("errors", contains("Livro não encontrado para o id informado")))
        ;
    }


    private BookDTO validDto() {
        return BookDTO.builder().title("A").author("A").isbn("A").build();
    }

    private Book validBook() {
        return Book.builder().title("A").author("A").isbn("A").build();
    }

}