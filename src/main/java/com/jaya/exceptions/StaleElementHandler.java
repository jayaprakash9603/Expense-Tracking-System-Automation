package com.jaya.exceptions;


import com.jaya.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaleElementHandler extends ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(StaleElementHandler.class);

    @Override
    protected WebElement process(WebDriver driver, By locator, Exception exception) {
        if (exception instanceof StaleElementReferenceException) {
            logger.info("Handling StaleElementReferenceException for locator: {}", locator);
            return WaitUtils.waitForElementVisible(driver, locator);
        }
        throw new RuntimeException(exception);
    }
}
