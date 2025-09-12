package com.redundantcich.crudauto.steps;

import com.redundantcich.crudauto.factories.BookFactory;
import com.redundantcich.crudauto.model.Book;
import com.redundantcich.crudauto.service.BookService;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;

import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class BookSteps {

    private final BookService bookService = new BookService();
    private Book createdBook;
    private Map<String, Object> invalidBook;


    // --- Book Creation Steps ---
    @When("I create a new book")
    public void createRandomBook() {
        createdBook = BookFactory.createRandomBook();
        bookService.createBook(createdBook);
    }

    @When("I create a new book with name {string}, author {string}, publication {string}, category {string}, pages {string}, price {string}")
    public void createBookWithCustomFields(String name, String author, String publication, String category, String pages, String price) {
        Book book = BookFactory.createCustomBookFromStep(name, author, publication, category, price, pages);
        bookService.createBook(book);
    }

    @When("I create a new book with extremely long text values")
    public void createTooLongTextBook() {
        Book longBook = BookFactory.createTooLongTextFieldsBook();
        bookService.createBook(longBook);
    }

    @When("I create a new book with extremely long number values")
    public void createTooLongNumberBook() {
        Book longBook = BookFactory.createTooLongNumberFieldsBook();
        bookService.createBook(longBook);
    }

    @When("I create a new book with negative price {float}")
    public void createBookWithNegativePrice(float price) {
        Book book = BookFactory.createRandomBook();
        book.setPrice(price);
        bookService.createBook(book);
    }

    @When("I create a new book with invalid price {string}")
    public void createBookWithInvalidPrice(String price) {
        invalidBook = BookFactory.createCustomInvalidPriceBookFromStep(price);
        bookService.createInvalidBook(invalidBook);
    }

    @When("I create a new book with negative pages {int}")
    public void createBookWithNegativePages(int pages) {
        Book book = BookFactory.createRandomBook();
        book.setPages(pages);
        bookService.createBook(book);
    }

    @When("I create a new book with invalid pages {string}")
    public void createBookWithInvalidPages(String pages) {
        invalidBook = BookFactory.createCustomInvalidPagesBookFromStep(pages);
        bookService.createInvalidBook(invalidBook);
    }

    @When("I create a book in the following format:")
    public void createBookInFormat(String formattedBook) {
        bookService.createInvalidBook(formattedBook);
    }

    // --- Book Fetching Steps ---

    @When("I fetch all the books")
    public void fetchAllBooks() {
        bookService.getAllBooks();
    }

    @When("I fetch the book by stored id")
    public void fetchBookByStoredId() {
        bookService.getBook(createdBook.getId());
    }

    @When("I fetch a book with id {string}")
    public void fetchBookById(String id) {
        bookService.getBook(id);
    }

    // --- Book Deletion Steps ---
    @When("I delete the book by stored id")
    public void deleteBookByStoredId() {
        bookService.deleteBook(createdBook);
    }

    @When("I delete a book with id {string}")
    public void deleteBookById(String id) {
        bookService.deleteBook(id);
    }

    // --- Book Update Steps ---
    @When("I update the book with new name {string}")
    public void updateBookName(String newName) {
        createdBook.setName(newName);
        bookService.updateBook(createdBook);
    }

    @When("I update a book with id {string}")
    public void fullyUpdateBookWithId(String id) {
        Book newBook = BookFactory.createRandomBook();
        newBook.setId(id);
        bookService.updateBook(newBook);
    }

    @When("I update an existing book with completely new book details")
    public void updateBookWithNewDetails() {
        Book newBook = BookFactory.createRandomBook();
        newBook.setId(createdBook.getId());
        createdBook = newBook;
        bookService.updateBook(createdBook);
    }

    // --- Validation Steps ---
    @Then("the response should contain my book details")
    public void validateResponseContainsBookDetails() {
        Book returnedBook = lastResponse().jsonPath().getObject("", Book.class);
        assertThat(returnedBook).usingRecursiveComparison().isEqualTo(createdBook);
    }

    @Then("first book has non-empty fields")
    public void validateFirstBookFields() {
        JsonPath jsonPath = lastResponse().jsonPath();
        Book firstBook = jsonPath.getObject("[0]", Book.class);
        validateBookFieldsNotEmpty(firstBook);
    }

    @Then("last book has non-empty fields")
    public void validateLastBookFields() {
        JsonPath jsonPath = lastResponse().jsonPath();
        int lastIndex = jsonPath.getList("$").size() - 1;
        Book lastBook = jsonPath.getObject("[" + lastIndex + "]", Book.class);
        validateBookFieldsNotEmpty(lastBook);
    }

    @And("the response should contain an error about {string}")
    public void validateErrorResponse(String missingField) {
        String body = SerenityRest.lastResponse().asString();
        assertThat(body).contains(missingField);
    }

    // --- Helper Methods ---
    private void validateBookFieldsNotEmpty(Book book) {
        assertThat(book).isNotNull();
        assertThat(book.getId()).isNotEmpty();
        assertThat(book.getName()).isNotEmpty();
        assertThat(book.getAuthor()).isNotEmpty();
        assertThat(book.getPublication()).isNotEmpty();
        assertThat(book.getCategory()).isNotEmpty();
        assertThat(book.getPages()).isNotNull();
        assertThat(book.getPrice()).isNotNull();
    }

    @After
    public void cleanup() {
        String bookId = Serenity.sessionVariableCalled("createdBookId");
        if (bookId != null) {
            bookService.deleteBook(bookId);
            Serenity.clearSessionVariable("createdBookId");
        }
    }

    @And("I store the book id")
    public void storeBookId() {
        String id = lastResponse().jsonPath().getString("id");
        assertThat(id).isNotEmpty();
        createdBook.setId(id);
        Serenity.setSessionVariable("createdBookId").to(id);
    }
}
