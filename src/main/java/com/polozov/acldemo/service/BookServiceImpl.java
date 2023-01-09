package com.polozov.acldemo.service;

import com.polozov.acldemo.model.Book;
import com.polozov.acldemo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @Override
    public Book getById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}
