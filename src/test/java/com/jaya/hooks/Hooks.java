package com.jaya.hooks;



import com.jaya.factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.WebDriver;

public class Hooks {

    static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        if (DriverFactory.getInstance().getDriver() == null) {
            DriverFactory.getInstance().initDriver();
        }
        driver = DriverFactory.getInstance().getDriver();
    }

    @AfterAll
    public static  void tearDown() {
        DriverFactory.getInstance().quitDriver();
    }
}
