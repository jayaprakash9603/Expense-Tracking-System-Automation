package com.jaya.pages.login.locators;

import org.openqa.selenium.By;

/**
 * Centralized locators for the Login page.
 * Keep this class dumb (constants only) to allow reuse across Page Objects, step defs, or components.
 */
public final class LoginPageLocators {
    private LoginPageLocators() {}

    public static final By USERNAME_FIELD = By.xpath("//input[@name='email']");
    public static final By PASSWORD_FIELD = By.xpath("//input[@name='password']");
    public static final By LOGIN_BUTTON   = By.xpath("//button[text()='Login']");
    public static final By LOGOUT_BUTTON  = By.xpath("//div[contains(text(),'Logout')]");
    public static final By CONFIRM_BUTTON = By.xpath("//button[contains(text(),'Yes')]");
    public static final By FORGOT_PASSWORD_LINK = By.linkText("Forgot Password?");
    public static final By ERROR_MESSAGE  = By.className("error-message");
    public static final By HOME_TEXT      = By.xpath("//div[contains(text(),'Home')]");

    // Dynamic alert / error message container (Material UI style alert)
    public static final String ALERT_MESSAGE_XPATH_TEMPLATE =
            "//div[@role='alert']//div[contains(@class,'MuiAlert-message') and contains(normalize-space(.),'%s')]";

    public static By alertMessageContaining(String fragment) {
        return By.xpath(String.format(ALERT_MESSAGE_XPATH_TEMPLATE, fragment));
    }
}
