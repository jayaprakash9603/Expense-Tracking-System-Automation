#Feature: Expense Management
#  As a user
#  I want to manage my expenses
#  So that I can track my financial transactions
#
#  Background:
#    Given I am authenticated with a valid JWT token
#    And I have a user account with ID 1
#
#  Scenario: Successfully add a new expense
#    Given I have valid expense data:
#      | expenseName | amount | type | paymentMethod | date       | comments        |
#      | Groceries   | 150.50 | loss | cash         | 2024-01-15 | Weekly shopping |
#    When I submit a request to add the expense
#    Then the expense should be created successfully
#    And the response should contain the expense ID
#    And the expense should be linked to my user account
#
#  Scenario: Add expense with invalid data
#    Given I have invalid expense data:
#      | expenseName | amount | type | paymentMethod | date | comments |
#      | ""          | -50    | ""   | ""           | null | ""       |
#    When I submit a request to add the expense
#    Then I should receive a validation error
#    And the response should contain error messages for required fields
#
#  Scenario: Add expense with future date
#    Given I have expense data with future date:
#      | expenseName | amount | type | paymentMethod | date       |
#      | Future Exp  | 100    | loss | cash         | 2025-12-31 |
#    When I submit a request to add the expense
#    Then I should receive a validation error
#    And the error message should indicate "Date cannot be in the future"
#
#  Scenario: Add expense with zero or negative amount
#    Given I have expense data with invalid amount:
#      | expenseName | amount | type | paymentMethod | date       |
#      | Zero Amount | 0      | loss | cash         | 2024-01-15 |
#    When I submit a request to add the expense
#    Then I should receive a validation error
#    And the error message should indicate "Amount must be greater than zero"
#
#  Scenario: Copy an existing expense
#    Given I have an existing expense with ID 123
#    When I request to copy the expense
#    Then a new expense should be created with the same details
#    And the new expense should have a different ID
#    And the new expense should be linked to my user account
#
#  Scenario: Copy non-existent expense
#    Given I request to copy expense with ID 999999
#    When I submit the copy request
#    Then I should receive a "not found" error
#    And the error message should indicate "Expense not found with ID: 999999"
#
#  Scenario: Update an existing expense
#    Given I have an existing expense with ID 123
#    And I have updated expense data:
#      | expenseName | amount | type | paymentMethod | date       |
#      | Updated Exp | 200.75 | gain | credit       | 2024-01-16 |
#    When I submit a request to update the expense
#    Then the expense should be updated successfully
#    And the response should contain the updated expense details
#
#  Scenario: Update expense with invalid ID
#    Given I request to update expense with ID 999999
#    When I submit the update request
#    Then I should receive a "not found" error
#    And the error message should indicate "Expense not found with ID: 999999"
#
#  Scenario: Delete an existing expense
#    Given I have an existing expense with ID 123
#    When I request to delete the expense
#    Then the expense should be deleted successfully
#    And the response should confirm deletion
#
#  Scenario: Delete non-existent expense
#    Given I request to delete expense with ID 999999
#    When I submit the delete request
#    Then I should receive a "not found" error
#    And the error message should indicate "Expense not found with ID: 999999"
#
#  Scenario: Get all expenses for user
#    Given I have multiple expenses in my account
#    When I request to fetch all my expenses
#    Then I should receive a list of all my expenses
#    And each expense should contain complete details
#    And expenses should be sorted by date in descending order
#
#  Scenario: Get expenses with date range filter
#    Given I have expenses from different dates
#    When I request expenses from "2024-01-01" to "2024-01-31"
#    Then I should receive only expenses within that date range
#    And the response should not contain expenses outside the range
#
#  Scenario: Search expenses by name
#    Given I have expenses with different names
#    When I search for expenses with name "Groceries"
#    Then I should receive only expenses containing "Groceries" in the name
#    And the search should be case-insensitive
#
#  Scenario: Filter expenses by payment method
#    Given I have expenses with different payment methods
#    When I filter expenses by payment method "cash"
#    Then I should receive only expenses paid with cash
#    And other payment method expenses should not be included
#
#  Scenario: Get monthly summary
#    Given I have expenses for January 2024
#    When I request monthly summary for January 2024
#    Then I should receive a summary with total income and expenses
#    And the summary should include balance remaining
#    And category-wise breakdown should be provided
#
#  Scenario: Get yearly summary
#    Given I have expenses for the year 2024
#    When I request yearly summary for 2024
#    Then I should receive month-wise summary for the entire year
#    And each month should show total income and expenses
#
#  Scenario: Access another user's expenses without permission
#    Given another user exists with ID 2
#    And I don't have permission to view their expenses
#    When I request to fetch expenses for user ID 2
#    Then I should receive a "forbidden" error
#    And the error message should indicate insufficient permissions