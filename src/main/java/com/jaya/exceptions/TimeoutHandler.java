package com.jaya.exceptions;



import com.jaya.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeoutHandler extends ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(TimeoutHandler.class);

    @Override
    protected WebElement process(WebDriver driver, By locator, Exception exception) {
        if (exception instanceof TimeoutException) {
            logger.info("Handling TimeoutException for locator: {}", locator);
            // Try with increased wait time
            return WaitUtils.waitForElementVisible(driver, locator, 60);
        }
        throw new RuntimeException(exception);
    }
}