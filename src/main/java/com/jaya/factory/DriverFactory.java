package com.jaya.factory;

import com.jaya.utils.config.ConfigReader;
import com.jaya.utils.driver.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static DriverFactory instance; // singleton instance
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final Map<String, DriverManager> managers = new HashMap<>();

    private DriverFactory() {
        managers.put("chrome", new ChromeDriverManager());
        managers.put("firefox", new FirefoxDriverManager());
        managers.put("edge", new EdgeDriverManager());
    }

    // Singleton accessor
    public static DriverFactory getInstance() {
        if (instance == null) {
            synchronized (DriverFactory.class) {
                if (instance == null) {
                    instance = new DriverFactory();
                }
            }
        }
        return instance;
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean gridEnabled = ConfigReader.getBoolean("grid.enabled");

        WebDriver webDriver;
        if (gridEnabled) {
            try {
                Capabilities capabilities = (Capabilities) getCapabilities(browser);
                webDriver = new RemoteWebDriver(new URL(ConfigReader.get("grid.url")), capabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Grid URL", e);
            }
        } else {
            DriverManager manager = managers.get(browser);
            if (manager == null) throw new IllegalArgumentException("Unsupported browser: " + browser);
            webDriver = manager.createDriver();
        }

        driver.set(webDriver);
        configureDriver(webDriver);
    }

    private void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
    }

    private Object getCapabilities(String browser) {
        return switch (browser) {
            case "chrome" -> BrowserOptionsFactory.getChromeOptions();
            case "firefox" -> BrowserOptionsFactory.getFirefoxOptions();
            case "edge" -> BrowserOptionsFactory.getEdgeOptions();
            default -> throw new IllegalArgumentException("Unsupported browser for Grid: " + browser);
        };
    }

    public void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
