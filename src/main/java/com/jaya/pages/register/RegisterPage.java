package com.jaya.pages.register;

import com.jaya.base.BasePage;
import com.jaya.pages.register.locators.RegisterPageLocators;
import com.jaya.utils.config.YamlConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.Assert;

import java.util.List;

public class RegisterPage extends BasePage {

    public RegisterPage load() { return load(null); }

    public RegisterPage load(String url) {
        String urlToUse = url;
        if (urlToUse == null || urlToUse.isBlank()) {
            try {
                urlToUse = YamlConfig.getRegisterUrl();
            } catch (Exception ignored) {}
        }
        if (urlToUse != null && !urlToUse.isBlank()) {
            navigateTo(urlToUse);
        }
        waitForElementToBeVisible(RegisterPageLocators.REGISTER_BUTTON);
        return this;
    }

    public boolean isAt() {
        return isElementDisplayed(RegisterPageLocators.REGISTER_BUTTON);
    }

    // Input helpers
    public RegisterPage firstName(String value) { sendKeys(RegisterPageLocators.FIRST_NAME_INPUT, value); return this; }
    public RegisterPage lastName(String value) { sendKeys(RegisterPageLocators.LAST_NAME_INPUT, value); return this; }
    public RegisterPage email(String value) { sendKeys(RegisterPageLocators.EMAIL_INPUT, value); return this; }
    public RegisterPage password(String value) { sendKeys(RegisterPageLocators.PASSWORD_INPUT, value); return this; }

    public RegisterPage gender(String gender) {
        if (gender == null || gender.isBlank()) return this; // optional now
        String g = gender.trim().toLowerCase();
        if (g.startsWith("f")) clickGender("female", RegisterPageLocators.GENDER_FEMALE_RADIO);
        else if (g.startsWith("m")) clickGender("male", RegisterPageLocators.GENDER_MALE_RADIO);
        return this;
    }

    private void clickGender(String visible, By inputLocator) {
        try {
            click(inputLocator);
            return;
        } catch (Exception ignored) { }
        // Try label
        try {
            click(RegisterPageLocators.genderLabel(capitalize(visible)));
            return;
        } catch (Exception ignored) { }
        // JS fallback if input is hidden
        try {
            WebElement el = driver.findElement(inputLocator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        } catch (Exception e) {
            throw new RuntimeException("Unable to select gender: " + visible, e);
        }
    }

    private String capitalize(String s) { return s.isEmpty()? s : Character.toUpperCase(s.charAt(0))+s.substring(1); }

    public RegisterPage togglePasswordVisibility() {
        click(RegisterPageLocators.PASSWORD_TOGGLE);
        return this;
    }

    // Password visibility helpers
    @SuppressWarnings("deprecation")
    public boolean isPasswordVisible(String expectedValue) {
        WebElement input = findElement(RegisterPageLocators.PASSWORD_INPUT);
        String type = input.getAttribute("type");
        if (!"text".equalsIgnoreCase(type)) return false;
        if (expectedValue == null) return true;
        String current = input.getAttribute("value");
        return expectedValue.equals(current);
    }

    public void assertPasswordVisible(String expectedValue) {
        Assert.assertTrue(isPasswordVisible(expectedValue),
                "Password not visible in plain text or value mismatch. Expected: " + expectedValue);
    }

    public RegisterPage submit() {
        click(RegisterPageLocators.REGISTER_BUTTON);
        return this;
    }

    public RegisterPage refresh() {
        refreshPage();
        waitForElementToBeVisible(RegisterPageLocators.REGISTER_BUTTON);
        return this;
    }

    public RegisterPage register(String first, String last, String email, String password, String gender) {
        return firstName(first).lastName(last).email(email).password(password).gender(gender).submit();
    }

    // Validation helpers
    public boolean isFirstNameErrorVisible() {
        return isElementDisplayed(RegisterPageLocators.FIRST_NAME_ERROR);
    }

    public RegisterPage clearFirstName() {
        sendKeys(RegisterPageLocators.FIRST_NAME_INPUT, "");
        return this;
    }

    public RegisterPage submitWithBlankFirstNameExpectingError() {
        clearFirstName();
        submit();
        return this;
    }

    public boolean hasSuccessMessage(String text) {
    return waitForToastMessage(text, 5);
    }

    public boolean hasError(String expected) {
        // Prefer top aggregated alert first
        if (isElementDisplayed(RegisterPageLocators.ERROR_ALERT)) {
            return getText(RegisterPageLocators.ERROR_ALERT).contains(expected);
        }
        List<WebElement> errors = findElements(RegisterPageLocators.ERROR_MESSAGES);
        return errors.stream().anyMatch(e -> e.getText().trim().contains(expected));
    }

    // ==== Toast helpers ====
    public boolean waitForToastVisible(long timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(RegisterPageLocators.TOAST_ALERT));
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean waitForToastMessage(String fragment, long timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(RegisterPageLocators.toastMessageContaining(fragment)));
            return true;
        } catch (Exception e) { return false; }
    }

    public String getToastText() {
        return getText(RegisterPageLocators.TOAST_ALERT);
    }

    public void assertToastContains(String fragment) {
        Assert.assertTrue(waitForToastMessage(fragment, 5), "Toast not found containing: " + fragment);
    }

    public void assertError(String expected) { Assert.assertTrue(hasError(expected), "Expected error not found: " + expected); }
    public void assertSuccess(String expected) { Assert.assertTrue(hasSuccessMessage(expected), "Success message not found: " + expected); }

    @SuppressWarnings("deprecation")
    public String getFieldValue(By locator) { return findElement(locator).getAttribute("value"); }

    public RegisterPage clearAll() {
        firstName(""); lastName(""); email(""); password("");
        return this;
    }
}
