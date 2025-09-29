package com.jaya.pages.login;

import com.jaya.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

/**
 * Page Object representing the Login page.
 *
 * Responsibilities:
 *  - Encapsulate locators for login related elements
 *  - Provide clear, intention-revealing interaction methods (enterUsername, enterPassword, submit/login)
 *  - Offer convenience / fluent APIs for composing actions (loginAs, typeUsername...)
 *  - Expose lightweight state queries (isAt, hasError, getAlertMessage)
 *  - Keep assertions optional (assert* methods) so tests can decide where to assert
 *
 * Backward compatibility: Existing public methods (login, invalidLogin, verifyLoginPage, verifyHomePage, etc.)
 * are retained; some are marked @Deprecated in favor of newer naming patterns.
 */
public class LoginPage extends BasePage {

    /* ========================= Locators ========================= */
    // Form fields
    private final By usernameField = By.xpath("//input[@name='email']");
    private final By passwordField = By.xpath("//input[@name='password']");

    // Buttons / actionable elements
    private final By loginButton   = By.xpath("//button[text()='Login']");
    private final By logoutButton  = By.xpath("//div[contains(text(),'Logout')]");
    private final By confirmButton = By.xpath("//button[contains(text(),'Yes')]");
    private final By forgotPasswordLink = By.linkText("Forgot Password?");

    // Feedback / status elements
    private final By errorMessage  = By.className("error-message");
    private final By homeTextField = By.xpath("//div[contains(text(),'Home')]");

    // Dynamic alert / error message container (Material UI style alert). Use helper to build locator.
    private static final String ALERT_MESSAGE_XPATH_TEMPLATE =
            "//div[@role='alert']//div[contains(@class,'MuiAlert-message') and contains(normalize-space(.),'%s')]";

    /* ========================= Page Lifecycle ========================= */

    /**
     * Optionally navigate to the provided login URL and wait until page is ready.
     * @param loginUrl absolute or relative URL to login page.
     * @return this page instance for chaining
     */
    public LoginPage load(String loginUrl) {
        if (loginUrl != null && !loginUrl.isBlank()) {
            navigateTo(loginUrl);
        }
        waitUntilLoaded();
        return this;
    }

    /** Waits for a core element to ensure the page loaded. */
    private void waitUntilLoaded() {
        waitForElementToBeVisible(loginButton);
    }

    /** @return true if we're on the login page (button visible). */
    public boolean isAt() {
        return isElementDisplayed(loginButton);
    }

    /* ========================= Low-level Actions ========================= */
    public void enterUsername(String username) { sendKeys(usernameField, username); }
    public void enterPassword(String password) { sendKeys(passwordField, password); }
    public void clickLoginButton() { click(loginButton); }
    public void clickLogoutButton() { click(logoutButton); }
    public void confirmLogout() { click(confirmButton); }
    public void clickForgotPassword() { click(forgotPasswordLink); }

    /* ========================= Fluent API ========================= */
    public LoginPage typeUsername(String username) { enterUsername(username); return this; }
    public LoginPage typePassword(String password) { enterPassword(password); return this; }
    public LoginPage submit() { clickLoginButton(); return this; }

    /** Convenience method combining typing and submit (no post-condition checks). */
    public LoginPage loginAs(String username, String password) {
        return typeUsername(username).typePassword(password).submit();
    }

    /** Legacy naming kept for backward compatibility; prefer {@link #loginAs(String, String)}. */
    public void login(String username, String password) {
        loginAs(username, password);
        verifyHomePage(); // preserve existing behavior (assert)
    }

    public void invalidLogin(String username, String password) {
        loginAs(username, password); // no success assertion
    }

    public void logout(String username, String password) { // params not used but retained for compatibility
        clickLogoutButton();
        confirmLogout();
        verifyLoginPage();
    }

    /* ========================= Assertions / Legacy Methods ========================= */
    /** Prefer using isAt() + external assertion in test code. */
    @Deprecated
    public void verifyLoginPage() {
        Assert.assertEquals(findElement(loginButton).getText(), "Login", "Not on Login page.");
    }

    /** Prefer a dedicated HomePage object with isAt() (future enhancement). */
    @Deprecated
    public void verifyHomePage() {
        Assert.assertEquals(findElement(homeTextField).getText(), "Home", "Home page text mismatch.");
    }

    /* ========================= Alerts & Errors ========================= */
    // Builds a By for an alert message that contains the provided text fragment
    private By alertMessageBy(String textFragment) {
        return By.xpath(String.format(ALERT_MESSAGE_XPATH_TEMPLATE, textFragment));
    }

    /** Waits for an alert message containing the expected text and asserts full equality. */
    public void assertInvalidLoginMessage(String expectedMessage) {
        String actual = waitForAlertContaining(expectedMessage).getText().trim();
        Assert.assertEquals(actual, expectedMessage, "Invalid login message mismatch. Actual: " + actual);
    }

    /** Asserts that any visible alert message contains the fragment provided. */
    public void assertLoginMessageContains(String fragment) {
        WebElement msg = waitForAlertContaining(fragment);
        Assert.assertTrue(msg.getText().contains(fragment),
                "Alert message does not contain expected fragment: " + fragment + " | Actual: " + msg.getText());
    }

    /** Returns the text of the first alert message that matches the fragment. */
    public String getAlertMessage(String fragment) {
        return waitForAlertContaining(fragment).getText().trim();
    }

    /** True if an alert containing the fragment appears within timeout. */
    public boolean hasAlertContaining(String fragment) {
        try {
            getAlertMessage(fragment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private WebElement waitForAlertContaining(String fragment) {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(alertMessageBy(fragment)));
    }


}