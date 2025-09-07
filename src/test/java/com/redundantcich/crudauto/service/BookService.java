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
    private final String fullBooksUrl;

    public BookService() {
        this.baseUrl = Config.getBaseUrl();
        this.user = Config.getApiUser();
        this.password = Config.getApiPass();
        String booksEndpoint = Config.getBooksEndpoint();
        this.fullBooksUrl = baseUrl + booksEndpoint;
    }

    public Response createBook(Book book) {
        return authenticated()
                .body(book)
                .log().all()
                .post(fullBooksUrl);
    }

    public Response getBook(String bookId) {
        String url = baseUrl + Config.getBookByIdEndpoint(bookId);
        return authenticated()
                .log().all()
                .get(url);
    }

    public Response updateBook(Book book) {
        String url = baseUrl + Config.getBookByIdEndpoint(book.getId());
        return authenticated()
                .contentType("application/json")
                .body(book)
                .log().all()
                .put(url);
    }

    public Response deleteBook(Book book) {
        String url = baseUrl + Config.getBookByIdEndpoint(book.getId());
        return authenticated()
                .log().all()
                .delete(url);
    }

    public List<Book> listBooks() {
        return authenticated()
                .log().all()
                .get(fullBooksUrl)
                .then()
                .log().all()
                .extract().body().jsonPath().getList("", Book.class);
    }

    private RequestSpecification authenticated() {
        return SerenityRest.given()
                .auth().preemptive().basic(user, password);
    }
}
