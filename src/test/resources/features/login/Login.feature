Feature: Login functionality
  As a registered or unregistered user
  I want to authenticate with the application
  So that authorized users can access protected areas

  Background:
    Given the user is on the Login page

  @smoke @positive
  Scenario: Successful login with valid credentials
    When the user enters username "jjayaprakash2002@gmail.com"
    And the user enters password "123456"
    And clicks on the Login button
    Then the user should be redirected to the Home page
    And click on logout and confirm

  @negative
  Scenario: Unsuccessful login with invalid password
    When the user enters username "jjayaprakash2002@gmail.com"
    And the user enters password "12345622"
    And clicks on the Login button
  Then "Invalid Username or Password" error message should be displayed

  @negative
  Scenario: Unsuccessful login with unregistered email
    When the user enters username "unknown.user@example.com"
    And the user enters password "SomePass123"
    And clicks on the Login button
  Then "Invalid Username or Password" error message should be displayed

  @negative
  Scenario: Unsuccessful login with both fields empty
    When the user enters username ""
    And the user enters password ""
    And clicks on the Login button
  Then "Enter all the mandatory fields" error message should be displayed

  @negative
  Scenario: Unsuccessful login with empty username
    When the user enters username ""
    And the user enters password "SomePass123"
    And clicks on the Login button
  Then "Email is required" error message should be displayed

  @negative
  Scenario: Unsuccessful login with empty password
    When the user enters username "valid.user@example.com"
    And the user enters password ""
    And clicks on the Login button
  Then "Password is required" error message should be displayed



  @outline @negative
  Scenario Outline: Unsuccessful login with invalid combinations
    When the user enters username "<username>"
    And the user enters password "<password>"
    And clicks on the Login button
  Then "<expectedMessage>" error message should be displayed

    Examples:
      | username                | password | expectedMessage             |
      | invalid@example.com     | 123456   | Invalid Username or Password|
      | jjayaprakash2002@gmail.com  | badpass  | Invalid Username or Password|
      | invalid@example.com     | badpass  | Invalid Username or Password|

  @regression
  Scenario: Logout after successful login
    When the user enters username "jjayaprakash2002@gmail.com"
    And the user enters password "123456"
    And clicks on the Login button
    Then the user should be redirected to the Home page
    And click on logout and confirm