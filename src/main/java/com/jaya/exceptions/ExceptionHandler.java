package com.jaya.exceptions;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    private ExceptionHandler nextHandler;

    public void setNextHandler(ExceptionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public WebElement handle(WebDriver driver, By locator, Exception exception) {
        try {
            return process(driver, locator, exception);
        } catch (Exception e) {
            if (nextHandler != null) {
                return nextHandler.handle(driver, locator, e);
            }
            logger.error("Unhandled exception for locator {}: {}", locator, e.getMessage());
            throw new RuntimeException("Failed to handle exception for locator: " + locator, e);
        }
    }

    protected abstract WebElement process(WebDriver driver, By locator, Exception exception);
}