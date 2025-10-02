package com.jaya.pages.login;

import com.jaya.base.BasePage;
import com.jaya.pages.login.locators.LoginPageLocators;
import com.jaya.utils.config.YamlConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends BasePage {

    public LoginPage load() { return load(null); }

    public LoginPage load(String loginUrl) {
        String urlToUse = loginUrl;
        if (urlToUse == null || urlToUse.isBlank()) {
            try {
                urlToUse = YamlConfig.getLoginUrl();
            } catch (Exception e) {
                // Fallback: if YAML missing, keep urlToUse null so caller can still navigate later
            }
        }
        if (urlToUse != null && !urlToUse.isBlank()) {
            navigateTo(urlToUse);
        }
        waitUntilLoaded();
        return this;
    }

    private void waitUntilLoaded() {
        waitForElementToBeVisible(LoginPageLocators.LOGIN_BUTTON);
    }

    public boolean isAt() {
        return isElementDisplayed(LoginPageLocators.LOGIN_BUTTON);
    }

    public void enterUsername(String username) { sendKeys(LoginPageLocators.USERNAME_FIELD, username); }
    public void enterPassword(String password) { sendKeys(LoginPageLocators.PASSWORD_FIELD, password); }
    public void clickLoginButton() { click(LoginPageLocators.LOGIN_BUTTON); }
    public void clickLogoutButton() { click(LoginPageLocators.LOGOUT_BUTTON); }
    public void confirmLogout() { click(LoginPageLocators.CONFIRM_BUTTON); }
    public void clickForgotPassword() { click(LoginPageLocators.FORGOT_PASSWORD_LINK); }

    public LoginPage typeUsername(String username) { enterUsername(username); return this; }
    public LoginPage typePassword(String password) { enterPassword(password); return this; }
    public LoginPage submit() { clickLoginButton(); return this; }

    public LoginPage loginAs(String username, String password) {
        return typeUsername(username).typePassword(password).submit();
    }

    public void login(String username, String password) {
        loginAs(username, password);
        verifyHomePage();
    }

    public void invalidLogin(String username, String password) {
        loginAs(username, password);
    }

    public void logout(String username, String password) {
        clickLogoutButton();
        confirmLogout();
        verifyLoginPage();
    }

    @Deprecated
    public void verifyLoginPage() {
        Assert.assertEquals(findElement(LoginPageLocators.LOGIN_BUTTON).getText(), "Login", "Not on Login page.");
    }

    @Deprecated
    public void verifyHomePage() {
        Assert.assertEquals(findElement(LoginPageLocators.HOME_TEXT).getText(), "Home", "Home page text mismatch.");
    }

    public void assertInvalidLoginMessage(String expectedMessage) {
        String actual = waitForAlertContaining(expectedMessage).getText().trim();
        Assert.assertEquals(actual, expectedMessage, "Invalid login message mismatch. Actual: " + actual);
    }

    public void assertLoginMessageContains(String fragment) {
        WebElement msg = waitForAlertContaining(fragment);
        Assert.assertTrue(msg.getText().contains(fragment),
                "Alert message does not contain expected fragment: " + fragment + " | Actual: " + msg.getText());
    }

    public String getAlertMessage(String fragment) {
        return waitForAlertContaining(fragment).getText().trim();
    }

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
                .until(ExpectedConditions.visibilityOfElementLocated(LoginPageLocators.alertMessageContaining(fragment)));
    }
}