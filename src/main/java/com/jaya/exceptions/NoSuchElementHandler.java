package com.jaya.exceptions;



import com.jaya.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoSuchElementHandler extends ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(NoSuchElementHandler.class);

    @Override
    protected WebElement process(WebDriver driver, By locator, Exception exception) {
        if (exception instanceof NoSuchElementException) {
            logger.info("Handling NoSuchElementException for locator: {}", locator);
            // Try to wait for element to appear
            return WaitUtils.waitForElementPresent(driver, locator);
        }
        throw new RuntimeException(exception);
    }
}
