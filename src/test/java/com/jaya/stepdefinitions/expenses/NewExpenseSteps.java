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
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.Keys;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//public class NewExpenseSteps {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//    private Actions actions;
//
//    public NewExpenseStepDefinitions() {
//        this.driver = DriverManager.getDriver();
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        this.actions = new Actions(driver);
//    }
//
//    @Given("I am on the new expense page")
//    public void i_am_on_the_new_expense_page() {
//        driver.get(ConfigReader.getProperty("base.url") + "/expenses/new");
//    }
//
//    @Given("the new expense form has loaded")
//    public void the_new_expense_form_has_loaded() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("new-expense-container")));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(
//                By.className("loading-spinner")));
//    }
//
//    @When("the new expense page initializes")
//    public void the_new_expense_page_initializes() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//p[contains(text(), 'Add New Expense')]")));
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
//    @Then("I should see a close button in the top right")
//    public void i_should_see_a_close_button_in_the_top_right() {
//        WebElement closeButton = driver.findElement(
//                By.xpath("//button[contains(text(), '×')]"));
//        Assert.assertTrue("Close button should be visible", closeButton.isDisplayed());
//    }
//
//    @Then("I should see the expense name field with autocomplete")
//    public void i_should_see_the_expense_name_field_with_autocomplete() {
//        WebElement expenseNameField = driver.findElement(By.id("expenseName"));
//        Assert.assertTrue("Expense name field should be visible", expenseNameField.isDisplayed());
//
//        // Check for autocomplete functionality
//        WebElement autocompleteContainer = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]"));
//        Assert.assertTrue("Autocomplete container should be present", autocompleteContainer.isDisplayed());
//    }
//
//    @Then("I should see the amount field")
//    public void i_should_see_the_amount_field() {
//        WebElement amountField = driver.findElement(By.id("amount"));
//        Assert.assertTrue("Amount field should be visible", amountField.isDisplayed());
//        Assert.assertEquals("Amount field should be number type", "number",
//                amountField.getAttribute("type"));
//    }
//
//    @Then("I should see the date picker")
//    public void i_should_see_the_date_picker() {
//        WebElement datePicker = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiDatePicker-root')]"));
//        Assert.assertTrue("Date picker should be visible", datePicker.isDisplayed());
//    }
//
//    @Then("I should see the transaction type dropdown")
//    public void i_should_see_the_transaction_type_dropdown() {
//        WebElement transactionTypeField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input[@placeholder='Select transaction type']"));
//        Assert.assertTrue("Transaction type field should be visible", transactionTypeField.isDisplayed());
//    }
//
//    @Then("I should see the category autocomplete field")
//    public void i_should_see_the_category_autocomplete_field() {
//        WebElement categoryField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input[@placeholder='Search category']"));
//        Assert.assertTrue("Category field should be visible", categoryField.isDisplayed());
//    }
//
//    @Then("I should see the payment method autocomplete field")
//    public void i_should_see_the_payment_method_autocomplete_field() {
//        WebElement paymentMethodField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input[@placeholder='Select payment method']"));
//        Assert.assertTrue("Payment method field should be visible", paymentMethodField.isDisplayed());
//    }
//
//    @Then("I should see the comments textarea")
//    public void i_should_see_the_comments_textarea() {
//        WebElement commentsField = driver.findElement(By.id("comments"));
//        Assert.assertTrue("Comments field should be visible", commentsField.isDisplayed());
//        Assert.assertTrue("Comments should be multiline",
//                commentsField.getAttribute("rows") != null ||
//                        commentsField.getTagName().equals("textarea"));
//    }
//
//    @Then("I should see the Link Budgets button")
//    public void i_should_see_the_link_budgets_button() {
//        WebElement linkBudgetsButton = driver.findElement(
//                By.xpath("//button[contains(text(), 'Link Budgets')]"));
//        Assert.assertTrue("Link Budgets button should be visible", linkBudgetsButton.isDisplayed());
//    }
//
//    @Then("I should see the Submit button")
//    public void i_should_see_the_submit_button() {
//        WebElement submitButton = driver.findElement(
//                By.xpath("//button[contains(text(), 'Submit')]"));
//        Assert.assertTrue("Submit button should be visible", submitButton.isDisplayed());
//    }
//
//    @When("I click on the expense name field")
//    public void i_click_on_the_expense_name_field() {
//        WebElement expenseNameField = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input")));
//        expenseNameField.click();
//    }
//
//    @Then("I should see a dropdown with expense suggestions")
//    public void i_should_see_a_dropdown_with_expense_suggestions() {
//        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-popper')]")));
//        Assert.assertTrue("Suggestions dropdown should be visible", dropdown.isDisplayed());
//
//        List<WebElement> suggestions = driver.findElements(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]"));
//        Assert.assertTrue("Should have suggestion options", suggestions.size() > 0);
//    }
//
//    @Then("the suggestions should be based on previous expenses")
//    public void the_suggestions_should_be_based_on_previous_expenses() {
//        List<WebElement> suggestions = driver.findElements(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]"));
//
//        for (WebElement suggestion : suggestions) {
//            String suggestionText = suggestion.getText();
//            Assert.assertTrue("Suggestion should not be empty", !suggestionText.trim().isEmpty());
//        }
//    }
//
//    @When("I type {string} in the expense name field")
//    public void i_type_in_the_expense_name_field(String text) {
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        expenseNameField.clear();
//        expenseNameField.sendKeys(text);
//    }
//
//    @Then("the suggestions should filter to match my input")
//    public void the_suggestions_should_filter_to_match_my_input() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]")));
//
//        List<WebElement> suggestions = driver.findElements(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]"));
//
//        for (WebElement suggestion : suggestions) {
//            String suggestionText = suggestion.getText().toLowerCase();
//            Assert.assertTrue("Suggestion should contain filter text",
//                    suggestionText.contains("gro")); // Based on "Groceries" input
//        }
//    }
//
//    @Then("matching text should be highlighted in teal")
//    public void matching_text_should_be_highlighted_in_teal() {
//        WebElement highlightedText = driver.findElement(
//                By.xpath("//mark[contains(@style, 'color: #00dac6')]"));
//        Assert.assertTrue("Highlighted text should be visible", highlightedText.isDisplayed());
//    }
//
//    @When("I click on a suggestion")
//    public void i_click_on_a_suggestion() {
//        WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')][1]")));
//        suggestion.click();
//    }
//
//    @Then("the expense name field should be populated with the selected suggestion")
//    public void the_expense_name_field_should_be_populated_with_the_selected_suggestion() {
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        String fieldValue = expenseNameField.getAttribute("value");
//        Assert.assertTrue("Field should be populated", !fieldValue.trim().isEmpty());
//    }
//
//    @When("I enter {string} in the amount field")
//    public void i_enter_in_the_amount_field(String amount) {
//        WebElement amountField = driver.findElement(By.id("amount"));
//        amountField.clear();
//        amountField.sendKeys(amount);
//    }
//
//    @Then("the amount should be accepted")
//    public void the_amount_should_be_accepted() {
//        WebElement amountField = driver.findElement(By.id("amount"));
//        String fieldValue = amountField.getAttribute("value");
//        Assert.assertTrue("Amount should be entered", !fieldValue.isEmpty());
//    }
//
//    @When("I enter {string} in the amount field")
//    public void i_enter_invalid_amount_in_the_amount_field(String invalidAmount) {
//        WebElement amountField = driver.findElement(By.id("amount"));
//        amountField.clear();
//        amountField.sendKeys(invalidAmount);
//    }
//
//    @Then("the field should not accept the invalid input")
//    public void the_field_should_not_accept_the_invalid_input() {
//        WebElement amountField = driver.findElement(By.id("amount"));
//        String fieldValue = amountField.getAttribute("value");
//        Assert.assertTrue("Invalid input should be rejected or cleaned",
//                fieldValue.isEmpty() || fieldValue.matches("\\d*\\.?\\d*"));
//    }
//
//    @When("I click on the date picker")
//    public void i_click_on_the_date_picker() {
//        WebElement datePicker = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//div[contains(@class, 'MuiDatePicker-root')]//input")));
//        datePicker.click();
//    }
//
//    @Then("I should see a calendar popup")
//    public void i_should_see_a_calendar_popup() {
//        WebElement calendar = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'MuiPickersPopper-root')]")));
//        Assert.assertTrue("Calendar popup should be visible", calendar.isDisplayed());
//    }
//
//    @Then("future dates should be disabled")
//    public void future_dates_should_be_disabled() {
//        List<WebElement> futureDates = driver.findElements(
//                By.xpath("//button[contains(@class, 'MuiPickersDay-root') and contains(@class, 'Mui-disabled')]"));
//        Assert.assertTrue("Future dates should be disabled", futureDates.size() > 0);
//    }
//
//    @When("I select a valid date")
//    public void i_select_a_valid_date() {
//        WebElement validDate = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(@class, 'MuiPickersDay-root') and not(contains(@class, 'Mui-disabled'))][1]")));
//        validDate.click();
//    }
//
//    @Then("the date field should be populated")
//    public void the_date_field_should_be_populated() {
//        WebElement dateField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiDatePicker-root')]//input"));
//        String fieldValue = dateField.getAttribute("value");
//        Assert.assertTrue("Date field should be populated", !fieldValue.isEmpty());
//    }
//
//    @Then("the calendar should close")
//    public void the_calendar_should_close() {
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(
//                By.xpath("//div[contains(@class, 'MuiPickersPopper-root')]")));
//    }
//
//    @When("I click on the transaction type field")
//    public void i_click_on_the_transaction_type_field() {
//        WebElement transactionTypeField = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//input[@placeholder='Select transaction type']")));
//        transactionTypeField.click();
//    }
//
//    @Then("I should see options: {string} and {string}")
//    public void i_should_see_options_and(String option1, String option2) {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]")));
//
//        List<WebElement> options = driver.findElements(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]"));
//
//        List<String> optionTexts = options.stream()
//                .map(WebElement::getText)
//                .toList();
//
//        Assert.assertTrue("Should contain " + option1, optionTexts.contains(option1));
//        Assert.assertTrue("Should contain " + option2, optionTexts.contains(option2));
//    }
//
//    @When("I select {string}")
//    public void i_select_transaction_type(String optionText) {
//        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option') and text()='" + optionText + "']")));
//        option.click();
//    }
//
//    @Then("the transaction type should be set to {string}")
//    public void the_transaction_type_should_be_set_to(String expectedType) {
//        WebElement transactionTypeField = driver.findElement(
//                By.xpath("//input[@placeholder='Select transaction type']"));
//        String fieldValue = transactionTypeField.getAttribute("value");
//        Assert.assertEquals("Transaction type should be set", expectedType, fieldValue);
//    }
//
//    @When("I click on the category field")
//    public void i_click_on_the_category_field() {
//        WebElement categoryField = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//input[@placeholder='Search category']")));
//        categoryField.click();
//    }
//
//    @Then("I should see a list of available categories")
//    public void i_should_see_a_list_of_available_categories() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]")));
//
//        List<WebElement> categories = driver.findElements(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]"));
//        Assert.assertTrue("Should have category options", categories.size() > 0);
//    }
//
//    @When("I type {string} in the category field")
//    public void i_type_in_the_category_field(String categoryText) {
//        WebElement categoryField = driver.findElement(
//                By.xpath("//input[@placeholder='Search category']"));
//        categoryField.clear();
//        categoryField.sendKeys(categoryText);
//    }
//
//    @Then("the categories should filter based on my input")
//    public void the_categories_should_filter_based_on_my_input() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]")));
//
//        List<WebElement> filteredCategories = driver.findElements(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]"));
//
//        for (WebElement category : filteredCategories) {
//            String categoryText = category.getText().toLowerCase();
//            Assert.assertTrue("Category should match filter",
//                    categoryText.contains("foo")); // Based on "Food" input
//        }
//    }
//
//    @When("I select a category")
//    public void i_select_a_category() {
//        WebElement category = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')][1]")));
//        category.click();
//    }
//
//    @Then("the category field should be populated")
//    public void the_category_field_should_be_populated() {
//        WebElement categoryField = driver.findElement(
//                By.xpath("//input[@placeholder='Search category']"));
//        String fieldValue = categoryField.getAttribute("value");
//        Assert.assertTrue("Category field should be populated", !fieldValue.isEmpty());
//    }
//
//    @When("I click on the payment method field")
//    public void i_click_on_the_payment_method_field() {
//        WebElement paymentMethodField = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//input[@placeholder='Select payment method']")));
//        paymentMethodField.click();
//    }
//
//    @Then("I should see payment method options: {string}, {string}, {string}")
//    public void i_should_see_payment_method_options(String option1, String option2, String option3) {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]")));
//
//        List<WebElement> options = driver.findElements(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')]"));
//
//        List<String> optionTexts = options.stream()
//                .map(WebElement::getText)
//                .toList();
//
//        Assert.assertTrue("Should contain " + option1, optionTexts.contains(option1));
//        Assert.assertTrue("Should contain " + option2, optionTexts.contains(option2));
//        Assert.assertTrue("Should contain " + option3, optionTexts.contains(option3));
//    }
//
//    @When("I select a payment method")
//    public void i_select_a_payment_method() {
//        WebElement paymentMethod = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option')][1]")));
//        paymentMethod.click();
//    }
//
//    @Then("the payment method field should be populated")
//    public void the_payment_method_field_should_be_populated() {
//        WebElement paymentMethodField = driver.findElement(
//                By.xpath("//input[@placeholder='Select payment method']"));
//        String fieldValue = paymentMethodField.getAttribute("value");
//        Assert.assertTrue("Payment method field should be populated", !fieldValue.isEmpty());
//    }
//
//    @When("I enter comments in the textarea")
//    public void i_enter_comments_in_the_textarea() {
//        WebElement commentsField = driver.findElement(By.id("comments"));
//        commentsField.clear();
//        commentsField.sendKeys("Test expense comment for automation");
//    }
//
//    @Then("the comments should be accepted")
//    public void the_comments_should_be_accepted() {
//        WebElement commentsField = driver.findElement(By.id("comments"));
//        String fieldValue = commentsField.getAttribute("value");
//        Assert.assertTrue("Comments should be entered", !fieldValue.isEmpty());
//        Assert.assertTrue("Comments should contain expected text",
//                fieldValue.contains("Test expense comment"));
//    }
//
//    @When("I click the Link Budgets button")
//    public void i_click_the_link_budgets_button() {
//        WebElement linkBudgetsButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Link Budgets')]")));
//        linkBudgetsButton.click();
//    }
//
//    @Then("I should see a budget selection table")
//    public void i_should_see_a_budget_selection_table() {
//        WebElement budgetTable = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'MuiDataGrid-root')]")));
//        Assert.assertTrue("Budget table should be visible", budgetTable.isDisplayed());
//    }
//
//    @Then("each budget should have a checkbox for selection")
//    public void each_budget_should_have_a_checkbox_for_selection() {
//        List<WebElement> checkboxes = driver.findElements(
//                By.xpath("//input[@type='checkbox']"));
//        Assert.assertTrue("Should have budget checkboxes", checkboxes.size() > 0);
//    }
//
//    @Then("I should see budget details like name, description, dates, and amounts")
//    public void i_should_see_budget_details_like_name_description_dates_and_amounts() {
//        List<WebElement> nameColumns = driver.findElements(
//                By.xpath("//div[@data-field='name']"));
//        List<WebElement> descriptionColumns = driver.findElements(
//                By.xpath("//div[@data-field='description']"));
//        List<WebElement> amountColumns = driver.findElements(
//                By.xpath("//div[@data-field='amount']"));
//
//        Assert.assertTrue("Should show budget names", nameColumns.size() > 0);
//        Assert.assertTrue("Should show budget descriptions", descriptionColumns.size() > 0);
//        Assert.assertTrue("Should show budget amounts", amountColumns.size() > 0);
//    }
//
//    @When("I select some budgets using checkboxes")
//    public void i_select_some_budgets_using_checkboxes() {
//        List<WebElement> checkboxes = driver.findElements(
//                By.xpath("//input[@type='checkbox']"));
//
//        if (checkboxes.size() > 0) {
//            checkboxes.get(0).click();
//        }
//        if (checkboxes.size() > 1) {
//            checkboxes.get(1).click();
//        }
//    }
//
//    @Then("the selected budgets should be visually indicated")
//    public void the_selected_budgets_should_be_visually_indicated() {
//        List<WebElement> selectedCheckboxes = driver.findElements(
//                By.xpath("//input[@type='checkbox' and @checked]"));
//        Assert.assertTrue("Should have selected checkboxes", selectedCheckboxes.size() > 0);
//    }
//
//    @When("I fill in all required fields with valid data")
//    public void i_fill_in_all_required_fields_with_valid_data() {
//        // Fill expense name
//        WebElement expenseNameField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiAutocomplete-root')]//input"));
//        expenseNameField.clear();
//        expenseNameField.sendKeys("Test Expense");
//
//        // Fill amount
//        WebElement amountField = driver.findElement(By.id("amount"));
//        amountField.clear();
//        amountField.sendKeys("100");
//
//        // Set date (today)
//        WebElement dateField = driver.findElement(
//                By.xpath("//div[contains(@class, 'MuiDatePicker-root')]//input"));
//        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        dateField.clear();
//        dateField.sendKeys(today);
//
//        // Select transaction type
//        WebElement transactionTypeField = driver.findElement(
//                By.xpath("//input[@placeholder='Select transaction type']"));
//        transactionTypeField.click();
//        WebElement lossOption = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@class, 'MuiAutocomplete-option') and text()='Loss']")));
//        lossOption.click();
//    }
//
//    @When("I click the Submit button")
//    public void i_click_the_submit_button() {
//        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Submit')]")));
//        submitButton.click();
//    }
//
//    @Then("the expense should be created successfully")
//    public void the_expense_should_be_created_successfully() {
//        WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(text(), 'Expense created successfully') or contains(text(), 'Success')]")));
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
//    @Then("I should be redirected to the expenses list")
//    public void i_should_be_redirected_to_the_expenses_list() {
//        wait.until(ExpectedConditions.urlContains("/expenses"));
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertTrue("Should be redirected to expenses list",
//                currentUrl.contains("/expenses") && !currentUrl.contains("/new"));
//    }
//
//    @When("I try to submit without filling required fields")
//    public void i_try_to_submit_without_filling_required_fields() {
//        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Submit')]")));
//        submitButton.click();
//    }
//
//    @Then("I should see validation errors for required fields")
//    public void i_should_see_validation_errors_for_required_fields() {
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
//        Assert.assertTrue("Should remain on new expense page", currentUrl.contains("/new"));
//    }
//
//    @Then("required fields should be highlighted in red")
//    public void required_fields_should_be_highlighted_in_red() {
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
//    @Then("I should be taken back to the previous page")
//    public void i_should_be_taken_back_to_the_previous_page() {
//        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/new")));
//        String currentUrl = driver.getCurrentUrl();
//        Assert.assertFalse("Should not be on new expense page", currentUrl.contains("/new"));
//    }
//
//    @Given("I am viewing the new expense form on a mobile device")
//    public void i_am_viewing_the_new_expense_form_on_a_mobile_device() {
//        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
//        driver.get(ConfigReader.getProperty("base.url") + "/expenses/new");
//    }
//
//    @When("the screen width is less than 640px")
//    public void the_screen_width_is_less_than_640px() {
//        driver.manage().window().setSize(new org.openqa.selenium.Dimension(480, 800));
//    }
//
//    @Then("the form should stack vertically")
//    public void the_form_should_stack_vertically() {
//        List<WebElement> formRows = driver.findElements(
//                By.xpath("//div[contains(@class, 'flex-col')]"));
//        Assert.assertTrue("Form should have vertical layout", formRows.size() > 0);
//    }
//
//    @Then("field labels should be full width")
//    public void field_labels_should_be_full_width() {
//        List<WebElement> labels = driver.findElements(By.xpath("//label"));
//        for (WebElement label : labels) {
//            String style = label.getAttribute("style");
//            // Check if width is 100% or similar full-width styling
//            Assert.assertTrue("Labels should be full width on mobile",
//                    style.contains("width: 100%") || label.getAttribute("class").contains("w-full"));
//        }
//    }
//
//    @Then("input fields should be full width")
//    public void input_fields_should_be_full_width() {
//        List<WebElement> inputs = driver.findElements(By.xpath("//input"));
//        for (WebElement input : inputs) {
//            String style = input.getAttribute("style");
//            String className = input.getAttribute("class");
//            Assert.assertTrue("Inputs should be full width on mobile",
//                    style.contains("width: 100%") || className.contains("w-full") ||
//                            style.contains("max-width: 100%"));
//        }
//    }
//
//    @Then("the submit button should be full width")
//    public void the_submit_button_should_be_full_width() {
//        WebElement submitButton = driver.findElement(
//                By.xpath("//button[contains(text(), 'Submit')]"));
//        String className = submitButton.getAttribute("class");
//        Assert.assertTrue("Submit button should be full width on mobile",
//                className.contains("w-full"));
//    }
//}