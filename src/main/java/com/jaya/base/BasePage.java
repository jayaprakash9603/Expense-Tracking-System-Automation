package com.jaya.base;


import com.jaya.exceptions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jaya.utils.UtilityManager;
import com.jaya.config.FrameworkConfig;
import com.jaya.factory.DriverFactory;
import com.jaya.utils.WaitUtils;

import java.util.List;
import java.util.function.Supplier;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final UtilityManager utils;
    private final ExceptionHandler exceptionHandler;
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverFactory.getInstance().getDriver();
        this.utils = new UtilityManager(driver);

        // Set up exception handling chain
        ExceptionHandler staleHandler = new StaleElementHandler();
        ExceptionHandler timeoutHandler = new TimeoutHandler();
        ExceptionHandler noSuchElementHandler = new NoSuchElementHandler();

        staleHandler.setNextHandler(timeoutHandler);
        timeoutHandler.setNextHandler(noSuchElementHandler);

        this.exceptionHandler = staleHandler;
    }

    // Core element interaction methods with retry mechanism
    protected void click(By locator) {
        executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementClickable(driver, locator);
            element.click();
            logger.info("Clicked element: {}", locator);
            return null;
        }, "click", locator);
    }

    protected void sendKeys(By locator, String text) {
        executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '{}' into element: {}", text, locator);
            return null;
        }, "sendKeys", locator);
    }

    protected void clearAndSendKeys(By locator, String text) {
        executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Cleared and entered text '{}' into element: {}", text, locator);
            return null;
        }, "clearAndSendKeys", locator);
    }

    protected WebElement findElement(By locator) {
        return executeWithRetry(() -> WaitUtils.waitForElementVisible(driver, locator), "findElement", locator);
    }

    protected List<WebElement> findElements(By locator) {
        return executeWithRetry(() -> WaitUtils.waitForElementsVisible(driver, locator), "findElements", locator);
    }

    protected String getText(By locator) {
        return executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            String text = element.getText();
            logger.info("Retrieved text '{}' from element: {}", text, locator);
            return text;
        }, "getText", locator);
    }

    protected String getAttribute(By locator, String attributeName) {
        return executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            String value = element.getAttribute(attributeName);
            logger.info("Retrieved attribute '{}' value '{}' from element: {}", attributeName, value, locator);
            return value;
        }, "getAttribute", locator);
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return executeWithRetry(() -> {
                WebElement element = WaitUtils.waitForElementVisible(driver, locator);
                boolean displayed = element.isDisplayed();
                logger.info("Element {} is displayed: {}", locator, displayed);
                return displayed;
            }, "isElementDisplayed", locator);
        } catch (Exception e) {
            logger.info("Element {} is not displayed", locator);
            return false;
        }
    }

    protected boolean isElementEnabled(By locator) {
        return executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            boolean enabled = element.isEnabled();
            logger.info("Element {} is enabled: {}", locator, enabled);
            return enabled;
        }, "isElementEnabled", locator);
    }

    protected boolean isElementSelected(By locator) {
        return executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            boolean selected = element.isSelected();
            logger.info("Element {} is selected: {}", locator, selected);
            return selected;
        }, "isElementSelected", locator);
    }

    // Advanced interaction methods
    protected void clickWithJS(By locator) {
        executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            utils.getJsUtils().clickWithJS(element);
            logger.info("Clicked element with JavaScript: {}", locator);
            return null;
        }, "clickWithJS", locator);
    }

    protected void scrollToElement(By locator) {
        executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            utils.getJsUtils().scrollIntoView(element);
            logger.info("Scrolled to element: {}", locator);
            return null;
        }, "scrollToElement", locator);
    }

    protected void hoverOverElement(By locator) {
        executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementVisible(driver, locator);
            utils.getActionUtils().hover(element);
            logger.info("Hovered over element: {}", locator);
            return null;
        }, "hoverOverElement", locator);
    }

    protected void doubleClick(By locator) {
        executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementClickable(driver, locator);
            utils.getActionUtils().doubleClick(element);
            logger.info("Double clicked element: {}", locator);
            return null;
        }, "doubleClick", locator);
    }

    protected void rightClick(By locator) {
        executeWithRetry(() -> {
            WebElement element = WaitUtils.waitForElementClickable(driver, locator);
            utils.getActionUtils().rightClick(element);
            logger.info("Right clicked element: {}", locator);
            return null;
        }, "rightClick", locator);
    }

    // Wait methods
    protected void waitForElementToBeVisible(By locator) {
        WaitUtils.waitForElementVisible(driver, locator);
    }

    protected void waitForElementToBeClickable(By locator) {
        WaitUtils.waitForElementClickable(driver, locator);
    }

    protected void waitForElementToBeInvisible(By locator) {
        WaitUtils.waitForElementInvisible(driver, locator);
    }

    protected void waitForTextToBePresentInElement(By locator, String text) {
        WaitUtils.waitForTextToBePresentInElement(driver, locator, text);
    }

    // Navigation methods
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    protected void refreshPage() {
        logger.info("Refreshing page");
        driver.navigate().refresh();
    }

    protected void goBack() {
        logger.info("Navigating back");
        driver.navigate().back();
    }

    protected void goForward() {
        logger.info("Navigating forward");
        driver.navigate().forward();
    }

    protected String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }

    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        return title;
    }

    // Utility access methods
    protected UtilityManager getUtils() {
        return utils;
    }

    // Screenshot method
    protected void takeScreenshot(String fileName) {
        try {
            utils.getScreenshotUtils().takeScreenshot(FrameworkConfig.SCREENSHOT_PATH + fileName + ".png");
            logger.info("Screenshot taken: {}", fileName);
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
        }
    }

    // Retry mechanism with exception handling
    private <T> T executeWithRetry(Supplier<T> action, String actionName, By locator) {
        int attempts = 0;
        Throwable lastException = null;

        while (attempts < FrameworkConfig.MAX_RETRY_ATTEMPTS) {
            try {
                return action.get();
            } catch (Exception e) {
                attempts++;
                lastException = e;
                logger.warn("Attempt {} failed for {} on {}: {}", attempts, actionName, locator, e.getMessage());

                if (attempts == FrameworkConfig.MAX_RETRY_ATTEMPTS) {
                    logger.error("Max retries reached for {} on {}", actionName, locator);
                    if (FrameworkConfig.TAKE_SCREENSHOT_ON_FAILURE) {
                        takeScreenshot("failure_" + actionName + "_" + System.currentTimeMillis());
                    }
                    throw new RuntimeException("Failed after " + FrameworkConfig.MAX_RETRY_ATTEMPTS + " attempts: " + actionName, lastException);
                }

                try {
                    Thread.sleep(FrameworkConfig.POLLING_INTERVAL.toMillis());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during retry", ie);
                }

                // Attempt recovery using exception handler
                try {
                    exceptionHandler.handle(driver, locator, (Exception) lastException);
                } catch (Exception ignored) {
                    // If recovery fails, continue with retry
                }
            }
        }
        return null;
    }

    @FunctionalInterface
    protected interface Supplier<T> {
        T get() throws Exception;
    }
}