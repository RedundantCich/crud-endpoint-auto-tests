package com.redundantcich.crudauto.steps;

import com.redundantcich.crudauto.factories.BookFactory;
import com.redundantcich.crudauto.model.Book;
import com.redundantcich.crudauto.service.BookService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(id).isNotEmpty();
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
        assertThat(returnedBook)
                .usingRecursiveComparison()
                .isEqualTo(createdBook);
    }

    @Then("first book has non-empty fields")
    public void responseContainsNonEmptyBookInFirstPlace() {
        JsonPath jsonPath = lastResponse().jsonPath();
        Book firstBook = jsonPath.getObject("[0]", Book.class);
        bookFieldsNotEmpty(firstBook);
    }

    @Then("last book has non-empty fields")
    public void responseContainsNonEmptyBookInLastPlace() {
        JsonPath jsonPath = lastResponse().jsonPath();
        int lastIndex = jsonPath.getList("$").size() - 1;
        Book lastBook = jsonPath.getObject("[" + lastIndex + "]", Book.class);
        bookFieldsNotEmpty(lastBook);
    }

    private void bookFieldsNotEmpty(Book book) {
        assertThat(book).isNotNull();
        assertThat(book.getName()).isNotEmpty();
        assertThat(book.getAuthor()).isNotEmpty();
        assertThat(book.getPublication()).isNotEmpty();
        assertThat(book.getCategory()).isNotEmpty();
        assertThat(book.getPages()).isNotNull();
        assertThat(book.getPrice()).isNotNull();
    }
}

