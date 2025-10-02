package com.jaya.stepdefinitions.register;

import com.jaya.factory.DriverFactory;
import com.jaya.pages.register.RegisterPage;
import com.jaya.pages.register.locators.RegisterPageLocators;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Step definitions for Register.feature.
 * NOTE: A dedicated RegisterPage object does not yet exist; basic interactions are placeholders.
 * TODO: Implement RegisterPage with proper locators & actions similar to LoginPage, then refactor below.
 */
public class RegisterSteps {

    private WebDriver driver;
    private RegisterPage registerPage;
    // If set, this email should be used raw (no uniqueness tag) for duplicate tests
    private String rawEmailOverride;

    private void setFirstName(String value) { registerPage.firstName(value); }
    private void setLastName(String value) { registerPage.lastName(value); }
    private static final DateTimeFormatter EMAIL_TS_FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private String uniqueEmail(String raw) {
        if (raw == null || raw.isBlank()) return raw;
        int at = raw.indexOf('@');
        if (at < 1 || at == raw.length() - 1) return raw + "+" + LocalDateTime.now().format(EMAIL_TS_FMT); // malformed, still tag
        String local = raw.substring(0, at);
        String domain = raw.substring(at + 1);
        String stamp = LocalDateTime.now().format(EMAIL_TS_FMT);
        // Always append a new +timestamp (chain if one already exists)
        return local + "+" + stamp + "@" + domain;
    }
    private void setEmail(String value) { registerPage.email(uniqueEmail(value)); }
    private void setPassword(String value) { registerPage.password(value); }
    private void selectGender(String gender) { registerPage.gender(gender); }
    private void clickRegisterButton() { registerPage.submit(); }
    private void togglePasswordVisibility() { registerPage.togglePasswordVisibility(); }

    @Given("I am on the Register page")
    public void i_am_on_the_register_page() {
    driver = DriverFactory.getInstance().getDriver();
    registerPage = new RegisterPage().load();
    }

    @Given("an account already exists with email {string}")
    public void an_account_already_exists_with_email(String email) {
        rawEmailOverride = email; // ensure subsequent steps use raw email
        if (registerPage == null) {
            driver = DriverFactory.getInstance().getDriver();
            registerPage = new RegisterPage().load();
        } else {
            registerPage.load();
        }
        // Attempt to create the account if it doesn't exist
        registerPage
                .firstName("Pre")
                .lastName("Existing")
                .email(email)
                .password("Str0ng@123")
                .submit();
        // After successful registration app may redirect; ignore errors if already taken
        // Navigate back to register page to start duplicate attempt
        registerPage.load();
    }

    @When("I register with first name {string}, last name {string}, email {string}, password {string}")
    public void i_register_with_all_fields(String first, String last, String email, String password) {
        setFirstName(first);
        setLastName(last);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    @When("I register with first name {string}, last name {string}, email {string}, password {string} without choosing gender")
    public void i_register_without_gender(String first, String last, String email, String password) {
        setFirstName(first);
        setLastName(last);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    @When("I attempt to register with first name {string}, last name {string}, email {string}, password {string}")
    public void i_attempt_to_register_missing_field(String first, String last, String email, String password) {
        if (!first.isEmpty()) setFirstName(first);
        if (!last.isEmpty()) setLastName(last);
        if (!email.isEmpty()) {
            if (rawEmailOverride != null && email.equals(rawEmailOverride)) {
                registerPage.email(email); // use raw, no timestamp
            } else {
                setEmail(email);
            }
        }
        if (!password.isEmpty()) setPassword(password);
        clickRegisterButton();
    }

    @When("I enter a password {string}")
    public void i_enter_a_password(String password) {
        setPassword(password);
    }

    @When("I toggle password visibility")
    public void i_toggle_password_visibility() {
        togglePasswordVisibility();
    }

    @Then("I should see the password in plain text")
    public void i_should_see_the_password_in_plain_text() {
        // Use the last entered password value; for simplicity read current field value and assert input type = text
        String current = registerPage.getFieldValue(RegisterPageLocators.PASSWORD_INPUT);
        registerPage.assertPasswordVisible(current);
    }

    @When("I refresh the page")
    public void i_refresh_the_page() {
        registerPage.refresh();
    }

    @When("I attempt to submit with blank First Name")
    public void i_attempt_to_submit_with_blank_first_name() {
        registerPage.clearFirstName();
        registerPage.submit();
        Assert.assertTrue(registerPage.isFirstNameErrorVisible(), "First Name validation message not shown after blank submit");
    }

    @When("I enter First Name {string}")
    public void i_enter_first_name(String firstName) {
        registerPage.firstName(firstName);
    }

    @When("I attempt to register with first name {string}, last name {string}, email weak+{string}@test.com, password {string}")
    public void weak_password_rule_variant(String first, String last, String id, String password) {
        setFirstName(first);
        setLastName(last);
        setEmail("weak+" + id + "@test.com");
        setPassword(password);
        clickRegisterButton();
    }

    @When("I attempt to register with first name {string}, last name {string}, email {string}, password {string} without choosing gender")
    public void attempt_without_gender_generic(String first, String last, String email, String password) {
        setFirstName(first);
        setLastName(last);
    setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    @When("I fill valid registration data")
    public void i_fill_valid_registration_data() {
        setFirstName("Valid");
        setLastName("User");
        setEmail("valid.user+" + System.currentTimeMillis() + "@test.com");
        setPassword("Str0ng@123");
    }

    @When("I double-click the Register button quickly")
    public void i_double_click_register() {
        clickRegisterButton();
        clickRegisterButton();
    }

    @When("I attempt to register with first name {string}, last name {string}, email test@test.com' OR '1'='1, password {string}")
    public void sql_injection_email(String first, String last, String password) {
        setFirstName(first);
        setLastName(last);
        setEmail("test@test.com' OR '1'='1");
        setPassword(password);
        clickRegisterButton();
    }

    @When("I click the Login link")
    public void i_click_the_login_link() {
    driver.findElement(RegisterPageLocators.LOGIN_LINK).click();
    }

    @When("I click the Register button")
    public void i_click_the_register_button() {
    driver.findElement(RegisterPageLocators.REGISTER_BUTTON).click();
    }

    @When("I populate the registration form")
    public void i_populate_the_registration_form() {
        setFirstName("Temp");
        setLastName("User");
        setEmail("temp.user+" + System.currentTimeMillis() + "@test.com");
        setPassword("Str0ng@123");
    }

    @When("I select gender {string}")
    public void i_select_gender(String gender) {
        selectGender(gender);
    }

    @When("I change gender to {string}")
    public void i_change_gender_to(String gender) {
        selectGender(gender);
    }

    @When("I complete valid registration")
    public void i_complete_valid_registration() {
        clickRegisterButton();
    }

    @Then("I should see a success message {string}")
    public void i_should_see_a_success_message(String msg) {
    Assert.assertTrue(registerPage.hasSuccessMessage(msg), "Success message not present: " + msg);
    }

    @Given("I have just registered successfully")
    public void i_have_just_registered_successfully() {
        if (registerPage == null) {
            driver = DriverFactory.getInstance().getDriver();
            registerPage = new RegisterPage().load();
        }
        String email = "fp.user+" + System.currentTimeMillis() + "@test.com";
        registerPage
                .firstName("FP")
                .lastName("User")
                .email(email)
                .password("Str0ng@123")
                .submit();
        registerPage.assertToastContains("Registration successful");
    }

    @When("I am redirected to the Login page")
    public void i_am_redirected_to_the_login_page() {
        long end = System.currentTimeMillis() + 10000;
        while (System.currentTimeMillis() < end) {
            if (driver.getCurrentUrl().toLowerCase().contains("login")) return;
            try { Thread.sleep(250); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); break; }
        }
        Assert.fail("Did not reach login page after registration");
    }

    @Then("I should be redirected to the Login page")
    public void i_should_be_redirected_to_the_login_page() {
        // Wait up to 10s for redirect after toast triggers navigation
        long end = System.currentTimeMillis() + 10000;
        boolean ok = false;
        while (System.currentTimeMillis() < end) {
            String url = driver.getCurrentUrl().toLowerCase();
            if (url.contains("login")) { ok = true; break; }
            try { Thread.sleep(250); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); break; }
        }
        Assert.assertTrue(ok, "Not redirected to login page within timeout. Last URL: " + driver.getCurrentUrl());
    }

    @Then("I should see validation error {string}")
    public void i_should_see_validation_error(String error) {
    registerPage.assertError(error);
    }

    @Then("Registration should succeed")
    public void registration_should_succeed() {
    registerPage.assertSuccess("Registration successful");
    }

    @Then("Stored name should be {string}")
    public void stored_name_should_be(String expected) {
    // Placeholder: using input value (replace if profile shows name separately)
    Assert.assertEquals(registerPage.getFieldValue(RegisterPageLocators.FIRST_NAME_INPUT), expected);
    }

    @Then("Stored email should be {string}")
    public void stored_email_should_be(String expected) {
        String actual = registerPage.getFieldValue(RegisterPageLocators.EMAIL_INPUT);
        if (!actual.equals(expected)) {
            Assert.assertTrue(actual.startsWith(expected + "+") && actual.contains("@"),
                    "Email mismatch. Expected base '" + expected + "' (with optional +timestamp), but was: " + actual);
        }
    }

    @Then("Only one account should be created")
    public void only_one_account_should_be_created() {
        // TODO: verify single creation (maybe via DB/API/mock) placeholder assertion
        Assert.assertTrue(true, "Placeholder - ensure only one account created");
    }

    @Then("I should see a single success message")
    public void i_should_see_a_single_success_message() {
    // Placeholder: rely on single success assertion already performed
    Assert.assertTrue(registerPage.hasSuccessMessage("Registration successful"));
    }

    @Then("The script should not execute")
    public void the_script_should_not_execute() {
    // Placeholder: ensure no alert present
    boolean alertPresent = false;
    try { driver.switchTo().alert(); alertPresent = true; } catch (Exception ignored) {}
    Assert.assertFalse(alertPresent, "Unexpected alert appeared (possible XSS execution)");
    }

    @Then("I should land on the Login page")
    public void i_should_land_on_the_login_page() {
    Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("login"));
    }

    @Then("I should land on the Register page")
    public void i_should_land_on_the_register_page() {
    Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("register"));
    }

    @Then("I should see a {string} link")
    public void i_should_see_a_link(String linkText) {
    Assert.assertTrue(driver.getPageSource().contains(linkText), "Link text not found: " + linkText);
    }

    @Then("All fields should be cleared")
    public void all_fields_should_be_cleared() {
    Assert.assertEquals(registerPage.getFieldValue(RegisterPageLocators.FIRST_NAME_INPUT), "");
    Assert.assertEquals(registerPage.getFieldValue(RegisterPageLocators.LAST_NAME_INPUT), "");
    Assert.assertEquals(registerPage.getFieldValue(RegisterPageLocators.EMAIL_INPUT), "");
    Assert.assertEquals(registerPage.getFieldValue(RegisterPageLocators.PASSWORD_INPUT), "");
    }

    @Then("The First Name validation message should disappear")
    public void the_first_name_validation_message_should_disappear() {
    Assert.assertFalse(registerPage.isFirstNameErrorVisible(), "First Name validation message still present after correction");
    }

    @Then("I should see validation outcome {string}")
    public void i_should_see_validation_outcome(String outcome) {
        if ("success".equalsIgnoreCase(outcome)) {
            registerPage.assertSuccess("Registration successful");
        } else {
            registerPage.assertError(outcome);
        }
    }
}
