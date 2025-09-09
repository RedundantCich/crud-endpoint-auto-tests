@crud @negative
Feature: Negative cases for Book management
  As an API client
  I want proper error responses
  So that invalid requests do not corrupt the system

  Background:
    Given I am an authenticated API user
    And I configure API timeouts

  Scenario Outline: Create a book with any of the fields missing
    When When I create a new book with name "<name>", author "<author>", publication "<publication>", category "<category>", price "<price>", pages "<pages>"
    Then I send the new book with missing fields to API
    Then the response status should be 400
    And the response should contain an error about "<missingField>"

    Examples:
      | name      | author     | publication | category | price | pages | missingField |
      |           | John Smith | Penguin     | Fiction  | 9.99  | 200   | name         |
      | Fail Book |            | Penguin     | Fiction  | 9.99  | 200   | author       |
      | Fail Book | John Smith |             | Fiction  | 9.99  | 200   | publication  |
      | Fail Book | John Smith | Penguin     |          | 9.99  | 200   | category     |
      | Fail Book | John Smith | Penguin     | Fiction  |       | 200   | price        |
      | Fail Book | John Smith | Penguin     | Fiction  | 9.99  |       | pages        |
