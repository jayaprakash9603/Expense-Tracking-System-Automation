#Feature: Edit Expense UI
#  As a user
#  I want to edit existing expenses through the UI
#  So that I can correct or update my financial transactions
#
#  Background:
#    Given I am on the edit expense page for expense ID "123"
#    And I am authenticated with a valid JWT token
#    And the expense data has loaded successfully
#
#  Scenario: Edit expense form loads with existing data
#    When the edit expense form initializes
#    Then I should see the form title "Edit Expense"
#    And I should see a close button (×) in the top right
#    And I should see all form fields populated with existing expense data
#    And I should see the expense name field with current value
#    And I should see the amount field with current value
#    And I should see the date field with current date
#    And I should see the transaction type with current value
#    And I should see the category with current value
#    And I should see the payment method with current value
#    And I should see the comments with current value
#
#  Scenario: Modify expense name with autocomplete
#    Given the edit expense form is loaded with existing data
#    When I clear the expense name field
#    And I type "New Expense" in the expense name field
#    Then I should see autocomplete suggestions
#    And I should be able to select from suggestions
#    When I type a completely new expense name
#    Then the field should accept the new value
#
#  Scenario: Update expense amount
#    Given the edit expense form is loaded with existing data
#    When I change the amount from "1000" to "1500"
#    Then the amount field should show the new value
#    And the field should accept only numeric input
#    When I clear the amount field
#    Then I should see validation error on submit
#
#  Scenario: Change expense date and update budgets
#    Given the edit expense form is loaded with existing data
#    When I change the date from current date to a different date
#    Then the date field should update
#    And the system should fetch budgets for the new date
#    And the budget table should refresh with relevant budgets
#
#  Scenario: Update transaction type
#    Given the edit expense form is loaded with "Loss" transaction type
#    When I change the transaction type to "Gain"
#    Then the transaction type field should show "Gain"
#    And the form should accept the change
#
#  Scenario: Modify category selection
#    Given the edit expense form is loaded with existing category
#    When I click on the category field
#    Then I should see the current category selected
#    When I search for a different category
#    Then I should see filtered category options
#    When I select a new category
#    Then the category field should update with the new selection
#
#  Scenario: Change payment method
#    Given the edit expense form is loaded with existing payment method
#    When I click on the payment method dropdown
#    Then I should see the current payment method selected
#    When I select "Credit Due" instead of "Cash"
#    Then the payment method should update
#    And the creditDue calculation should adjust accordingly
#
#  Scenario: Update comments
#    Given the edit expense form is loaded with existing comments
#    When I modify the comments text
#    Then the comments field should accept the changes
#    And the field should support multi-line text
#    When I clear all comments
#    Then the field should be empty and valid
#
#  Scenario: Manage budget associations
#    Given the edit expense form is loaded
#    When I click the "Link Budgets" button
#    Then I should see the budget table with current associations
#    And previously linked budgets should be checked
#    When I uncheck a previously linked budget
#    Then that budget should be deselected
#    When I check a new budget
#    Then that budget should be selected for linking
#
#  Scenario: Budget table shows correct data for expense date
#    Given the edit expense form is loaded
#    When the budget table is displayed
#    Then I should see budgets relevant to the expense date
#    And each budget should show name, description, dates, and amounts
#    And the checkboxes should reflect current budget associations
#
#  Scenario: Submit expense updates successfully
#    Given I have modified expense details
#    And all required fields are filled correctly
#    When I click the "Submit" button
#    Then the expense should be updated successfully
#    And I should see a success toast "Expense updated successfully!"
#    And I should be redirected to the previous page
#
#  Scenario: Form validation on update
#    Given I am editing an expense
#    When I clear required fields and try to submit
#    Then I should see appropriate validation errors
#    And the form should not submit
#    And error fields should have red borders
#    And error messages should appear below fields
#
#  Scenario: Handle friend access permissions
#    Given I am editing an expense for a friend with ID "friend123"
#    And I have read-only access
#    When the form loads
#    Then I should be redirected to the read-only view
#    Given I have write access
#    When the form loads
#    Then I should be able to edit all fields
#    And the submit button should be visible
#
#  Scenario: Close edit form without saving
#    Given I am editing an expense
#    And I have made changes to the form
#    When I click the close button (×)
#    Then I should be redirected to the previous page
#    And the changes should be discarded
#
#  Scenario: Form responsive behavior on mobile
#    Given I am editing an expense on mobile
#    When the screen width is less than 640px
#    Then the form should adapt to mobile layout
#    And all fields should be full width
#    And the budget table should switch to card view
#    And buttons should be appropriately sized
#
#  Scenario: Loading states during edit
#    Given I am on the edit expense form
#    When the expense data is loading
#    Then I should see loading indicators
#    When categories are being fetched
#    Then the category field should show loading
#    When budgets are being fetched
#    Then the budget section should show loading
#
#  Scenario: Error handling during edit
#    Given I am editing an expense
#    When there is an error loading the expense data
#    Then I should see an appropriate error message
#    When there is an error submitting updates
#    Then I should see an error toast message
#    And the form should remain open for corrections
#
#  Scenario: Date change triggers budget refresh
#    Given I am editing an expense
#    When I change the expense date
#    Then the system should automatically fetch budgets for the new date
#    And the budget table should update with relevant budgets
#    And previously selected budgets should be cleared if not available for new date
#
#  Scenario: Payment method affects credit calculation
#    Given I am editing an expense with amount "2000"
#    When I change payment method to "Credit Due"
#    Then the creditDue should be calculated as 2000
#    When I change payment method to "Cash"
#    Then the creditDue should be set to 0
#    When I change payment method to "Credit Paid"
#    Then the creditDue should be set to 0
#
#  Scenario: Autocomplete behavior in edit mode
#    Given I am editing an expense
#    When I interact with autocomplete fields (expense name, category, payment method)
#    Then I should see relevant suggestions
#    And matching text should be highlighted in teal
#    And I should be able to select from suggestions or enter new values
#
#  Scenario: Budget table pagination and selection
#    Given I am editing an expense with many available budgets
#    When the budget table is displayed
#    Then I should see pagination controls if there are more than 5 budgets
#    And I should be able to navigate between pages
#    And my selections should be preserved across pages
#    When I select/deselect budgets
#    Then the selection should be reflected in the checkbox states
#
#  Scenario: Form field styling and validation feedback
#    Given I am editing an expense
#    When a field has validation errors
#    Then the field should have a red border
#    And error messages should appear below the field
#    When I correct the validation error
#    Then the red border should disappear
#    And the error message should be removed