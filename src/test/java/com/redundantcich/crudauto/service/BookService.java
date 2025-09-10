package com.redundantcich.crudauto.service;

import com.redundantcich.crudauto.config.Config;
import com.redundantcich.crudauto.model.Book;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

public class BookService {

    private final String user;
    private final String password;

    private final String baseUrl;
    private final String fullBooksUrl;

    public BookService() {
        this.user = Config.getApiUser();
        this.password = Config.getApiPass();

        this.baseUrl = Config.getBaseUrl();
        String booksEndpoint = Config.getBooksEndpoint();
        this.fullBooksUrl = baseUrl + booksEndpoint;
    }

    public Response createBook(Book book) {
        return authenticated()
                .contentType("application/json")
                .body(book)
                .post(fullBooksUrl);
    }

    public Response getBook(String bookId) {
        String url = baseUrl + Config.getBookByIdEndpoint(bookId);
        return authenticated()
                .get(url);
    }

    public Response updateBook(Book book) {
        String url = baseUrl + Config.getBookByIdEndpoint(book.getId());
        return authenticated()
                .contentType("application/json")
                .body(book)
                .put(url);
    }

    public Response deleteBook(Book book) {
        String url = baseUrl + Config.getBookByIdEndpoint(book.getId());
        return authenticated()
                .delete(url);
    }

    public Response listBooks() {
        return authenticated()
                .get(fullBooksUrl);
    }

    private RequestSpecification authenticated() {
        return SerenityRest.given()
                .auth().basic(user, password);
    }
}
