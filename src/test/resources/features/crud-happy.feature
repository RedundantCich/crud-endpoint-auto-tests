@crud @happy
Feature: Book CRUD - Happy Path
  As an API user
  I want to manage books successfully
  So that I can perform basic CRUD operations

  Background:
    Given I am an authenticated API user

  Scenario: List all the existing books
    When I list all books
    Then the response status should be 200

  Scenario: Create, fetch, update and delete a book
    When I create a new book
    Then the response status should be 201
    And I store the book id
    And the response should contain my book details

    When I fetch the book by stored id
    Then the response status should be 200
    And the response should contain my book details

    When I update the book with new name "UpdatedBook"
    Then the response status should be 200
    And the response should contain my book details

    When I delete the book by stored id
    Then the response status should be 204
    And the response should contain my book details

    When I fetch the book by stored id
    Then the response status should be 404