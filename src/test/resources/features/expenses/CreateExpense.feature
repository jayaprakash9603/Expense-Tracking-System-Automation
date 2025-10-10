#Feature: New Expense UI
#  As a user
#  I want to create new expenses through the UI
#  So that I can track my financial transactions
#
#  Background:
#    Given I am on the new expense page
#    And I am authenticated with a valid JWT token
#    And the form has loaded successfully
#
#  Scenario: New expense form loads with default values
#    When the new expense form initializes
#    Then I should see the form title "Add New Expense"
#    And I should see a close button (×) in the top right
#    And I should see required field indicators (red asterisks)
#    And I should see the expense name field with autocomplete
#    And I should see the amount field
#    And I should see the date field with today's date
#    And I should see the transaction type field defaulted to "Loss"
#    And I should see the category field with autocomplete
#    And I should see the payment method field with autocomplete
#    And I should see the comments field (optional)
#
#  Scenario: Fill expense name with autocomplete suggestions
#    Given I am on the new expense form
#    When I click on the expense name field
#    Then I should see a dropdown with expense suggestions
#    When I type "Groc" in the expense name field
#    Then I should see filtered suggestions containing "Groc"
#    And matching text should be highlighted in teal color
#    When I click on a suggestion
#    Then the expense name field should be populated with the selected value
#
#  Scenario: Enter expense amount with validation
#    Given I am on the new expense form
#    When I enter "1500" in the amount field
#    Then the amount field should accept the numeric value
#    When I enter "abc" in the amount field
#    Then the field should not accept non-numeric characters
#    When I leave the amount field empty and try to submit
#    Then I should see a validation error "Amount is required."
#
#  Scenario: Select date with date picker
#    Given I am on the new expense form
#    When I click on the date field
#    Then I should see a date picker popup
#    And I should not be able to select future dates
#    When I select a date from the picker
#    Then the date field should be populated with the selected date
#    And the format should be DD-MM-YYYY
#
#  Scenario: Select transaction type
#    Given I am on the new expense form
#    When I click on the transaction type dropdown
#    Then I should see options: "Gain" and "Loss"
#    When I select "Gain"
#    Then the transaction type should be set to "Gain"
#    And the field should show the selected value
#
#  Scenario: Select category with autocomplete
#    Given I am on the new expense form
#    When I click on the category field
#    Then I should see a dropdown with available categories
#    When I type "Foo" in the category field
#    Then I should see filtered categories containing "Foo"
#    And matching text should be highlighted in teal color
#    When I select a category
#    Then the category field should be populated with the selected category
#
#  Scenario: Select payment method with autocomplete
#    Given I am on the new expense form
#    When I click on the payment method field
#    Then I should see options: "Cash", "Credit Due", "Credit Paid"
#    When I select "Credit Due"
#    Then the payment method should be set to "Credit Due"
#    And the field should show the selected value
#
#  Scenario: Add optional comments
#    Given I am on the new expense form
#    When I click on the comments field
#    Then I should be able to enter multi-line text
#    And the field should expand as I type more content
#    And there should be no character limit enforced
#
#  Scenario: Link budgets to expense
#    Given I am on the new expense form
#    When I click the "Link Budgets" button
#    Then I should see a table/list of available budgets
#    And I should see checkboxes for each budget
#    And I should see budget details (name, description, dates, amounts)
#    When I select budgets using checkboxes
#    Then the selected budgets should be highlighted
#    When I click the close table button (X)
#    Then the budget table should be hidden
#
#  Scenario: Budget table responsive behavior
#    Given I am viewing the budget table on mobile
#    When the screen width is less than 640px
#    Then the table should switch to card view
#    And each budget should be displayed as a card
#    And I should still be able to select budgets using checkboxes
#
#  Scenario: Submit new expense successfully
#    Given I have filled all required fields correctly
#    And I have entered "Grocery Shopping" as expense name
#    And I have entered "1500" as amount
#    And I have selected today's date
#    And I have selected "Loss" as transaction type
#    When I click the "Submit" button
#    Then the expense should be created successfully
#    And I should see a success toast message
#    And I should be redirected to the previous page
#
#  Scenario: Form validation on submit
#    Given I am on the new expense form
#    When I click "Submit" without filling required fields
#    Then I should see validation errors for:
#      | Field | Error Message |
#      | Expense Name | Expense title is required. |
#      | Amount | Amount is required. |
#      | Date | Date is required. |
#    And the form should not be submitted
#    And error fields should have red borders
#
#  Scenario: Close form without saving
#    Given I am on the new expense form
#    And I have entered some data
#    When I click the close button (×)
#    Then I should be redirected to the previous page
#    And the entered data should be discarded
#
#  Scenario: Form responsive behavior on mobile
#    Given I am viewing the new expense form on mobile
#    When the screen width is less than 640px
#    Then the form should take full screen width
#    And form fields should stack vertically
#    And field labels should be full width
#    And the submit button should be full width
#    And font sizes should be adjusted for mobile
#
#  Scenario: Loading states in form
#    Given I am on the new expense form
#    When the categories are loading
#    Then I should see a loading indicator in the category field
#    When the expense suggestions are loading
#    Then I should see a loading indicator in the expense name field
#    When I submit the form
#    Then the submit button should show a loading state
#
#  Scenario: Error handling in form
#    Given I am on the new expense form
#    When there is an error loading categories
#    Then I should see an error message in the category field
#    When there is an error submitting the form
#    Then I should see an error toast message
#    And the form should remain open for corrections
#
#  Scenario: Autocomplete highlighting
#    Given I am using any autocomplete field
#    When I type text that matches suggestions
#    Then the matching portions should be highlighted in teal color
#    And the highlighting should be case-insensitive
#    And special characters should be escaped properly
#
#  Scenario: Payment method credit due calculation
#    Given I am on the new expense form
#    When I select "Credit Due" as payment method
#    And I enter "2000" as amount
#    Then the system should automatically set creditDue to 2000
#    When I select "Cash" as payment method
#    Then the creditDue should be set to 0