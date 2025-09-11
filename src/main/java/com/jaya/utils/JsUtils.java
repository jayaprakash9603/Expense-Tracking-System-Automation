package com.jaya.utils;

import org.openqa.selenium.*;

public class JsUtils {
    private WebDriver driver;

    public JsUtils(WebDriver driver) { this.driver = driver; }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void clickWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public String getPageTitleWithJS() {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.title;");
    }
}
