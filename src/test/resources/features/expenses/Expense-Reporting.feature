#Feature: Expense Reporting and Analytics
#  As a user
#  I want to generate reports and analytics for my expenses
#  So that I can analyze my spending patterns
#
#  Background:
#    Given I am authenticated with a valid JWT token
#    And I have a user account with ID 1
#    And I have expenses data for analysis
#
#  Scenario: Generate expense summary
#    Given I have expenses across different categories and payment methods
#    When I request an expense summary
#    Then I should receive a comprehensive summary including:
#      | metric                    | description                           |
#      | totalExpenses            | Sum of all loss-type expenses         |
#      | totalIncome              | Sum of all gain-type expenses         |
#      | netBalance               | Difference between income and expenses |
#      | categoryWiseBreakdown    | Expenses grouped by category          |
#      | paymentMethodBreakdown   | Expenses grouped by payment method    |
#
#  Scenario: Get top N expenses
#    Given I have expenses with varying amounts
#    When I request top 5 expenses
#    Then I should receive the 5 highest expense amounts
#    And they should be sorted in descending order by amount
#
#  Scenario: Get expenses for today
#    Given I have expenses for different dates including today
#    When I request today's expenses
#    Then I should receive only expenses with today's date
#    And expenses from other dates should not be included
#
#  Scenario: Get current month expenses
#    Given I have expenses from different months
#    When I request current month expenses
#    Then I should receive only expenses from the current month
#    And the total should match the sum of current month expenses
#
#  Scenario: Get last month expenses
#    Given I have expenses from different months
#    When I request last month expenses
#    Then I should receive only expenses from the previous month
#    And the response should include the correct month's data
#
#  Scenario: Get monthly spending insights
#    Given I have expenses for January 2024
#    When I request spending insights for January 2024
#    Then I should receive insights including:
#      | insight                  | description                        |
#      | topCategories           | Categories with highest spending   |
#      | spendingTrends          | Daily/weekly spending patterns     |
#      | averageDailySpending    | Average amount spent per day       |
#      | comparisonToPrevious    | Comparison with previous month     |
#
#  Scenario: Get payment method summary
#    Given I have expenses with different payment methods
#    When I request payment method summary
#    Then I should receive a breakdown by payment method
#    And each payment method should show total amount and count
#    And the summary should include both income and expense types
#
#  Scenario: Filter expenses by amount range
#    Given I have expenses with amounts ranging from 10 to 1000
#    When I filter expenses with amount between 100 and 500
#    Then I should receive only expenses within that amount range
#    And expenses outside the range should not be included
#
#  Scenario: Get expenses by category totals
#    Given I have expenses across multiple categories
#    When I request total expenses by category
#    Then I should receive category-wise totals
#    And each category should show the sum of all expenses in that category
#
#  Scenario: Generate Excel report
#    Given I have multiple expenses in my account
#    When I request to generate an Excel report
#    Then an Excel file should be created with all my expenses
#    And the file should contain all expense details in proper format
#    And the response should provide the file path or download link
#
#  Scenario: Email expense report
#    Given I have expenses in my account
#    And I provide a valid email address "user@example.com"
#    When I request to email the expense report
#    Then an Excel report should be generated
#    And the report should be sent to the specified email address
#    And I should receive confirmation of successful email delivery
#
#  Scenario: Get payment-wise total for date range
#    Given I have expenses with different payment methods and dates
#    When I request payment-wise totals from "2024-01-01" to "2024-01-31"
#    Then I should receive totals grouped by payment method
#    And only expenses within the specified date range should be included
#    And each payment method should show the total amount for that period