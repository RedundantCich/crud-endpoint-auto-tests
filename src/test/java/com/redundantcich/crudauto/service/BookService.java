package com.redundantcich.crudauto.service;

import com.redundantcich.crudauto.config.Config;
import com.redundantcich.crudauto.model.Book;
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

    public void createBook(Book book) {
        authenticated()
                .contentType("application/json")
                .body(book)
                .post(fullBooksUrl);
    }

    public void getBook(String bookId) {
        String endpointWithId = baseUrl + Config.getBookByIdEndpoint(bookId);
        authenticated()
                .get(endpointWithId);
    }

    public void updateBook(Book book) {
        String endpointWithId = baseUrl + Config.getBookByIdEndpoint(book.getId());
        authenticated()
                .contentType("application/json")
                .body(book)
                .put(endpointWithId);
    }

    public void deleteBook(Book book) {
        String endpointWithId = baseUrl + Config.getBookByIdEndpoint(book.getId());
        authenticated()
                .delete(endpointWithId);
    }

    public void listBooks() {
        authenticated()
                .get(fullBooksUrl);
    }

    private RequestSpecification authenticated() {
        return SerenityRest.given()
                .auth().basic(user, password);
    }
}
