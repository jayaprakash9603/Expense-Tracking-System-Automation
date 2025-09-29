package com.jaya.pages.login;

import com.jaya.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage extends BasePage {



    // Page elements
    private final By usernameField = By.xpath("//input[@name='email']");
    private final By passwordField = By.xpath("//input[@name='password']");
    private final By loginButton = By.xpath("//div[contains(text(),'Logout')]");
    private final By confirmButton=By.xpath("//button[contains(text(),'Yes')]");
    private final By logoutButton = By.xpath("//button[text()='Login']");
    private final By errorMessage = By.className("error-message");
    private final By forgotPasswordLink = By.linkText("Forgot Password?");
    private final By invalidLoginTextField=By.xpath("//div[contains(text(),'User not found with email')]");
    private final By homeTextField=By.xpath("//div[contains(text(),'Home')]");

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

    public void clickLogoutButton() {
        click(logoutButton);
    }

    public void confirmLogout() {
        click(confirmButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        verifyHomePage();
    }

    public void logout(String username, String password) {

        clickLogoutButton();
        confirmLogout();
        verifyLoginPage();
    }

    public void verifyLoginPage()
    {
        Assert.assertEquals(findElement(loginButton).getText(),"Login");
    }

    public void verifyHomePage()
    {
        Assert.assertEquals(findElement(homeTextField).getText(),"Home");
    }
    public void invalidLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        verifyInvalidLoginText(invalidLoginTextField,username);
    }

    public void verifyInvalidLoginText(By invalidLoginTextField,String username)
    {
        Assert.assertEquals(findElement(invalidLoginTextField).getText(),"User not found with email: "+username);
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