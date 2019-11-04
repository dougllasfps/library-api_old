package me.cursodsousa.libraryapi.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.cursodsousa.libraryapi.api.dto.BookDTO;
import me.cursodsousa.libraryapi.api.dto.BookDTOConverter;
import me.cursodsousa.libraryapi.service.BookService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    BookDTOConverter dtoConverter;

    @Test
    public void shouldCreateABook() throws Exception {
        String json = new ObjectMapper().writeValueAsString(BookDTO.builder().title("A").author("A").isbn("A").build());

        MockHttpServletRequestBuilder request = post(API_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform( request )
            .andExpect(status().isCreated());

    }

    @Test
    public void shouldGiveErrorWhenSendingAInvalidBook() throws Exception {
        String json = new ObjectMapper().writeValueAsString(BookDTO.builder().build());

        MockHttpServletRequestBuilder request =
                 post(API_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform( request )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", Is.is("Title is Mandatory")) )
                ;

    }
}