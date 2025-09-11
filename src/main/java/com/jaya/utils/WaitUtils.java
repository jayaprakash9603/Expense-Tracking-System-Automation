package com.jaya.utils;


import com.jaya.config.FrameworkConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class WaitUtils {
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);

    public WaitUtils(WebDriver driver) {
    }

    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        return waitForElementVisible(driver, locator, FrameworkConfig.WAIT_TIMEOUT.getSeconds());
    }

    public static WebElement waitForElementVisible(WebDriver driver, By locator, long timeoutInSeconds) {
        logger.info("Waiting for element to be visible: {}", locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds), FrameworkConfig.POLLING_INTERVAL);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        return waitForElementClickable(driver, locator, FrameworkConfig.WAIT_TIMEOUT.getSeconds());
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator, long timeoutInSeconds) {
        logger.info("Waiting for element to be clickable: {}", locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds), FrameworkConfig.POLLING_INTERVAL);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementPresent(WebDriver driver, By locator) {
        logger.info("Waiting for element to be present: {}", locator);
        WebDriverWait wait = new WebDriverWait(driver, FrameworkConfig.WAIT_TIMEOUT, FrameworkConfig.POLLING_INTERVAL);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static List<WebElement> waitForElementsVisible(WebDriver driver, By locator) {
        logger.info("Waiting for elements to be visible: {}", locator);
        WebDriverWait wait = new WebDriverWait(driver, FrameworkConfig.WAIT_TIMEOUT, FrameworkConfig.POLLING_INTERVAL);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static boolean waitForElementInvisible(WebDriver driver, By locator) {
        logger.info("Waiting for element to be invisible: {}", locator);
        WebDriverWait wait = new WebDriverWait(driver, FrameworkConfig.WAIT_TIMEOUT, FrameworkConfig.POLLING_INTERVAL);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForTextToBePresentInElement(WebDriver driver, By locator, String text) {
        logger.info("Waiting for text '{}' to be present in element: {}", text, locator);
        WebDriverWait wait = new WebDriverWait(driver, FrameworkConfig.WAIT_TIMEOUT, FrameworkConfig.POLLING_INTERVAL);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
}