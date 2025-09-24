Feature: Login functionality

  Background:
    Given the user is on the Login page

  Scenario: Successful login with valid credentials
    When the user enters username "jaya@gmail.com"
    And the user enters password "123456"
    And clicks on the Login button
    Then the user should be redirected to the Home page

  Scenario: Unsuccessful login with invalid credentials
    When the user enters username "invalid@gmail.com"
    And the user enters password "123456"
    And clicks on the Login button