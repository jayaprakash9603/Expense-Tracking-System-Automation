package com.jaya.tests;

import com.jaya.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;

    @BeforeSuite
    public void setUp() {
        DriverFactory.getInstance().initDriver();
        driver = DriverFactory.getInstance().getDriver();
        driver.get("https://jjayaprakash.netlify.app");
    }

    @AfterSuite
    public void tearDown() {
        DriverFactory.getInstance().quitDriver();
    }
}
