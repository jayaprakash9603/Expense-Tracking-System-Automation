package com.jaya.factory;

import com.jaya.utils.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserOptionsFactory {

    private static boolean isHeadless() {
        return ConfigReader.getBoolean("headless");
    }

    private static boolean isMaximized() {
        return ConfigReader.getBoolean("maximize");
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        if (isHeadless()) options.addArguments("--headless=new");
        if (isMaximized()) options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars", "--disable-notifications", "--remote-allow-origins=*");
        return options;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless()) options.addArguments("--headless");
        if (isMaximized()) {
            options.addArguments("--width=1920", "--height=1080");
        }
        return options;
    }

    public static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        if (isHeadless()) options.addArguments("--headless=new");
        if (isMaximized()) options.addArguments("--start-maximized");
        return options;
    }
}
