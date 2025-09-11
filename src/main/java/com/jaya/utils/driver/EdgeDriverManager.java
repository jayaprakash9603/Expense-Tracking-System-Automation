package com.jaya.utils.driver;

import com.jaya.factory.BrowserOptionsFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverManager implements DriverManager {
    @Override
    public WebDriver createDriver() {
        return new EdgeDriver(BrowserOptionsFactory.getEdgeOptions());
    }
}
