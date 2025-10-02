@registration @feature
Feature: User Registration
  As a new visitor
  I want to create an account
  So that I can access the application

  Background:
    Given I am on the Register page

  @smoke @positive
  Scenario: Successful registration with valid details
    When I register with first name "John", last name "Doe", email "john.doe+7@test.com", password "Str0ng@123"
    Then I should see a success message "Registration successful"
    And I should be redirected to the Login page


  # ---------------- Mandatory Field Validations ----------------
  @negative @outline
  Scenario Outline: Missing mandatory field
    When I attempt to register with first name "<first>", last name "<last>", email "<email>", password "<password>"
    Then I should see validation error "<error>"
  Examples:
    | first | last | email          | password    | error                   |
    |       | Doe  | u1@test.com    | Str0ng@123  | First Name is required  |
    | John  |      | u2@test.com    | Str0ng@123  | Last Name is required   |
    | John  | Doe  |                | Str0ng@123  | Email is required       |
    | John  | Doe  | u3@test.com    |             | Password is required    |

  # ---------------- Email Format ----------------
  @negative @outline
  Scenario Outline: Invalid email formats
    When I attempt to register with first name "John", last name "Doe", email "<email>", password "Str0ng@123"
    Then I should see validation error "Enter a valid email"
  Examples:
    | email          |
    | john           |
    | john@          |
    | john@test      |
    | john@test..com |
    | john@test,com  |
    | @nodomain.com  |
    | john@.com      |
    | john@-test.com |
    | john@te_st.com |
    | john@te$st.com |
    | john@test.c    |
    | john@test.toolongtld |
    | john..doe@test.com |
    | .john@test.com |
    | john.@test.com |
    | john@localhost |
    | john@127.0.0.1 |
    | john@@test.com |
    | john@test..co.uk |
  Examples: (additional malformed cases)
    | email                 |
    | john(test)@test.com   |
    | john>test@test.com    |
    | john..space@@test.com |

  # ---------------- Valid Email Formats ----------------
  @positive @outline
  Scenario Outline: Acceptable valid email formats
    When I attempt to register with first name "John", last name "Doe", email "<email>", password "Str0ng@123"
    Then I should see validation outcome "success"
  Examples:
    | email                          |
    | john.doe@test.com              |
    | john_doe@test.com              |
    | john-doe@test.co.uk            |
    | john+alias@test.com            |
    | j@test.io                      |
    | JOHN.DOE@TEST.COM              |
    | john.doe123@test-domain.org    |
    | john.doe+promo_2025@test.io    |
    | john.doe@test.travel           |
    | john.doe@sub.test.com          |
    | j_o-h.n+tag@sub.test.co.in     |

  # ---------------- Password Strength ----------------
  @negative @outline
  Scenario Outline: Weak password rules
    When I attempt to register with first name "John", last name "Doe", email "weak+<id>@test.com", password "<password>"
    Then I should see validation error "<message>"
  Examples:
    | id | password   | message                          |
    | 1  | short      | Password too short               |
    | 2  | allletters | Password must contain a number   |
    | 3  | 12345678   | Password must contain a letter   |
    | 4  | NoSymbol1  | Password must contain a symbol   |



  # ---------------- Duplicate Handling ----------------
  @negative @regression
  Scenario: Duplicate email registration
    Given an account already exists with email "jaya@gmail.com"
    When I attempt to register with first name "Any", last name "User", email "jaya@gmail.com", password "Str0ng@123"
    Then I should see validation error "This email is already taken"

  @regression
  Scenario: Double-clicking register button creates single account
    When I fill valid registration data
    And I double-click the Register button quickly
    Then Only one account should be created
    And I should see a single success message

  # ---------------- Security Negative ----------------
  @security @negative
  Scenario: XSS attempt in first name
    When I attempt to register with first name "<script>alert(1)</script>", last name "User", email "xss@test.com", password "Str0ng@123"
    Then I should see validation error "Invalid characters"
    And The script should not execute

  @security @negative
  Scenario: SQL injection attempt in email
    When I attempt to register with first name "SQL", last name "User", email "test@test.com' OR '1'='1", password "Str0ng@123"
    Then I should see validation error "Enter a valid email"

  # ---------------- Navigation & Flow ----------------
  @navigation @positive
  Scenario: Navigate to login from register form
    When I click the Login link
    Then I should land on the Login page

  @navigation
  Scenario: Navigate back to register from login
    Given the user is on the Login page
    When I click the Register button
    Then I should land on the Register page

  @navigation @regression
  Scenario: Forgot Password link visible post-registration
    Given I have just registered successfully
    When I am redirected to the Login page
    Then I should see a "Forgot Password?" link

#   ---------------- Resilience ----------------
  @resilience @regression
  Scenario: Refresh clears form fields
    When I populate the registration form
    And I refresh the page
    Then All fields should be cleared


  # ---------------- Boundary - First Name Length ----------------
  @boundary @outline
  Scenario Outline: Boundary test for first name length
    When I attempt to register with first name "<value>", last name "Doe", email "len<idx>@test.com", password "Str0ng@123"
    Then I should see validation outcome "<outcome>"
  Examples:
    | idx | value                  | outcome                 |
    | 1   | J                      | success                 |
    | 2   |                        | First Name is required  |
    | 3   | JJJJJJJJJJJJJJJJJJJJ   | success                 |
    | 4   | JJJJJJJJJJJJJJJJJJJJJJ | First Name too long     |

