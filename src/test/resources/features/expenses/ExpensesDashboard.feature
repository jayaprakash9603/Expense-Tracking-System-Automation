Feature: Expense Dashboard UI
  As a user
  I want to interact with the expense dashboard
  So that I can visualize and analyze my financial data

  Background:
    Given the user is on the Login page
    When the user enters username "jjayaprakash2002@gmail.com"
    And the user enters password "123456"
    And clicks on the Login button
    Then the user should be redirected to the Home page

  Scenario: Dashboard loads with default view
    When the dashboard initializes
    Then I should see the dashboard header with title "💰 Financial Dashboard"
    And I should see the subtitle "Real-time insights into your financial health"
    And I should see metric cards for financial overview
    And I should see the daily spending chart
    And I should see the category breakdown chart
    And I should see the monthly trend chart
    And I should see the payment methods chart
    And I should see the Application Overview section
    And I should see the Quick Access section
    And I should see the Recent Transactions section
    And I should see the Budget Overview section
    And click on logout and confirm
