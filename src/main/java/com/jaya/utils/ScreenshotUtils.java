package com.jaya.utils;

import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScreenshotUtils {
    private WebDriver driver;

    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
    }

    public File takeScreenshot(String filePath) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(filePath);
        Files.copy(src.toPath(), dest.toPath());
        return dest;
    }

    public byte[] getScreenshotAsBytes() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public String getScreenshotAsBase64() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
