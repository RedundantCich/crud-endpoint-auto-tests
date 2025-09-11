package com.redundantcich.crudauto.service;

import com.redundantcich.crudauto.config.Config;
import com.redundantcich.crudauto.model.Book;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;

import java.util.Map;

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

    public void createInvalidBook(String malformedBook) {
        authenticated()
                .contentType("application/json")
                .body(malformedBook)
                .post(fullBooksUrl);
    }

    public void createInvalidBook(Map<String, Object> invalidBook) {
        authenticated()
                .contentType("application/json")
                .body(invalidBook)
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

    public void deleteBook(String id) {
        String endpointWithId = baseUrl + Config.getBookByIdEndpoint(id);
        authenticated()
                .delete(endpointWithId);
    }

    public void getAllBooks() {
        authenticated()
                .get(fullBooksUrl);
    }

    private RequestSpecification authenticated() {
        boolean isAuthenticated = Serenity.sessionVariableCalled("auth");
        if (isAuthenticated) {
            return SerenityRest.given()
                    .auth().basic(user, password);
        } else {
            return SerenityRest.given();
        }
    }
}
