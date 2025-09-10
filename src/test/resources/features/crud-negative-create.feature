@crud @negative
Feature: Negative cases for Book management
  As an API client
  I want proper error responses
  So that invalid requests do not corrupt the system

  Background:
    Given I am an authenticated API user
    And I configure API timeouts

  Scenario Outline: Create a book with any of the fields missing
    When I try to create a new book with name "<name>", author "<author>", publication "<publication>", category "<category>", pages "<pages>", price "<price>"
    Then the response status should be 400
    And the response should contain an error about "<missingField>"

    Examples:
      | name        | author     | publication | category | price | pages | missingField |
      |             | John Smith | Penguin     | Fiction  | 9.99f | 200   | name         |
      | Fail BookA  |            | Penguin     | Fiction  | 9.99f | 200   | author       |
      | Fail BookPu | John Smith |             | Fiction  | 9.99f | 200   | publication  |
      | Fail BookC  | John Smith | Penguin     |          | 9.99f | 200   | category     |
      | Fail BookPr | John Smith | Penguin     | Fiction  | 0.00f | 200   | price        |
      | Fail BookPa | John Smith | Penguin     | Fiction  | 9.99f | 0     | pages        |

  Scenario: Create a book with text in price fields
    When I try to create a new book with invalid price "xyz!"
    Then the response status should be 400

  Scenario: Create a book with text in pages fields
    When I try to create a new book with invalid pages "xyz!"
    Then the response status should be 400
