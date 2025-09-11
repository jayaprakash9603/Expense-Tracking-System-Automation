package com.jaya.utils;

import org.openqa.selenium.WebDriver;

public class NavigationUtils {
    private WebDriver driver;

    public NavigationUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void goBack() {
        driver.navigate().back();
    }

    public void goForward() {
        driver.navigate().forward();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }
}
