package com.jaya.listeners;

import com.jaya.config.FrameworkConfig;
import com.jaya.factory.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestFailureListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestFailureListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName());
        WebDriver driver = DriverFactory.getInstance().getDriver();
        if (driver instanceof TakesScreenshot) {
            try {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String screenshotName = FrameworkConfig.SCREENSHOT_PATH + result.getName() + "_" + timestamp + ".png";
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File(screenshotName));
                logger.info("Screenshot saved: {}", screenshotName);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot: {}", e.getMessage());
            }
        }
    }
}