package com.polozov.acldemo.service;

import com.polozov.acldemo.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book getById(Integer id);

    Book save(Book book);
}
