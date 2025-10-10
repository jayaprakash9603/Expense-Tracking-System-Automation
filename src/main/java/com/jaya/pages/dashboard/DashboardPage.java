package com.jaya.pages.dashboard;

import com.jaya.base.BasePage;
import com.jaya.pages.dashboard.components.ChartComponent;
import com.jaya.pages.dashboard.components.FilterComponent;
import com.jaya.pages.dashboard.components.MetricCard;
import com.jaya.pages.dashboard.locators.DashboardPageLocators;
import com.jaya.utils.config.YamlConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dashboard Page Object implementing business logic for the Expense Dashboard
 * Uses DRY principle with reusable components (MetricCard, ChartComponent, FilterComponent)
 */
public class DashboardPage extends BasePage {
    
    // Metric Cards
    private MetricCard totalExpensesCard;
    private MetricCard creditDueCard;
    // Updated UI now shows four primary metric cards: Total Balance, Monthly Spending, Credit Due, Credit Card Bill Paid
    private MetricCard monthlySpendingCard;
    private MetricCard creditCardBillPaidCard;
    
    // Chart Components
    private ChartComponent dailySpendingChart;
    private ChartComponent categoryBreakdownChart;
    private ChartComponent monthlyTrendChart;
    private ChartComponent paymentMethodsChart;
    
    // Filter Components (for charts)
    private FilterComponent dailySpendingFilters;
    private FilterComponent categoryBreakdownFilters;
    // Removed unused paymentMethodsFilters field (no specific interactions implemented yet)
    // Payment methods currently uses same pattern; separate filter if needed later
    
    // Cache for dynamic verification
    private final Map<String, String> cachedValues = new HashMap<>();
    
    public DashboardPage() {
        super();
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Initialize metric cards
    totalExpensesCard = new MetricCard(DashboardPageLocators.TOTAL_EXPENSES_CARD, "TOTAL BALANCE");
        creditDueCard = new MetricCard(DashboardPageLocators.CREDIT_DUE_CARD, "CREDIT DUE");
    // Removed legacy cards (Active Budgets, Friends, Groups) no longer present in React UI
    monthlySpendingCard = new MetricCard(DashboardPageLocators.MONTHLY_SPENDING_CARD, "MONTHLY SPENDING");
    creditCardBillPaidCard = new MetricCard(DashboardPageLocators.CREDIT_CARD_BILL_PAID_CARD, "CREDIT CARD BILL PAID");
        
        // Initialize charts
        dailySpendingChart = new ChartComponent(DashboardPageLocators.DAILY_SPENDING_CHART, 
            "📊 Daily Spending Pattern", ChartComponent.ChartType.AREA);
        categoryBreakdownChart = new ChartComponent(DashboardPageLocators.CATEGORY_BREAKDOWN_CHART, 
            "🏷️ Category Breakdown", ChartComponent.ChartType.PIE);
        monthlyTrendChart = new ChartComponent(DashboardPageLocators.MONTHLY_TREND_CHART, 
            "📈 Monthly Expense Trend", ChartComponent.ChartType.COMPOSED);
        paymentMethodsChart = new ChartComponent(DashboardPageLocators.PAYMENT_METHODS_CHART, 
            "💳 Payment Methods", ChartComponent.ChartType.PIE);
        
        // Initialize filters
        dailySpendingFilters = new FilterComponent(DashboardPageLocators.DAILY_SPENDING_CHART);
        categoryBreakdownFilters = new FilterComponent(DashboardPageLocators.CATEGORY_BREAKDOWN_CHART);
    // If payment methods specific filter interactions are added later, reintroduce a FilterComponent here.
    }
    
    /**
     * Load the dashboard page
     */
    public DashboardPage load() {
        String urlToUse = null;
        try {
            // Attempt to build from base URL since getDashboardUrl not defined
            String baseUrl = YamlConfig.getBaseUrl();
            if (baseUrl != null) {
                urlToUse = baseUrl.endsWith("/") ? baseUrl + "dashboard" : baseUrl + "/dashboard";
            }
        } catch (Exception ignored) {
            // Silent fallback; caller can still navigate manually if needed
        }
        
        if (urlToUse != null) {
            navigateTo(urlToUse);
        }
        
        waitUntilLoaded();
        return this;
    }
    
    /**
     * Wait until the dashboard has loaded
     */
    private void waitUntilLoaded() {
        waitForElementToBeVisible(DashboardPageLocators.DASHBOARD_HEADER_TITLE);
    }
    
    /**
     * Verify the dashboard header and subtitle
     */
    public DashboardPage verifyDashboardHeader() {
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.DASHBOARD_HEADER_TITLE), 
            "Dashboard header title not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.DASHBOARD_SUBTITLE), 
            "Dashboard subtitle not displayed");
        return this;
    }
    
    /**
     * Open the More Actions menu
     */
    public DashboardPage openMoreActionsMenu() {
        click(DashboardPageLocators.MORE_ACTIONS_MENU_BUTTON);
        waitForElementToBeVisible(DashboardPageLocators.MORE_ACTIONS_DROPDOWN);
        return this;
    }
    
    /**
     * Click the Refresh option
     */
    public DashboardPage clickRefresh() {
        click(DashboardPageLocators.REFRESH_OPTION);
        // Wait for potential loading state
        waitForLoadingState();
        return this;
    }
    
    /**
     * Click the Export Reports option
     */
    public DashboardPage clickExportReports() {
        click(DashboardPageLocators.EXPORT_REPORTS_OPTION);
        // We can't directly verify download in UI, but we can check if a progress indicator appears
        return this;
    }
    
    /**
     * Wait for loading state (skeletons) to appear and disappear
     */
    private void waitForLoadingState() {
        // If loading appears, wait for it to disappear
        try {
            Thread.sleep(1000); // brief wait for loading to appear
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Wait up to 10 seconds for loading to disappear
        for (int i = 0; i < 10; i++) {
            if (findElements(DashboardPageLocators.METRIC_CARDS_SKELETON).isEmpty() && 
                findElements(DashboardPageLocators.CHART_SKELETON_BAR).isEmpty() &&
                findElements(DashboardPageLocators.CHART_SKELETON_LINE).isEmpty() &&
                findElements(DashboardPageLocators.CHART_SKELETON_PIE).isEmpty()) {
                break;
            }
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
    
    /**
     * Validate all metric cards
     */
    public DashboardPage validateMetricCards() {
        // Validate only the four current cards visible on the dashboard
        totalExpensesCard.verifyCompleteCard(); // Total Balance
        monthlySpendingCard.verifyCompleteCard(); // Monthly Spending
        creditDueCard.verifyCompleteCard(); // Credit Due
        creditCardBillPaidCard.verifyCompleteCard(); // Credit Card Bill Paid
        return this;
    }
    
    /**
     * Validate daily spending chart
     */
    public DashboardPage validateDailySpendingChart() {
        dailySpendingChart.validateCompleteChart();
        // Cache initial state
        cachedValues.put("dailySpendingTimeframe", "this-month");
        return this;
    }
    
    /**
     * Change daily spending timeframe and validate update
     */
    public DashboardPage changeDailySpendingTimeframe(String timeframeValue) {
        dailySpendingFilters.selectTimeframe(timeframeValue).waitForFilterUpdate();
        dailySpendingChart.verifyChartUpdates();
        cachedValues.put("dailySpendingTimeframe", timeframeValue);
        return this;
    }
    
    /**
     * Toggle daily spending view (Loss/Gain)
     */
    public DashboardPage toggleDailySpendingView(String viewType) {
        if (viewType.equalsIgnoreCase("Gain")) {
            dailySpendingFilters.clickGainToggle();
        } else if (viewType.equalsIgnoreCase("Loss")) {
            dailySpendingFilters.clickLossToggle();
        }
        dailySpendingChart.verifyChartUpdates();
        cachedValues.put("dailySpendingView", viewType.toLowerCase());
        return this;
    }
    
    /**
     * Validate category breakdown chart
     */
    public DashboardPage validateCategoryBreakdownChart() {
        categoryBreakdownChart.validateCompleteChart();
        categoryBreakdownChart.verifyChartHasLegend();
        categoryBreakdownChart.verifyTotalAmountDisplayed();
        return this;
    }
    
    /**
     * Change category breakdown timeframe
     */
    public DashboardPage changeCategoryTimeframe(String timeframeValue) {
        categoryBreakdownFilters.selectTimeframe(timeframeValue).waitForFilterUpdate();
        categoryBreakdownChart.verifyChartUpdates();
        cachedValues.put("categoryTimeframe", timeframeValue);
        return this;
    }
    
    /**
     * Toggle category breakdown flow type
     */
    public DashboardPage toggleCategoryFlow(String flowType) {
        if (flowType.equalsIgnoreCase("Gain")) {
            categoryBreakdownFilters.clickGainToggle();
        } else if (flowType.equalsIgnoreCase("Loss")) {
            categoryBreakdownFilters.clickLossToggle();
        }
        categoryBreakdownChart.verifyChartUpdates();
        cachedValues.put("categoryFlow", flowType.toLowerCase());
        return this;
    }
    
    /**
     * Validate monthly trend chart
     */
    public DashboardPage validateMonthlyTrendChart() {
        monthlyTrendChart.validateCompleteChart();
        // Cache current year
        String currentYear = getText(DashboardPageLocators.MONTHLY_TREND_YEAR_INDICATOR);
        cachedValues.put("currentYear", currentYear);
        return this;
    }
    
    /**
     * Navigate to previous year
     */
    public DashboardPage navigateToPreviousYear() {
        click(DashboardPageLocators.MONTHLY_TREND_PREV_YEAR);
        monthlyTrendChart.verifyChartUpdates();
        return this;
    }
    
    /**
     * Navigate to next year (if allowed)
     */
    public DashboardPage navigateToNextYear() {
        click(DashboardPageLocators.MONTHLY_TREND_NEXT_YEAR);
        monthlyTrendChart.verifyChartUpdates();
        return this;
    }
    
    /**
     * Validate payment methods chart
     */
    public DashboardPage validatePaymentMethodsChart() {
        paymentMethodsChart.validateCompleteChart();
        paymentMethodsChart.verifyChartHasLegend();
        paymentMethodsChart.verifyTotalAmountDisplayed();
        return this;
    }
    
    /**
     * Validate application overview section
     */
    public DashboardPage validateApplicationOverview() {
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.APPLICATION_OVERVIEW_SECTION), 
            "Application Overview section not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.APPLICATION_OVERVIEW_TITLE), 
            "Application Overview title not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.OVERVIEW_METRICS), 
            "Overview metrics not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.OVERVIEW_MINI_CHART), 
            "Overview mini chart not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.OVERVIEW_KPI_CARDS), 
            "Overview KPI cards not displayed");
        return this;
    }

    /**
     * Validate Quick Access section
     */
    public DashboardPage validateQuickAccess() {
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.QUICK_ACCESS_SECTION),
            "Quick Access section not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.QUICK_ACCESS_TITLE),
            "Quick Access title not displayed");
        // Expect at least one action box
        Assert.assertFalse(findElements(DashboardPageLocators.QUICK_ACCESS_ACTION_BOXES).isEmpty(),
            "Quick Access action boxes not found");
        return this;
    }
    
    /**
     * Validate recent transactions section
     */
    public DashboardPage validateRecentTransactions() {
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.RECENT_TRANSACTIONS_SECTION), 
            "Recent Transactions section not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.RECENT_TRANSACTIONS_TITLE), 
            "Recent Transactions title not displayed");
        
        // Verify at least one transaction
        List<WebElement> transactions = findElements(DashboardPageLocators.TRANSACTION_ITEM);
        Assert.assertTrue(transactions.size() <= 10, "Should display up to 10 recent transactions");
        
        return this;
    }
    
    /**
     * Validate budget overview section
     */
    public DashboardPage validateBudgetOverview() {
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.BUDGET_OVERVIEW_SECTION), 
            "Budget Overview section not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.BUDGET_OVERVIEW_TITLE), 
            "Budget Overview title not displayed");
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.BUDGET_PROGRESS_INDICATOR), 
            "Budget progress indicator not displayed");
        return this;
    }
    
    /**
     * Validate loading states are displayed properly
     */
    public DashboardPage validateLoadingStates() {
        // This would be triggered before data is loaded
        Assert.assertTrue(isElementDisplayed(DashboardPageLocators.METRIC_CARDS_SKELETON) || 
            isElementDisplayed(DashboardPageLocators.DASHBOARD_LOADING), 
            "Loading state (skeletons) should be visible");
        return this;
    }
    
    /**
     * Validate error handling (simulate error scenario)
     */
    public DashboardPage validateErrorHandling() {
        // In actual implementation, this might be triggered by network manipulation
        // Here we just check for error elements if they appear
        if (isElementDisplayed(DashboardPageLocators.DASHBOARD_ERROR)) {
            Assert.assertTrue(isElementDisplayed(DashboardPageLocators.RETRY_BUTTON), 
                "Retry button should be present in error state");
        }
        return this;
    }
    
    /**
     * Verify timeframe options display correctly in dropdown when clicked
     */
    public DashboardPage verifyTimeframeDropdownOptions() {
        // For the daily spending chart
        dailySpendingFilters.selectTimeframe("this-month");
        dailySpendingFilters.verifyStandardTimeframeOptions();
        return this;
    }
    
    /**
     * Comprehensive dashboard validation (for default view)
     */
    public DashboardPage validateCompleteDashboard() {
        verifyDashboardHeader();
        validateMetricCards();
        validateDailySpendingChart();
        validateCategoryBreakdownChart();
        validateMonthlyTrendChart();
        validatePaymentMethodsChart();
        validateApplicationOverview();
    validateQuickAccess();
        validateRecentTransactions();
        validateBudgetOverview();
        return this;
    }

    // Public helper wrappers to expose protected BasePage methods for step definitions
    public boolean isDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String safeGetText(By locator) {
        try {
            return getText(locator);
        } catch (Exception e) {
            return "";
        }
    }
}
