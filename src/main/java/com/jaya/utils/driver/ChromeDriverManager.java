package com.jaya.utils.driver;

import com.jaya.factory.BrowserOptionsFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager implements DriverManager {
    @Override
    public WebDriver createDriver() {
        return new ChromeDriver(BrowserOptionsFactory.getChromeOptions());
    }
}
