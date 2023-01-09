package com.polozov.acldemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polozov.acldemo.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Пользователь #1 может получать книгу #1")
    @Test
    @WithMockUser(username = "user1")
    public void user1ShouldGetBook1() throws Exception {

        Book b1 = new Book(1, "Book 1");
        mockMvc.perform(
                        get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(b1))));
    }

    @DisplayName("Пользователь #2 может получать книгу #2")
    @Test
    @WithMockUser(username = "user2")
    public void user2ShouldGetBook2() throws Exception {

        Book b2 = new Book(2, "Book 2");
        mockMvc.perform(
                        get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(b2))));
    }

    @DisplayName("Администор получает все книги")
    @Test
    @WithMockUser(username = "admin")
    public void adminShouldGetDocuments123() throws Exception {

        Book b1 = new Book(1, "Book 1");
        Book b2 = new Book(2, "Book 2");
        Book b3 = new Book(3, "Book 3");

        mockMvc.perform(
                        get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(b1, b2, b3))));

    }

    @DisplayName("Пользователь #1 может получать книгу #1 по идентификатору")
    @Test
    @WithMockUser(username = "user1")
    public void user1ShouldGetBook1WithPathVariable() throws Exception {

        Book b1 = new Book(1, "Book 1");
        mockMvc.perform(
                        get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(b1)));
    }

    @DisplayName("Пользователь #1 не может получать книгу #2")
    @Test
    @WithMockUser(username = "user1")
    public void user1ShouldNotGetBook2() throws Exception {

        mockMvc.perform(
                        get("/book/2"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Администратор может редактировать книгу")
    @Test
    @WithMockUser(username = "admin")
    public void adminShouldEditBook() throws Exception {

        Book b1 = new Book( 1,"Book 1 Edited");
        mockMvc.perform(
                        put("/book/1")
                                .content(objectMapper.writeValueAsString(b1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(b1)));
    }

    @DisplayName("Пользователь #1 не может редактировать книгу")
    @Test
    @WithMockUser(username = "user1")
    public void user1ShouldNotEditBook() throws Exception {

        Book b1 = new Book( 1,"Book 1 Edited");
        mockMvc.perform(
                        put("/book/1")
                                .content(objectMapper.writeValueAsString(b1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @DisplayName("Пользователь с ролью Администор может добавлять книги")
    @Test
    @WithMockUser(authorities = { "ROLE_ADMIN" })
    public void adminPostBook() throws Exception {

        Book b1 = new Book(4, "Book 4");
        mockMvc.perform(
                        post("/book")
                                .content(objectMapper.writeValueAsString(b1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(b1)));

    }
}
