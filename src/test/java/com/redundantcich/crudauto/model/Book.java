package com.redundantcich.crudauto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    private String id; // null initially; created server-side;
    private String name;
    private String author;
    private String publication;
    private String category;
    private int pages;
    private float price;
}