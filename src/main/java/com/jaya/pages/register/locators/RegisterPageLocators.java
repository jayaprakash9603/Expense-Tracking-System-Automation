package com.jaya.pages.register.locators;

import org.openqa.selenium.By;

/** Locator constants for the Register page. */
public final class RegisterPageLocators {
    private RegisterPageLocators() {}


    public static final By FIRST_NAME_INPUT = By.xpath("//input[@name='firstName' or @placeholder='First Name']");
    public static final By FIRST_NAME_ERROR = By.xpath("//*[(@role='alert' or contains(@class,'error')) and contains(.,'First Name')]");
    public static final By LAST_NAME_INPUT  = By.xpath("//input[@name='lastName' or @placeholder='Last Name']");
    public static final By EMAIL_INPUT      = By.xpath("//input[@name='email' or @placeholder='Email']");
    public static final By PASSWORD_INPUT   = By.xpath("//input[@name='password' or @placeholder='Password']");
    public static final By GENDER_FEMALE_RADIO = By.xpath("//input[@name='gender' and @value='female']");
    public static final By GENDER_MALE_RADIO   = By.xpath("//input[@name='gender' and @value='male']");


    public static By genderLabel(String visibleText) {
        return By.xpath("//label[.//span[normalize-space()='" + visibleText + "']]");
    }

    public static final By REGISTER_BUTTON = By.xpath("//button[normalize-space()='Register']");
    public static final By LOGIN_LINK      = By.xpath("//button[normalize-space()='Login']");
    public static final By FORGOT_PASSWORD_LINK = By.xpath("//a[contains(normalize-space(),'Forgot Password')]");
    // Password toggle button (supports either icon present)
    public static final By PASSWORD_TOGGLE = By.xpath("//button[.//svg[@data-testid='VisibilityIcon' or @data-testid='VisibilityOffIcon']]");
    // Specific states (if you need to assert icon change)
    public static final By PASSWORD_TOGGLE_VISIBLE_ICON = By.xpath("//button[.//svg[@data-testid='VisibilityIcon']]");
    public static final By PASSWORD_TOGGLE_HIDDEN_ICON  = By.xpath("//button[.//svg[@data-testid='VisibilityOffIcon']]");
    // Relative to password input (first following toggle)
    public static final By PASSWORD_TOGGLE_RELATIVE = By.xpath("//input[@name='password' or @placeholder='Password']/following::button[.//svg[@data-testid='VisibilityIcon' or @data-testid='VisibilityOffIcon']][1]");


    public static final By SUCCESS_MESSAGE = By.xpath("//div[@role='alert' and contains(.,'Registration successful')]");
    public static final By ERROR_ALERT = By.xpath("//div[@role='alert' and (contains(@class,'Error') or contains(@class,'error') or contains(.,'required'))]");
    public static final By ERROR_MESSAGES  = By.xpath("//*[(@role='alert' and not(contains(.,'Registration successful'))) or contains(@class,'error') or contains(@class,'invalid')]");

    public static final By TOAST_ALERT = By.xpath("//div[contains(@class,'MuiSnackbar-root')]//div[@role='alert']");
    public static By toastMessageContaining(String fragment) {
        return By.xpath("//div[contains(@class,'MuiSnackbar-root')]//div[@role='alert'][contains(.,'" + fragment + "')]");
    }

    // Dynamic message helpers
    public static By errorMessageText(String text) {
        return By.xpath("//*[contains(@class,'error') or @role='alert'][contains(normalize-space(), '" + text + "')]");
    }

    public static By validationMessage(String fieldLabel) {
        return By.xpath("//*[contains(normalize-space(), '" + fieldLabel + "') and (contains(@class,'error') or @role='alert')]");
    }
}
