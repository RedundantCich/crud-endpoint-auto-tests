package com.redundantcich.crudauto.steps;

import com.redundantcich.crudauto.factories.BookFactory;
import com.redundantcich.crudauto.model.Book;
import com.redundantcich.crudauto.service.BookService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BookSteps {

    private final BookService bookService = new BookService();
    private Book createdBook;

    @When("I create a new book")
    public void iCreateANewBook() {
        createdBook = BookFactory.createSimpleBook();
        bookService.createBook(createdBook);
    }

    @And("I store the book id")
    public void iStoreTheBookId() {
        String id = lastResponse().jsonPath().getString("id");
        createdBook.setId(id);
    }

    @When("I fetch the book by stored id")
    public void iFetchTheBookByStoredId() {
        bookService.getBook(createdBook.getId());
    }

    @When("I update the book with new name {string}")
    public void iUpdateTheBookWithNewName(String newName) {
        createdBook.setName(newName);
        bookService.updateBook(createdBook);
    }

    @When("I delete the book by stored id")
    public void iDeleteTheBookByStoredId() {
        bookService.deleteBook(createdBook);
    }

    @When("I list all books")
    public void iListAllBooks() {
        bookService.listBooks();
    }

    @Then("the response should contain my book details")
    public void responseContainsAllBooksDetails() {
        Book returnedBook = lastResponse().jsonPath().getObject("", Book.class);
        assertThat(returnedBook, equalTo(createdBook));
    }
}
