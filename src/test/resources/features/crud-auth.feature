@crud @negative @auth
Feature: Negative cases for Book management - authorization
  As an API client
  I want proper error responses
  So that unauthorized requests are not processed

  Background:
    Given I don't have valid authentication credentials

  Scenario: Handle unauthorized access to getting the books
    When I fetch all the books
    Then the response status should be 401
    And the response should contain an error about "Authentication"

  Scenario: Handle unauthorized access to creating a new book
    When I create a new book
    Then the response status should be 401
    And the response should contain an error about "Authentication"

  Scenario: Handle unauthorized access to updating a book
    When I update a book with id "1"
    Then the response status should be 401
    And the response should contain an error about "Authentication"

  Scenario: Handle unauthorized access to deleting a book
    When I delete a book with id "1"
    Then the response status should be 401
    And the response should contain an error about "Authentication"

