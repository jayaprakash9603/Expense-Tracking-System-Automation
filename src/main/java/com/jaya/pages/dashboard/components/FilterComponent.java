package com.jaya.pages.dashboard.components;

import com.jaya.base.BasePage;
import com.jaya.pages.dashboard.locators.DashboardPageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

/**
 * Clean FilterComponent implementation using centralized relative locators.
 */
public class FilterComponent extends BasePage {

    private final By containerLocator;

    public FilterComponent(By containerLocator) {
        super();
        this.containerLocator = containerLocator;
    }

    public WebElement getFilterContainer() { return findElement(containerLocator); }

    public FilterComponent selectTimeframe(String value) {
        WebElement c = getFilterContainer();
        List<WebElement> dropdowns = c.findElements(DashboardPageLocators.REL_TIMEFRAME_DROPDOWN);
        Assert.assertFalse(dropdowns.isEmpty(), "Timeframe dropdown missing");
        new Select(dropdowns.get(0)).selectByValue(value);
        verifyTimeframeSelected(value);
        return this;
    }

    public FilterComponent verifyTimeframeSelected(String expected) {
        WebElement c = getFilterContainer();
        List<WebElement> dropdowns = c.findElements(DashboardPageLocators.REL_TIMEFRAME_DROPDOWN);
        if (!dropdowns.isEmpty()) {
            String actual = new Select(dropdowns.get(0)).getFirstSelectedOption().getDomAttribute("value");
            Assert.assertEquals(actual, expected, "Timeframe selection mismatch");
        }
        return this;
    }

    public List<String> getAvailableTimeframes() {
        WebElement c = getFilterContainer();
        List<WebElement> dropdowns = c.findElements(DashboardPageLocators.REL_TIMEFRAME_DROPDOWN);
        if (!dropdowns.isEmpty()) {
            return new Select(dropdowns.get(0)).getOptions().stream().map(o -> o.getDomAttribute("value")).toList();
        }
        return List.of();
    }

    public FilterComponent verifyStandardTimeframeOptions() {
        List<String> opts = getAvailableTimeframes();
        Assert.assertTrue(opts.contains("this-month"));
        Assert.assertTrue(opts.contains("last-month"));
        Assert.assertTrue(opts.contains("last-3-months"));
        return this;
    }

    public FilterComponent clickLossToggle() { return clickToggle(DashboardPageLocators.REL_LOSS_TOGGLE, "loss"); }
    public FilterComponent clickGainToggle() { return clickToggle(DashboardPageLocators.REL_GAIN_TOGGLE, "gain"); }

    private FilterComponent clickToggle(By locator, String type) {
        WebElement c = getFilterContainer();
        List<WebElement> toggles = c.findElements(locator);
        Assert.assertFalse(toggles.isEmpty(), type + " toggle missing");
        toggles.get(0).click();
        verifyToggleActive(type);
        return this;
    }

    public FilterComponent verifyToggleActive(String type) {
        WebElement c = getFilterContainer();
        By locator = type.equalsIgnoreCase("loss") ? DashboardPageLocators.REL_LOSS_TOGGLE : DashboardPageLocators.REL_GAIN_TOGGLE;
        List<WebElement> buttons = c.findElements(locator);
        Assert.assertFalse(buttons.isEmpty(), type + " toggle not found");
        String cls = buttons.get(0).getDomAttribute("class");
        Assert.assertTrue(cls != null && cls.contains("active"), type + " toggle should be active");
        return this;
    }

    public FilterComponent verifyToggleInactive(String type) {
        WebElement c = getFilterContainer();
        String xpath = ".//button[contains(@data-testid,'" + type + "-toggle')]";
        List<WebElement> buttons = c.findElements(By.xpath(xpath));
        Assert.assertFalse(buttons.isEmpty(), type + " toggle not found");
        String cls = buttons.get(0).getDomAttribute("class");
        Assert.assertFalse(cls != null && cls.contains("active"), type + " toggle should be inactive");
        return this;
    }

    public FilterComponent verifyToggleButtonsPresent() {
        WebElement c = getFilterContainer();
        Assert.assertFalse(c.findElements(DashboardPageLocators.REL_LOSS_TOGGLE).isEmpty(), "Loss toggle missing");
        Assert.assertFalse(c.findElements(DashboardPageLocators.REL_GAIN_TOGGLE).isEmpty(), "Gain toggle missing");
        return this;
    }

    public FilterComponent verifyToggleBehavior() {
        clickLossToggle();
        verifyToggleActive("loss").verifyToggleInactive("gain");
        clickGainToggle();
        verifyToggleActive("gain").verifyToggleInactive("loss");
        return this;
    }

    public String getCurrentToggleState() {
        WebElement c = getFilterContainer();
        List<WebElement> active = c.findElements(DashboardPageLocators.REL_ACTIVE_TOGGLE);
        if (!active.isEmpty()) {
            String id = active.get(0).getDomAttribute("data-testid");
            if (id != null) {
                if (id.contains("loss")) return "loss";
                if (id.contains("gain")) return "gain";
            }
        }
        return "none";
    }

    public FilterComponent resetToDefaults() {
        selectTimeframe("this-month");
        clickLossToggle();
        return this;
    }

    public FilterComponent waitForFilterUpdate() {
        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public FilterComponent validateCompleteFilter() {
        verifyStandardTimeframeOptions();
        verifyToggleButtonsPresent();
        verifyToggleBehavior();
        return this;
    }
}
