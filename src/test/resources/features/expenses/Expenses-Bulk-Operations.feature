#Feature: Expense Bulk Operations
#  As a user
#  I want to perform bulk operations on expenses
#  So that I can efficiently manage multiple expenses
#
#  Background:
#    Given I am authenticated with a valid JWT token
#    And I have a user account with ID 1
#
#  Scenario: Add multiple expenses successfully
#    Given I have a list of valid expenses:
#      | expenseName | amount | type | paymentMethod | date       |
#      | Groceries   | 150.50 | loss | cash         | 2024-01-15 |
#      | Salary      | 5000   | gain | bank         | 2024-01-01 |
#      | Utilities   | 200    | loss | credit       | 2024-01-10 |
#    When I submit a request to add multiple expenses
#    Then all expenses should be created successfully
#    And the response should contain all created expense IDs
#
#  Scenario: Add multiple expenses with some invalid data
#    Given I have a mixed list of valid and invalid expenses:
#      | expenseName | amount | type | paymentMethod | date       |
#      | Valid Exp   | 100    | loss | cash         | 2024-01-15 |
#      | ""          | -50    | ""   | ""           | null       |
#      | Another     | 200    | gain | bank         | 2024-01-16 |
#    When I submit a request to add multiple expenses
#    Then only valid expenses should be created
#    And I should receive validation errors for invalid expenses
#    And the response should indicate which expenses failed
#
#  Scenario: Track bulk import progress
#    Given I have a large list of 1000 expenses to import
#    When I submit a tracked bulk import request
#    Then I should receive a job ID for tracking progress
#    And I can poll the progress using the job ID
#    And the progress should show completion percentage
#
#  Scenario: Upload expenses from Excel file
#    Given I have a valid Excel file with expense data
#    When I upload the Excel file
#    Then the expenses should be parsed and created
#    And the response should contain all created expenses
#    And invalid rows should be reported with error details
#
#  Scenario: Upload invalid Excel file
#    Given I have an Excel file with invalid format
#    When I upload the Excel file
#    Then I should receive a validation error
#    And the error should specify the format requirements
#
#  Scenario: Delete multiple expenses
#    Given I have multiple existing expenses with IDs [1, 2, 3, 4, 5]
#    When I request to delete expenses with IDs [1, 3, 5]
#    Then the specified expenses should be deleted
#    And the remaining expenses should still exist
#    And the response should confirm successful deletion
#
#  Scenario: Delete multiple expenses with some invalid IDs
#    Given I have existing expenses with IDs [1, 2, 3]
#    When I request to delete expenses with IDs [1, 999, 3]
#    Then valid expense IDs should be deleted
#    And I should receive errors for invalid IDs
#    And the response should indicate which deletions failed
#
#  Scenario: Update multiple expenses
#    Given I have multiple existing expenses
#    And I have updated data for these expenses
#    When I submit a request to update multiple expenses
#    Then all valid updates should be processed
#    And the response should contain updated expense details
#    And invalid updates should be reported with errors
#
#  Scenario: Delete all expenses for user
#    Given I have multiple expenses in my account
#    When I request to delete all my expenses
#    Then all my expenses should be deleted
#    And the response should confirm complete deletion
#    And my expense list should be empty