package com.redundantcich.crudauto.service;

import com.redundantcich.crudauto.config.Config;
import com.redundantcich.crudauto.model.Book;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

import java.util.List;

public class BookService {

    private final String baseUrl;
    private final String user;
    private final String password;
    private final String booksEndpoint;

    public BookService() {
        this.baseUrl = Config.getBaseUrl();
        this.user = Config.getApiUser();
        this.password = Config.getApiPass();
        this.booksEndpoint = Config.getBooksEndpoint();
    }

    public Response createBookOnly(Book book) {
        return spec().body(book).post(baseUrl + booksEndpoint);
    }

    public Book createBookAndReturn(Book book) {
        Response response = createBookOnly(book);
        response.then().statusCode(201);

        String generatedId = response.jsonPath().getString("id");
        book.setId(generatedId);
        return book;
    }

    public Response getBook(String bookId) {
        String url = baseUrl + Config.getBookByIdEndpoint(bookId);
        return spec()
                .get(url);
    }

    public Response updateBook(Book book) {
        String url = baseUrl + Config.getBookByIdEndpoint(book.getId());
        return spec()
                .contentType("application/json")
                .body(book)
                .put(url);
    }

    public Response deleteBook(Book book) {
        String url = baseUrl + Config.getBookByIdEndpoint(book.getId());
        return spec()
                .delete(url);
    }

    public List<Book> listBooks() {
        String url = baseUrl + booksEndpoint;
        return spec()
                .get(url)
                .then()
                .extract().body().jsonPath().getList("", Book.class);
    }


    private RequestSpecification spec() {
        return SerenityRest.given()
                .auth().preemptive().basic(user, password);
    }
}
