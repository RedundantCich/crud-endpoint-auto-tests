package com.redundantcich.crudauto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String id; // null initially; created server-side;
    private String name;
    private String author;
    private String publication;
    private String category;
    private int pages;
    private float price;
}