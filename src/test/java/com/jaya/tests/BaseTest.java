package com.jaya.tests;

import com.jaya.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.getInstance().initDriver();
        driver = DriverFactory.getInstance().getDriver();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.getInstance().quitDriver();
    }
}
