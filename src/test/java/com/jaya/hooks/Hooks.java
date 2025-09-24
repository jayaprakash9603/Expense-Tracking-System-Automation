package com.jaya.hooks;



import com.jaya.factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.getInstance().getDriver();
    }

    @After
    public void tearDown() {
        DriverFactory.getInstance().quitDriver();
    }
}
