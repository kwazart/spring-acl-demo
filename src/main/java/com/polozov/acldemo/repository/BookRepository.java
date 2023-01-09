package com.polozov.acldemo.repository;

import com.polozov.acldemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
