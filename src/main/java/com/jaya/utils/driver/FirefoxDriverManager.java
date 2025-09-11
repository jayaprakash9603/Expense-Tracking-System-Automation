package com.jaya.utils.driver;

import com.jaya.factory.BrowserOptionsFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverManager implements DriverManager {
    @Override
    public WebDriver createDriver() {
        return new FirefoxDriver(BrowserOptionsFactory.getFirefoxOptions());
    }
}
