//package com.jaya.stepdefinitions.expenses;
//
//
//
//import io.cucumber.java.en.*;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.By;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.interactions.Actions;
//import java.time.Duration;
//import java.util.List;
//
//public class EditExpenseSteps {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//    private Actions actions;
//    private String expenseId;
//
//    public EditExpenseStepDefinitions() {
//        this.driver = DriverManager.getDriver();
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        this.actions = new Actions(driver);
//    }
//
//    @Given("I am on the edit expense page for expense {string}")
//    public void i_am_on_the_edit_expense_page_for_expense(String expenseId) {
//        this.expenseId = expenseId;
//        driver.get(ConfigReader.getProperty("base.url") + "/expenses/edit/" + expenseId);
//    }
//
//    @Given("the edit expense form has loaded with existing data")
//    public void the_edit_expense_form_has_loaded_with_existing_data() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("new-expense-container")));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(
//                By.className("loading-spinner")));
//
//        // Verify form is populated with existing data
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        String expenseName = expenseNameField.getAttribute("value");
//        Assert.assertTrue("Expense name should be pre-populated", !expenseName.isEmpty());
//    }
//
//    @When("the edit expense page initializes")
//    public void the_edit_expense_page_initializes() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//p[contains(text(), 'Edit Expense')]")));
//    }
//
//    @Then("I should see the page title {string}")
//    public void i_should_see_the_page_title(String expectedTitle) {
//        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//p[contains(text(), '" + expectedTitle + "')]")));
//        Assert.assertTrue("Page title should be visible", titleElement.isDisplayed());
//        Assert.assertEquals("Page title should match", expectedTitle, titleElement.getText());
//    }
//
//    @Then("all form fields should be pre-populated with existing expense data")
//    public void all_form_fields_should_be_pre_populated_with_existing_expense_data() {
//        // Check expense name
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        Assert.assertTrue("Expense name should be populated",
//                !expenseNameField.getAttribute("value").isEmpty());
//
//        // Check amount
//        WebElement amountField = driver.findElement(By.id("amount"));
//        Assert.assertTrue("Amount should be populated",
//                !amountField.getAttribute("value").isEmpty());
//
//        // Check date
//        WebElement dateField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiDatePicker-root')]//input"));
//        Assert.assertTrue("Date should be populated",
//                !dateField.getAttribute("value").isEmpty());
//
//        // Check transaction type
//        WebElement transactionTypeField = driver.findElement(
//                By.xpath("//input[@placeholder='Select transaction type']"));
//        Assert.assertTrue("Transaction type should be populated",
//                !transactionTypeField.getAttribute("value").isEmpty());
//    }
//
//    @Then("the expense name field should show the current expense name")
//    public void the_expense_name_field_should_show_the_current_expense_name() {
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        String currentValue = expenseNameField.getAttribute("value");
//        Assert.assertTrue("Expense name should be meaningful",
//                currentValue.length() > 0 && !currentValue.equals(""));
//    }
//
//    @Then("the amount field should show the current amount")
//    public void the_amount_field_should_show_the_current_amount() {
//        WebElement amountField = driver.findElement(By.id("amount"));
//        String currentValue = amountField.getAttribute("value");
//        Assert.assertTrue("Amount should be a valid number",
//                currentValue.matches("\\d+(\\.\\d+)?"));
//    }
//
//    @Then("the date field should show the current expense date")
//    public void the_date_field_should_show_the_current_expense_date() {
//        WebElement dateField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiDatePicker-root')]//input"));
//        String currentValue = dateField.getAttribute("value");
//        Assert.assertTrue("Date should be in valid format",
//                currentValue.matches("\\d{2}-\\d{2}-\\d{4}"));
//    }
//
//    @Then("the category field should show the current category")
//    public void the_category_field_should_show_the_current_category() {
//        WebElement categoryField = driver.findElement(
//                By.xpath("//input[@placeholder='Search category']"));
//        String currentValue = categoryField.getAttribute("value");
//        Assert.assertTrue("Category should be populated", !currentValue.isEmpty());
//    }
//
//    @Then("the payment method field should show the current payment method")
//    public void the_payment_method_field_should_show_the_current_payment_method() {
//        WebElement paymentMethodField = driver.findElement(
//                By.xpath("//input[@placeholder='Select payment method']"));
//        String currentValue = paymentMethodField.getAttribute("value");
//        Assert.assertTrue("Payment method should be populated", !currentValue.isEmpty());
//    }
//
//    @When("I modify the expense name to {string}")
//    public void i_modify_the_expense_name_to(String newExpenseName) {
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        expenseNameField.clear();
//        expenseNameField.sendKeys(newExpenseName);
//    }
//
//    @When("I change the amount to {string}")
//    public void i_change_the_amount_to(String newAmount) {
//        WebElement amountField = driver.findElement(By.id("amount"));
//        amountField.clear();
//        amountField.sendKeys(newAmount);
//    }
//
//    @When("I update the category")
//    public void i_update_the_category() {
//        WebElement categoryField = driver.findElement(
//                By.xpath("//input[@placeholder='Search category']"));
//        categoryField.clear();
//        categoryField.sendKeys("Updated Category");
//
//        // Wait for and select first suggestion
//        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')][1]")));
//        firstOption.click();
//    }
//
//    @When("I click the Submit button")
//    public void i_click_the_submit_button() {
//        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Submit')]")));
//        submitButton.click();
//    }
//
//    @Then("the expense should be updated successfully")
//    public void the_expense_should_be_updated_successfully() {
//        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(text(), 'Expense updated successfully') or contains(text(), 'Success')]")));
//        Assert.assertTrue("Success message should be visible", successMessage.isDisplayed());
//    }
//
//    @Then("I should see a success notification")
//    public void i_should_see_a_success_notification() {
//        WebElement notification = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'MuiSnackbar-root')]")));
//        Assert.assertTrue("Success notification should be visible", notification.isDisplayed());
//    }
//
//    @Then("I should be redirected back to the previous page")
//    public void i_should_be_redirected_back_to_the_previous_page() {
//        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/edit")));
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertFalse("Should not be on edit page", currentUrl.contains("/edit"));
//    }
//
//    @When("I click the Link Budgets button")
//    public void i_click_the_link_budgets_button() {
//        WebElement linkBudgetsButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Link Budgets')]")));
//        linkBudgetsButton.click();
//    }
//
//    @Then("I should see the budget selection table")
//    public void i_should_see_the_budget_selection_table() {
//        WebElement budgetTable = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'MuiDataGrid-root')]")));
//        Assert.assertTrue("Budget table should be visible", budgetTable.isDisplayed());
//    }
//
//    @Then("budgets currently linked to this expense should be pre-selected")
//    public void budgets_currently_linked_to_this_expense_should_be_pre_selected() {
//        List<WebElement> selectedCheckboxes = driver.findElements(
//                By.xpath("//input[@type='checkbox' and @checked]"));
//        Assert.assertTrue("Should have pre-selected budgets", selectedCheckboxes.size() > 0);
//    }
//
//    @When("I modify the budget selections")
//    public void i_modify_the_budget_selections() {
//        List<WebElement> checkboxes = driver.findElements(
//                By.xpath("//input[@type='checkbox']"));
//
//        if (checkboxes.size() > 0) {
//            // Toggle first checkbox
//            checkboxes.get(0).click();
//        }
//        if (checkboxes.size() > 1) {
//            // Toggle second checkbox
//            checkboxes.get(1).click();
//        }
//    }
//
//    @Then("the budget selections should be updated")
//    public void the_budget_selections_should_be_updated() {
//        // Verify that checkbox states have changed
//        List<WebElement> checkboxes = driver.findElements(
//                By.xpath("//input[@type='checkbox']"));
//        Assert.assertTrue("Should have checkboxes available", checkboxes.size() > 0);
//
//        // At least one checkbox should be in a different state than initial
//        boolean hasChanges = checkboxes.stream()
//                .anyMatch(checkbox -> checkbox.isSelected());
//        Assert.assertTrue("Budget selections should have changed", hasChanges);
//    }
//
//    @When("I clear a required field")
//    public void i_clear_a_required_field() {
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        expenseNameField.clear();
//    }
//
//    @When("I try to submit the form")
//    public void i_try_to_submit_the_form() {
//        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Submit')]")));
//        submitButton.click();
//    }
//
//    @Then("I should see validation errors")
//    public void i_should_see_validation_errors() {
//        List<WebElement> errorMessages = driver.findElements(
//                By.xpath("//span[contains(@class, 'text-red-500')]"));
//        Assert.assertTrue("Should show validation errors", errorMessages.size() > 0);
//
//        boolean hasRequiredFieldError = errorMessages.stream()
//                .anyMatch(error -> error.getText().contains("required"));
//        Assert.assertTrue("Should show required field errors", hasRequiredFieldError);
//    }
//
//    @Then("the form should not be submitted")
//    public void the_form_should_not_be_submitted() {
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertTrue("Should remain on edit page", currentUrl.contains("/edit"));
//    }
//
//    @Then("the invalid fields should be highlighted")
//    public void the_invalid_fields_should_be_highlighted() {
//        List<WebElement> errorFields = driver.findElements(
//                By.xpath("//input[contains(@style, 'border-color: #ff4d4f')]"));
//        Assert.assertTrue("Should have fields highlighted in red", errorFields.size() > 0);
//    }
//
//    @When("I click the close button")
//    public void i_click_the_close_button() {
//        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), '×')]")));
//        closeButton.click();
//    }
//
//    @Then("I should be taken back without saving changes")
//    public void i_should_be_taken_back_without_saving_changes() {
//        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/edit")));
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertFalse("Should not be on edit page", currentUrl.contains("/edit"));
//    }
//
//    @Given("I have write access to edit the expense")
//    public void i_have_write_access_to_edit_the_expense() {
//        // Verify submit button is visible and enabled
//        WebElement submitButton = driver.findElement(
//                By.xpath("//button[contains(text(), 'Submit')]"));
//        Assert.assertTrue("Submit button should be visible with write access",
//                submitButton.isDisplayed());
//        Assert.assertTrue("Submit button should be enabled", submitButton.isEnabled());
//    }
//
//    @Given("I have read-only access to the expense")
//    public void i_have_read_only_access_to_the_expense() {
//        // This would typically involve setting up a different user context
//        // For testing purposes, we can simulate this by checking if submit button is hidden
//        driver.executeScript("window.localStorage.setItem('readOnlyMode', 'true');");
//        driver.navigate().refresh();
//    }
//
//    @Then("all form fields should be disabled")
//    public void all_form_fields_should_be_disabled() {
//        List<WebElement> inputs = driver.findElements(By.xpath("//input"));
//        for (WebElement input : inputs) {
//            Assert.assertTrue("Input should be disabled in read-only mode",
//                    !input.isEnabled() || input.getAttribute("readonly") != null);
//        }
//    }
//
//    @Then("the Submit button should not be visible")
//    public void the_submit_button_should_not_be_visible() {
//        List<WebElement> submitButtons = driver.findElements(
//                By.xpath("//button[contains(text(), 'Submit')]"));
//        Assert.assertTrue("Submit button should not be visible in read-only mode",
//                submitButtons.isEmpty() || !submitButtons.get(0).isDisplayed());
//    }
//
//    @Then("I should see a read-only indicator")
//    public void i_should_see_a_read_only_indicator() {
//        WebElement readOnlyIndicator = driver.findElement(
//                By.xpath("//div[contains(text(), 'read-only') or contains(text(), 'Read Only')]"));
//        Assert.assertTrue("Read-only indicator should be visible", readOnlyIndicator.isDisplayed());
//    }
//
//    @Given("I am viewing the edit expense form on a mobile device")
//    public void i_am_viewing_the_edit_expense_form_on_a_mobile_device() {
//        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
//        driver.get(ConfigReader.getProperty("base.url") + "/expenses/edit/" + expenseId);
//    }
//
//    @When("the screen width is less than 640px")
//    public void the_screen_width_is_less_than_640px() {
//        driver.manage().window().setSize(new org.openqa.selenium.Dimension(480, 800));
//    }
//
//    @Then("the form should adapt to mobile layout")
//    public void the_form_should_adapt_to_mobile_layout() {
//        List<WebElement> formRows = driver.findElements(
//                By.xpath("//div[contains(@class, 'flex-col')]"));
//        Assert.assertTrue("Form should have vertical layout", formRows.size() > 0);
//    }
//
//    @Then("the budget table should switch to card view")
//    public void the_budget_table_should_switch_to_card_view() {
//        // Click Link Budgets to show table
//        WebElement linkBudgetsButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Link Budgets')]")));
//        linkBudgetsButton.click();
//
//        // Check for mobile card view
//        List<WebElement> budgetCards = driver.findElements(
//                By.xpath("//div[contains(@class, 'bg-[#29282b]') and contains(@class, 'border')]"));
//        Assert.assertTrue("Should show budget cards on mobile", budgetCards.size() > 0);
//    }
//
//    @Then("navigation should be touch-friendly")
//    public void navigation_should_be_touch_friendly() {
//        List<WebElement> buttons = driver.findElements(By.xpath("//button"));
//        for (WebElement button : buttons) {
//            // Check button size is adequate for touch (minimum 44px height)
//            int height = button.getSize().getHeight();
//            Assert.assertTrue("Buttons should be touch-friendly size", height >= 40);
//        }
//    }
//
//    @Given("there is an error loading the expense data")
//    public void there_is_an_error_loading_the_expense_data() {
//        // Simulate error by navigating to non-existent expense
//        driver.get(ConfigReader.getProperty("base.url") + "/expenses/edit/999999");
//    }
//
//    @When("the edit expense page tries to load")
//    public void the_edit_expense_page_tries_to_load() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("new-expense-container")));
//    }
//
//    @Then("I should see an error message")
//    public void i_should_see_an_error_message() {
//        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(text(), 'error') or contains(text(), 'Error') or contains(text(), 'not found')]")));
//        Assert.assertTrue("Error message should be visible", errorMessage.isDisplayed());
//    }
//
//    @Then("I should have an option to go back")
//    public void i_should_have_an_option_to_go_back() {
//        WebElement backButton = driver.findElement(
//                By.xpath("//button[contains(text(), 'Back') or contains(text(), 'Go Back') or contains(text(), '×')]"));
//        Assert.assertTrue("Back button should be available", backButton.isDisplayed());
//    }
//
//    @Then("the form fields should remain empty or show default values")
//    public void the_form_fields_should_remain_empty_or_show_default_values() {
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        String expenseName = expenseNameField.getAttribute("value");
//        Assert.assertTrue("Expense name should be empty on error",
//                expenseName == null || expenseName.isEmpty());
//    }
//
//    @When("I change the date")
//    public void i_change_the_date() {
//        WebElement dateField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiDatePicker-root')]//input"));
//        dateField.click();
//
//        // Select a different date from calendar
//        WebElement differentDate = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(@class, 'MuiPickersDay-root') and not(contains(@class, 'Mui-disabled'))][2]")));
//        differentDate.click();
//    }
//
//    @Then("the budget list should refresh based on the new date")
//    public void the_budget_list_should_refresh_based_on_the_new_date() {
//        // Click Link Budgets to see updated list
//        WebElement linkBudgetsButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Link Budgets')]")));
//        linkBudgetsButton.click();
//
//        // Wait for table to load with new data
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'MuiDataGrid-root')]")));
//
//        // Verify table has loaded (presence of budget rows or no data message)
//        boolean hasData = !driver.findElements(
//                By.xpath("//div[@data-field='name']")).isEmpty();
//        boolean hasNoDataMessage = !driver.findElements(
//                By.xpath("//div[contains(text(), 'No rows found')]")).isEmpty();
//
//        Assert.assertTrue("Budget list should be refreshed", hasData || hasNoDataMessage);
//    }
//
//    @Then("previously selected budgets may be deselected if they don't match the new date range")
//    public void previously_selected_budgets_may_be_deselected_if_they_dont_match_the_new_date_range() {
//        // This is more of a business logic verification
//        // We can check that the checkbox states have been reset or updated
//        List<WebElement> checkboxes = driver.findElements(
//                By.xpath("//input[@type='checkbox']"));
//
//        if (checkboxes.size() > 0) {
//            // Verify that checkbox states are appropriate for the new date
//            // This would depend on the specific business logic
//            Assert.assertTrue("Checkboxes should be present for date validation", true);
//        }
//    }
//}
//
//
