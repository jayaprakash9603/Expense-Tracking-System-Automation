package com.jaya.listeners;


import com.jaya.config.FrameworkConfig;
import com.jaya.factory.DriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getMethod().getMethodName());
        logger.error("Failure reason: {}", result.getThrowable().getMessage());

        if (FrameworkConfig.TAKE_SCREENSHOT_ON_FAILURE) {
            try {
                String screenshotName = "failure_" + result.getMethod().getMethodName() + "_" + System.currentTimeMillis();
                DriverFactory.getInstance().getDriver().manage().window().maximize();
                // Take screenshot logic here
                logger.info("Screenshot taken for failed test: {}", screenshotName);
            } catch (Exception e) {
                logger.error("Failed to take screenshot: {}", e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: {}", result.getMethod().getMethodName());
    }
}