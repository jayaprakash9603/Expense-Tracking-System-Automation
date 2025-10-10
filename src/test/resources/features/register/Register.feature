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

  # ---------------- Boundary - Last Name Length ----------------
  @boundary @outline
  Scenario Outline: Boundary test for last name length
    When I attempt to register with first name "Doe", last name "<value>", email "lastname<idx>@test.com", password "Str0ng@123"
    Then I should see validation outcome "<outcome>"
  Examples:
    | idx | value                  | outcome                |
    | 1   | D                      | success                |
    | 2   |                        | Last Name is required  |
    | 3   | DDDDDDDDDDDDDDDDDDDD   | success                |
    | 4   | DDDDDDDDDDDDDDDDDDDDDD | Last Name too long     |

  # ---------------- Boundary - Password Length Upper ----------------
  @boundary @outline
  Scenario Outline: Boundary test for password max length
    When I attempt to register with first name "John", last name "Doe", email "pwlen<idx>@test.com", password "<password>"
    Then I should see validation outcome "<outcome>"
  Examples:
    | idx | password                                   | outcome                   |
    | 1   | Str0ng@1                                   | success                   |
    | 2   | Str0ng@123456789012345                     | success                   |
    | 3   | Str0ng@1234567890123456                    | success        |

  # ---------------- Case Insensitive Email Uniqueness ----------------
  @regression @negative
  Scenario: Duplicate email differing only by case
    Given an account already exists with email "JaYa@gmail.com"
    When I attempt to register with first name "Case", last name "User", email "JaYa@gmail.com", password "prakash@2002"
    Then I should see validation error "This email is already taken"

  # ---------------- Leading/Trailing Whitespace Trimming ----------------
  @usability @positive
  Scenario: Inputs with surrounding spaces are trimmed
    When I attempt to register with first name "  John  ", last name "  Doe ", email "  trim@test.com  ", password "  Str0ng@123  "
    Then I should see a success message "Registration successful"
    And The stored user name should be "John Doe"

  # ---------------- Duplicate Submission Prevention ----------------
#  @resilience @negative
#  Scenario: Rapid multiple form submissions are throttled
#    When I attempt to submit the registration form 3 times within 1 second with first name "Load", last name "Test", email "throttle@test.com", password "Str0ng@123"
#    Then I should see validation error "Too many attempts, please wait"



  # ---------------- Disallowed Characters in Names ----------------
  @negative @outline
  Scenario Outline: Reject names with disallowed characters
    When I attempt to register with first name "<bad>", last name "User", email "badname<idx>@test.com", password "Str0ng@123"
    Then I should see validation error "Invalid characters"
  Examples:
    | idx | bad                |
    | 1   | John<script>       |
    | 2   | Eve@              |
    | 3   | Bob#               |
    | 4   | Jane<>             |






  # ---------------- Field Focus Order / Accessibility ----------------
  @accessibility
  Scenario: Tabbing through fields follows logical order
    When I navigate the form using the Tab key
    Then Focus order should be First Name -> Last Name -> Email -> Password -> Register Button -> Login link

  # ---------------- Screen Reader / ARIA Validation ----------------
  @accessibility
  Scenario: Validation errors announced via ARIA
    When I submit an empty registration form
    Then Each field error should have aria-live polite

  # ---------------- Password Not Logged (Audit) ----------------
  @security
  Scenario: Password value not leaked to client logs
    When I open the browser console while registering with first name "Log", last name "Audit", email "audit@test.com", password "Str0ng@123"
    Then I should not see the password value in console logs






  # ---------------- Export / Report Injection Safety ----------------
  @security @negative
  Scenario: Formula injection attempt in first name
    When I attempt to register with first name "=CMD|' /C calc'!A0", last name "Formula", email "formula@test.com", password "Str0ng@123"
    Then I should see validation error "Invalid characters"

