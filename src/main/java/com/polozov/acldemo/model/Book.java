package com.polozov.acldemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book{

    @Id
    private Integer id;

    private String name;

    public Book(String name) {
        this.name = name;
    }
}
