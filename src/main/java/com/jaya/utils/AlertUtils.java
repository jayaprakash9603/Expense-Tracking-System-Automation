package com.jaya.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public class AlertUtils {
    private WebDriver driver;

    public AlertUtils(WebDriver driver) {
        this.driver = driver;
    }

    public Alert switchToAlert() {
        return driver.switchTo().alert();
    }

    public String getAlertText() {
        return switchToAlert().getText();
    }

    public void acceptAlert() {
        switchToAlert().accept();
    }

    public void dismissAlert() {
        switchToAlert().dismiss();
    }

    public void sendTextToAlert(String text) {
        switchToAlert().sendKeys(text);
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
