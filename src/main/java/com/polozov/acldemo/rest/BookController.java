package com.polozov.acldemo.rest;

import com.polozov.acldemo.model.Book;
import com.polozov.acldemo.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/book/{id}")
    public Book getById(@PathVariable("id") Integer id) {
        return bookService.getById(id);
    }

    @PreAuthorize("hasPermission(#book, 'WRITE')")
    @PutMapping("/book/{id}")
    public Book edit(@PathVariable("id") Integer id, @RequestBody Book book) {
        book.setId(id);
        return bookService.save(book);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/book")
    public Book post(@RequestBody Book book) {
        return bookService.save(book);
    }
}
