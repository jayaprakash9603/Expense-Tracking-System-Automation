package com.jaya.utils;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class WindowUtils {
    private WebDriver driver;

    public WindowUtils(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    public Set<String> getAllWindowHandles() {
        return driver.getWindowHandles();
    }

    public void switchToWindow(String handle) {
        driver.switchTo().window(handle);
    }

    public String getWindowHandle() { return driver.getWindowHandle(); }
    public void switchToLastWindow() { for (String h : driver.getWindowHandles()) driver.switchTo().window(h); }
    public void closeWindow() { driver.close(); }
    public void switchToChildWindow() {
        String parent = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(parent)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public void switchBackToParent(String parentHandle) {
        driver.switchTo().window(parentHandle);
    }

    public void closeCurrentWindow() {
        driver.close();
    }
}
