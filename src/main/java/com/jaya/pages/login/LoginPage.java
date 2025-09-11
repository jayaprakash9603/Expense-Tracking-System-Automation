package com.jaya.pages.login;

import com.jaya.base.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // Page elements
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.className("error-message");
    private final By forgotPasswordLink = By.linkText("Forgot Password?");

    // Page actions
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }

    // Business logic methods
    public boolean isLoginSuccessful() {
        // Wait for redirect or success indicator
        try {
            waitForElementToBeInvisible(loginButton);
            return !getCurrentUrl().contains("login");
        } catch (Exception e) {
            return false;
        }
    }

    public void loginWithValidCredentials() {
        login("validuser@example.com", "validpassword");
    }

    public void loginWithInvalidCredentials() {
        login("invalid@example.com", "wrongpassword");
    }

    // Utility methods specific to this page
    public void takeLoginPageScreenshot() {
        takeScreenshot("login_page_" + System.currentTimeMillis());
    }

    public void clearLoginForm() {
        clearAndSendKeys(usernameField, "");
        clearAndSendKeys(passwordField, "");
    }
}