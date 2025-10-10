package com.jaya.pages.dashboard.components;

import com.jaya.base.BasePage;
import com.jaya.pages.dashboard.locators.DashboardPageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * Reusable component for metric cards in the dashboard
 * Provides common functionality for financial overview cards like Total Expenses, Credit Due, etc.
 * Implements DRY principle by centralizing metric card interactions and validations
 */
public class MetricCard extends BasePage {
    
    private final By cardLocator;
    private final String expectedTitle;
    
    public MetricCard(By cardLocator, String expectedTitle) {
        super();
        this.cardLocator = cardLocator;
        this.expectedTitle = expectedTitle;
    }
    
    /**
     * Get the metric card element
     */
    public WebElement getCardElement() {
        return findElement(cardLocator);
    }
    
    /**
     * Verify the metric card is displayed
     */
    public MetricCard verifyCardIsDisplayed() {
        Assert.assertTrue(isElementDisplayed(cardLocator), 
            "Metric card '" + expectedTitle + "' is not displayed");
        return this;
    }
    
    /**
     * Get the title of the metric card
     */
    public String getTitle() {
        WebElement card = getCardElement();
    WebElement titleElement = card.findElement(DashboardPageLocators.METRIC_CARD_TITLE);
    return titleElement.getText().trim();
    }
    
    /**
     * Verify the metric card has the expected title
     */
    public MetricCard verifyTitle() {
        String actualTitle = getTitle();
        Assert.assertTrue(actualTitle.contains(expectedTitle), 
            "Expected title '" + expectedTitle + "' but found '" + actualTitle + "'");
        return this;
    }
    
    /**
     * Get the current value/amount displayed in the metric card
     */
    public String getValue() {
        WebElement card = getCardElement();
    WebElement valueElement = card.findElement(DashboardPageLocators.METRIC_CARD_VALUE);
    return valueElement.getText().trim();
    }
    
    /**
     * Verify the metric card has a value (not empty or null)
     */
    public MetricCard verifyHasValue() {
        String value = getValue();
        Assert.assertNotNull(value, "Metric card value should not be null");
        Assert.assertFalse(value.trim().isEmpty(), "Metric card value should not be empty");
        return this;
    }
    
    /**
     * Verify the value is in currency format (contains ₹ symbol)
     */
    public MetricCard verifyCurrencyFormat() {
        String value = getValue();
        Assert.assertTrue(value.contains("₹"), 
            "Value '" + value + "' should be in currency format with ₹ symbol");
        return this;
    }
    
    /**
     * Get the trend indicator (up/down/flat)
     */
    public String getTrendIndicator() {
        WebElement card = getCardElement();
    List<WebElement> trendElements = card.findElements(DashboardPageLocators.METRIC_CARD_TREND);
        if (trendElements.isEmpty()) {
            return "no-trend";
        }
        WebElement trendElement = trendElements.get(0);
        String classAttribute = trendElement.getDomAttribute("class");
        
        if (classAttribute.contains("up") || classAttribute.contains("increase")) {
            return "up";
        } else if (classAttribute.contains("down") || classAttribute.contains("decrease")) {
            return "down";
        } else {
            return "flat";
        }
    }
    
    /**
     * Verify the metric card has a trend indicator
     */
    public MetricCard verifyHasTrendIndicator() {
        String trend = getTrendIndicator();
        Assert.assertNotEquals(trend, "no-trend", "Metric card should have a trend indicator");
        return this;
    }
    
    /**
     * Get the percentage change value
     */
    public String getPercentageChange() {
        WebElement card = getCardElement();
    List<WebElement> percentageElements = card.findElements(DashboardPageLocators.METRIC_CARD_PERCENTAGE);
        if (percentageElements.isEmpty()) {
            return "0%";
        }
        return percentageElements.get(0).getText();
    }
    
    /**
     * Verify the metric card has percentage change information
     */
    public MetricCard verifyHasPercentageChange() {
        String percentage = getPercentageChange();
        Assert.assertTrue(percentage.contains("%"), 
            "Percentage change '" + percentage + "' should contain % symbol");
        return this;
    }
    
    /**
     * Verify the metric card has all required elements
     */
    public MetricCard verifyCompleteCard() {
        verifyCardIsDisplayed()
            .verifyTitle()
            .verifyHasValue()
            .verifyHasTrendIndicator()
            .verifyHasPercentageChange();
        return this;
    }
    
    /**
     * Click on the metric card (if clickable)
     */
    public MetricCard clickCard() {
        click(cardLocator);
        return this;
    }
    
    /**
     * Hover over the metric card
     */
    public MetricCard hoverOverCard() {
        hoverOverElement(cardLocator);
        return this;
    }
    
    /**
     * Wait for the metric card to load (useful for loading states)
     */
    public MetricCard waitForCardToLoad() {
        waitForElementToBeVisible(cardLocator);
        return this;
    }
    
    /**
     * Verify the card is not in loading state
     */
    public MetricCard verifyNotLoading() {
        WebElement card = getCardElement();
        List<WebElement> skeletonElements = card.findElements(By.xpath(".//div[@data-testid='metric-skeleton']"));
        Assert.assertTrue(skeletonElements.isEmpty(), "Metric card should not be in loading state");
        return this;
    }
}
