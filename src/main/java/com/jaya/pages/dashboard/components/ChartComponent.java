package com.jaya.pages.dashboard.components;

import com.jaya.base.BasePage;
import com.jaya.pages.dashboard.locators.DashboardPageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

/**
 * Reusable component for chart interactions in the dashboard
 * Supports different chart types: Area, Pie, Bar, Composed charts
 * Provides common functionality for chart validation, interaction, and data verification
 */
public class ChartComponent extends BasePage {
    
    private final By chartContainerLocator;
    private final String chartTitle;
    private final ChartType chartType;
    
    public enum ChartType {
        AREA, PIE, BAR, COMPOSED, LINE
    }
    
    public ChartComponent(By chartContainerLocator, String chartTitle, ChartType chartType) {
        super();
        this.chartContainerLocator = chartContainerLocator;
        this.chartTitle = chartTitle;
        this.chartType = chartType;
    }
    
    /**
     * Get the chart container element
     */
    public WebElement getChartContainer() {
        return findElement(chartContainerLocator);
    }
    
    /**
     * Verify the chart is displayed and loaded
     */
    public ChartComponent verifyChartIsDisplayed() {
        Assert.assertTrue(isElementDisplayed(chartContainerLocator), 
            "Chart '" + chartTitle + "' is not displayed");
        return this;
    }
    
    /**
     * Verify the chart title is correct
     */
    public ChartComponent verifyChartTitle() {
        WebElement container = getChartContainer();
        List<WebElement> titleElements = container.findElements(By.xpath(".//h3"));
        
        Assert.assertFalse(titleElements.isEmpty(), "Chart title not found");
        String actualTitle = titleElements.get(0).getText();
        Assert.assertTrue(actualTitle.contains(chartTitle), 
            "Expected chart title to contain '" + chartTitle + "' but found '" + actualTitle + "'");
        return this;
    }
    
    /**
     * Verify the chart has data (not empty)
     */
    public ChartComponent verifyChartHasData() {
        WebElement container = getChartContainer();
        
        switch (chartType) {
            case AREA:
                List<WebElement> areaPaths = container.findElements(By.xpath(".//path[contains(@class, 'recharts-area-area')]"));
                Assert.assertFalse(areaPaths.isEmpty(), "Area chart should have data paths");
                break;
            case PIE:
                List<WebElement> pieSegments = container.findElements(DashboardPageLocators.REL_PIE_SEGMENT);
                Assert.assertFalse(pieSegments.isEmpty(), "Pie chart should have data segments");
                break;
            case BAR:
                List<WebElement> barRects = container.findElements(By.xpath(".//rect[contains(@class, 'recharts-bar-rectangle')]"));
                Assert.assertFalse(barRects.isEmpty(), "Bar chart should have data bars");
                break;
            case COMPOSED:
                List<WebElement> composedElements = container.findElements(DashboardPageLocators.REL_COMPOSED_ANY);
                Assert.assertFalse(composedElements.isEmpty(), "Composed chart should have chart elements");
                break;
            case LINE:
                List<WebElement> linePaths = container.findElements(By.xpath(".//path[contains(@class, 'recharts-line')]"));
                Assert.assertFalse(linePaths.isEmpty(), "Line chart should have data lines");
                break;
        }
        return this;
    }
    
    /**
     * Interact with chart tooltip by hovering over chart elements
     */
    public ChartComponent hoverOverChartData() {
        WebElement container = getChartContainer();
        List<WebElement> interactiveElements = container.findElements(
            DashboardPageLocators.REL_INTERACTIVE_CHART_ELEMENT);
        
        if (!interactiveElements.isEmpty()) {
            hoverOverElement(DashboardPageLocators.REL_INTERACTIVE_CHART_ELEMENT);
        }
        return this;
    }
    
    /**
     * Verify tooltip appears on hover
     */
    public ChartComponent verifyTooltipAppears() {
        hoverOverChartData();
        // Wait a moment for tooltip to appear
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        boolean tooltipExists = isElementDisplayed(By.xpath("//div[@class='recharts-tooltip-wrapper']"));
        Assert.assertTrue(tooltipExists, "Chart tooltip should appear on hover");
        return this;
    }
    
    /**
     * Change timeframe using dropdown (if available)
     */
    public ChartComponent selectTimeframe(String timeframeValue) {
        WebElement container = getChartContainer();
        List<WebElement> timeframeDropdowns = container.findElements(
            DashboardPageLocators.REL_TIMEFRAME_DROPDOWN);
        
        if (!timeframeDropdowns.isEmpty()) {
            Select timeframeSelect = new Select(timeframeDropdowns.get(0));
            timeframeSelect.selectByValue(timeframeValue);
        }
        return this;
    }
    
    /**
     * Toggle between Loss and Gain (if available)
     */
    public ChartComponent selectFlowType(String flowType) {
        WebElement container = getChartContainer();
        String toggleSelector = ".//button[@data-testid='" + flowType.toLowerCase() + "-toggle']";
        List<WebElement> toggleButtons = container.findElements(By.xpath(toggleSelector));
        
        if (!toggleButtons.isEmpty()) {
            toggleButtons.get(0).click();
            // Verify the button becomes active
            verifyToggleIsActive(flowType);
        }
        return this;
    }
    
    /**
     * Verify a toggle button is active
     */
    public ChartComponent verifyToggleIsActive(String flowType) {
        WebElement container = getChartContainer();
        String toggleSelector = ".//button[@data-testid='" + flowType.toLowerCase() + "-toggle']";
        WebElement toggleButton = container.findElement(By.xpath(toggleSelector));
        
        String classAttribute = toggleButton.getDomAttribute("class");
        Assert.assertTrue(classAttribute.contains("active"), 
            flowType + " toggle should be active");
        return this;
    }
    
    /**
     * Verify chart updates after interaction (timeframe or toggle change)
     */
    public ChartComponent verifyChartUpdates() {
        // Wait for potential loading state
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify chart still has data after update
        verifyChartHasData();
        return this;
    }
    
    /**
     * Get chart legend items (for pie charts)
     */
    public List<WebElement> getLegendItems() {
        WebElement container = getChartContainer();
        return container.findElements(By.xpath(".//div[@data-testid='legend']//li"));
    }
    
    /**
     * Verify chart has legend (for applicable chart types)
     */
    public ChartComponent verifyChartHasLegend() {
        if (chartType == ChartType.PIE) {
            List<WebElement> legendItems = getLegendItems();
            Assert.assertFalse(legendItems.isEmpty(), "Pie chart should have legend items");
        }
        return this;
    }
    
    /**
     * Get total amount displayed (for charts that show totals)
     */
    public String getTotalAmount() {
        WebElement container = getChartContainer();
    List<WebElement> totalElements = container.findElements(DashboardPageLocators.REL_TOTAL_AMOUNT);
        
        if (!totalElements.isEmpty()) {
            return totalElements.get(0).getText();
        }
        return "";
    }
    
    /**
     * Verify total amount is displayed and formatted correctly
     */
    public ChartComponent verifyTotalAmountDisplayed() {
        String total = getTotalAmount();
        Assert.assertFalse(total.isEmpty(), "Chart should display total amount");
        Assert.assertTrue(total.contains("₹"), "Total amount should be in currency format");
        return this;
    }
    
    /**
     * Verify chart is not in loading state
     */
    public ChartComponent verifyChartNotLoading() {
        WebElement container = getChartContainer();
    List<WebElement> skeletonElements = container.findElements(DashboardPageLocators.REL_SKELETON_ANY);
        
        Assert.assertTrue(skeletonElements.isEmpty(), "Chart should not be in loading state");
        return this;
    }
    
    /**
     * Verify chart responsive behavior
     */
    public ChartComponent verifyResponsiveBehavior() {
        // Check if mobile container exists
        List<WebElement> mobileContainers = findElements(By.xpath("//div[@data-testid='mobile-chart-container']"));
        
        if (!mobileContainers.isEmpty()) {
            // Verify mobile-specific styles are applied
            WebElement mobileContainer = mobileContainers.get(0);
            String classAttribute = mobileContainer.getDomAttribute("class");
            Assert.assertTrue(classAttribute.contains("mobile") || classAttribute.contains("responsive"), 
                "Chart should have mobile responsive styles");
        }
        return this;
    }
    
    /**
     * Comprehensive chart validation
     */
    public ChartComponent validateCompleteChart() {
        verifyChartIsDisplayed()
            .verifyChartTitle()
            .verifyChartHasData()
            .verifyChartNotLoading();
        
        if (chartType == ChartType.PIE) {
            verifyChartHasLegend();
        }
        
        return this;
    }
}
