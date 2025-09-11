package com.jaya.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class ActionUtils {
    private WebDriver driver;
    private Actions actions;

    public ActionUtils(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public void hover(WebElement element) { actions.moveToElement(element).perform(); }

    public void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }

    public void rightClick(WebElement element) { actions.contextClick(element).perform(); }

    public void doubleClick(WebElement element) { actions.doubleClick(element).perform(); }

    public void sendKeys(WebElement element, String keys) {
        actions.moveToElement(element).sendKeys(keys).perform();
    }

    public void clickAndHold(WebElement element) { new Actions(driver).clickAndHold(element).perform(); }
    public void release(WebElement element) { new Actions(driver).release(element).perform(); }
    public void sendKeysAction(CharSequence keys) { new Actions(driver).sendKeys(keys).perform(); }
    public void moveByOffset(int x, int y) { new Actions(driver).moveByOffset(x, y).perform(); }
    public void pressEnter(WebElement element) { element.sendKeys(Keys.ENTER); }
    public void pressTab(WebElement element) { element.sendKeys(Keys.TAB); }
    public void pressEscape(WebElement element) { element.sendKeys(Keys.ESCAPE); }
    public void pressCtrlA(WebElement element) { element.sendKeys(Keys.CONTROL, "a"); }
    public void pressCtrlC(WebElement element) { element.sendKeys(Keys.CONTROL, "c"); }
    public void pressCtrlV(WebElement element) { element.sendKeys(Keys.CONTROL, "v"); }

}
