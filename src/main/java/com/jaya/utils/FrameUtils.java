package com.jaya.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FrameUtils {
    private WebDriver driver;

    public FrameUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void switchToFrameByIndex(int index) {
        driver.switchTo().frame(index);
    }

    public void switchToFrameByName(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }
    public void switchToDefaultContent() { driver.switchTo().defaultContent(); }
    public void switchToFrameByElement(WebElement frameElement) {
        driver.switchTo().frame(frameElement);
    }

    public void switchToFrameByLocator(By locator) {
        driver.switchTo().frame(driver.findElement(locator));
    }

    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    public void switchToParent() {
        driver.switchTo().parentFrame();
    }
}
