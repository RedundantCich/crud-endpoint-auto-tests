@crud @negative
Feature: Negative cases for Book management - Request Body
  As an API client
  I want proper error responses
  So that invalid requests do not corrupt the system

  Background:
    Given I am an authenticated API user

  Scenario Outline: Create a book with any of the fields missing
    When I create a new book with name "<name>", author "<author>", publication "<publication>", category "<category>", pages "<pages>", price "<price>"
    Then the response status should be 400
    And the response should contain an error about "<missingField>"

    Examples:
      | name        | author     | publication | category | price | pages | missingField |
      |             | John Smith | Penguin     | Fiction  | 9.99  | 200   | name         |
      | Fail BookA  |            | Penguin     | Fiction  | 9.99  | 200   | author       |
      | Fail BookPu | John Smith |             | Fiction  | 9.99  | 200   | publication  |
      | Fail BookC  | John Smith | Penguin     |          | 9.99  | 200   | category     |
      | Fail BookPr | John Smith | Penguin     | Fiction  | 0.00  | 200   | price        |
      | Fail BookPa | John Smith | Penguin     | Fiction  | 9.99  | 0     | pages        |

  Scenario: Create a book with text in price field
    When I create a new book with invalid price "xyz!"
    Then the response status should be 400
    And the response should contain an error about "price"

  Scenario: Create a book with text in pages field
    When I create a new book with invalid pages "xyz!"
    Then the response status should be 400
    And the response should contain an error about "pages"

  Scenario: Create a book with negative value in pages field
    When I create a new book with negative pages -100
    Then the response status should be 400
    And the response should contain an error about "pages"

  Scenario: Create a book with negative value in price field
    When I create a new book with negative price -9.99
    Then the response status should be 400
    And the response should contain an error about "price"

  Scenario: Fetching a non-existing book should return 404
    When I fetch a book with id "99999"
    Then the response status should be 404
    And the response should contain an error about "id"

  Scenario: Updating a non-existing book should return 404
    When I update a book with id "99999"
    Then the response status should be 404
    And the response should contain an error about "id"

  Scenario: Deleting a non-existing book should return 404
    When I delete a book with id "99999"
    Then the response status should be 404
    And the response should contain an error about "id"

  Scenario: Handle malformed JSON in request body
    When I create a book in the following format:
      """
      {"name": "Test", "author": "Test", invalid json}
      """
    Then the response status should be 400
    And the response should contain an error about "Invalid JSON"

  Scenario: Handle request body size limits - text
    When I create a new book with extremely long text values
    Then the response status should be 413
    And the response should contain an error about "Request entity too large"

  Scenario: Handle request body size limits - numbers
    When I create a new book with extremely long number values
    Then the response status should be 413
    And the response should contain an error about "Request entity too large"
