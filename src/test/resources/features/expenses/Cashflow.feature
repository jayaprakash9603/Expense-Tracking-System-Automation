#Feature: Cash Flow UI
#  As a user
#  I want to interact with the cash flow visualization
#  So that I can analyze my income and expense patterns
#
#  Background:
#    Given I am on the cash flow page
#    And I am authenticated with a valid JWT token
#    And the cash flow data has loaded
#
#  Scenario: Cash flow chart loads with default view
#    When the cash flow page initializes
#    Then I should see a bar chart displaying cash flow data
#    And I should see positive values for income (gain)
#    And I should see negative values for expenses (loss)
#    And I should see a reference line at zero
#    And I should see proper axis labels and formatting
#
#  Scenario: View cash flow tooltip information
#    Given the cash flow chart is displayed
#    When I hover over a bar in the chart
#    Then I should see a tooltip with detailed information
#    And the tooltip should show the date
#    And the tooltip should show the amount formatted as currency
#    And the tooltip should indicate if it's income or expense
#    When I move the mouse away
#    Then the tooltip should disappear
#
#  Scenario: Interact with cash flow bars
#    Given the cash flow chart is displayed
#    When I click on a specific bar
#    Then the bar should be highlighted or selected
#    And I should see additional details for that time period
#    And I should be able to drill down into the transactions
#
#  Scenario: View cash flow with different time periods
#    Given I am viewing the cash flow chart
#    When I change the time period filter
#    Then the chart should update to show data for the selected period
#    And the x-axis should adjust to show appropriate time labels
#    And the y-axis should scale to fit the new data range
#
#  Scenario: Cash flow chart responsive behavior
#    Given I am viewing the cash flow on different screen sizes
#    When the screen width changes
#    Then the chart should maintain readability
#    And the bars should remain proportional
#    And the axis labels should adjust appropriately
#
#  Scenario: Cash flow data filtering
#    Given I am viewing the cash flow chart
#    When I apply filters for specific categories or payment methods
#    Then the chart should update to show only filtered data
#    And the reference line should remain at zero
#    And the totals should reflect the filtered amounts
#
#  Scenario: Cash flow export functionality
#    Given I am viewing the cash flow chart
#    When I click the export button
#    Then I should be able to download the cash flow data
#    And the export should include all visible data points
#    And the format should be user-friendly (CSV, Excel, etc.)
#
#  Scenario: Cash flow loading and error states
#    Given the cash flow component is loading data
#    When I view the chart area
#    Then I should see a loading indicator
#    When there is an error loading cash flow data
#    Then I should see an appropriate error message
#    And I should have an option to retry loading the data
#
#  Scenario: Cash flow with no data
#    Given there is no cash flow data available
#    When I view the cash flow chart
#    Then I should see a message indicating no data is available
#    And I should see suggestions for adding transactions
#    And the chart area should display an empty state gracefully
#
#  Scenario: Cash flow real-time updates
#    Given I am viewing the cash flow chart
#    When new transactions are added to the system
#    Then the chart should update to reflect the new data
#    And the bars should animate to their new positions
#    And the axis scaling should adjust if necessary