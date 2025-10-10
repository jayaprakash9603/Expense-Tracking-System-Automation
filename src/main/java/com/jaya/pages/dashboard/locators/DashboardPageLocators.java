package com.jaya.pages.dashboard.locators;

import org.openqa.selenium.By;

/**
 * Page locators for the Expense Dashboard
 * Contains all element identifiers for dashboard components including:
 * - Header and navigation elements
 * - Metric cards for financial overview
 * - Chart components (daily spending, category breakdown, monthly trend, payment methods)
 * - Application overview section
 * - Recent transactions section
 * - Budget overview section
 * - Loading states and error elements
 */
public class DashboardPageLocators {

    // Dashboard Header Elements
    public static final By DASHBOARD_HEADER_TITLE = By.xpath("//h1[contains(text(), '💰 Financial Dashboard')]");
    public static final By DASHBOARD_SUBTITLE = By.xpath("//p[contains(text(), 'Real-time insights into your financial health')]");
    public static final By MORE_ACTIONS_MENU_BUTTON = By.xpath("//button[@aria-label='More actions']");
    public static final By MORE_ACTIONS_DROPDOWN = By.xpath("//div[@role='menu']");
    public static final By REFRESH_OPTION = By.xpath("//div[@role='menuitem']//span[contains(text(), 'Refresh')]");
    public static final By EXPORT_REPORTS_OPTION = By.xpath("//div[@role='menuitem']//span[contains(text(), 'Export Reports')]");

    // Financial Metrics Cards
    // Updated metric cards: support old data-testid plus new class/text based structure
    public static final By METRIC_CARDS_SECTION = By.xpath("//div[@data-testid='metric-cards'] | //div[contains(@class,'metrics-grid')]");
    public static final By TOTAL_EXPENSES_CARD = By.xpath(
        "//div[@data-testid='total-expenses-card'] | " +
        "//div[contains(@class,'metric-card')][.//h3[normalize-space()='Total Balance' or normalize-space()='Total Expenses']]"
    );
    public static final By CREDIT_DUE_CARD = By.xpath(
        "//div[@data-testid='credit-due-card'] | " +
        "//div[contains(@class,'metric-card')][.//h3[normalize-space()='Credit Due']]"
    );
    public static final By ACTIVE_BUDGETS_CARD = By.xpath(
        "//div[@data-testid='active-budgets-card'] | " +
        "//div[contains(@class,'metric-card')][.//h3[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'active budgets')]]"
    );
    public static final By FRIENDS_CARD = By.xpath(
        "//div[@data-testid='friends-card'] | " +
        "//div[contains(@class,'metric-card')][.//h3[normalize-space()='Friends']]"
    );
    public static final By GROUPS_CARD = By.xpath(
        "//div[@data-testid='groups-card'] | " +
        "//div[contains(@class,'metric-card')][.//h3[normalize-space()='Groups']]"
    );
    // Additional card example from new UI (credit card bill paid)
    public static final By CREDIT_CARD_BILL_PAID_CARD = By.xpath(
        "//div[contains(@class,'metric-card')][.//h3[normalize-space()='Credit Card Bill Paid']]"
    );

    // New Monthly Spending card (from updated UI snippet)
    public static final By MONTHLY_SPENDING_CARD = By.xpath(
        "//div[contains(@class,'metric-card')][.//h3[normalize-space()='Monthly Spending']]"
    );

    // Metric Card Elements (generic patterns)
    public static final By METRIC_CARD_TITLE = By.xpath(".//h3 | .//h3[@data-testid='metric-title']");
    public static final By METRIC_CARD_VALUE = By.xpath(".//div[contains(@class,'metric-value')] | .//span[@data-testid='metric-value']");
    public static final By METRIC_CARD_TREND = By.xpath(".//div[contains(@class,'trend-indicator')] | .//div[@data-testid='trend-indicator']");
    public static final By METRIC_CARD_PERCENTAGE = By.xpath(".//div[contains(@class,'metric-change')] | .//span[@data-testid='percentage-change']");
    public static final By METRIC_CARD_SPARKLINE_BAR = By.xpath(".//div[contains(@class,'sparkline-bar')]");

    // ===== Dynamic Metric Card Locator Builders (title-driven unique scoping) =====
    // These methods allow selecting the specific metric card by its <h3> title text and then
    // scoping child lookups (value, trend, percentage, sparkline) without relying on data-testid duplication.
    // Title matching is normalized (trim + exact). Adjust normalize-space() usage if partial matches are required.

    /** Returns the metric card container By for a given title. */
    public static By metricCardByTitle(String title) {
        String xpath = "//div[contains(@class,'metric-card')][.//h3[normalize-space()=" + quoted(title) + "]]";
        return By.xpath(xpath);
    }

    /** Returns the value element inside the metric card with the given title. */
    public static By metricCardValue(String title) {
        String xpath = metricCardBaseXpath(title) + "//div[contains(@class,'metric-value')]";
        return By.xpath(xpath);
    }

    /** Returns the trend indicator element inside the metric card with the given title. */
    public static By metricCardTrend(String title) {
        String xpath = metricCardBaseXpath(title) + "//div[contains(@class,'trend-indicator')]";
        return By.xpath(xpath);
    }

    /** Returns the percentage / change element inside the metric card with the given title. */
    public static By metricCardPercentage(String title) {
        String xpath = metricCardBaseXpath(title) + "//div[contains(@class,'metric-change')]";
        return By.xpath(xpath);
    }

    /** Returns all sparkline bars for the card with the given title. */
    public static By metricCardSparklines(String title) {
        String xpath = metricCardBaseXpath(title) + "//div[contains(@class,'sparkline-bar')]";
        return By.xpath(xpath);
    }

    /** Internal helper: base xpath for metric card body given a title. */
    private static String metricCardBaseXpath(String title) {
        return "//div[contains(@class,'metric-card')][.//h3[normalize-space()=" + quoted(title) + "]]//";
    }

    /** Escapes and wraps a literal for XPath. Basic single-quote handling. */
    private static String quoted(String text) {
        if (text.contains("'")) {
            // Split on single quotes and concat using concat() XPath function
            String[] parts = text.split("'");
            StringBuilder sb = new StringBuilder("concat(");
            for (int i = 0; i < parts.length; i++) {
                if (i > 0) sb.append(",");
                sb.append("'" + parts[i] + "'");
                if (i < parts.length - 1) {
                    // insert a literal single quote between parts: concat('part1', "'", 'part2')
                    sb.append(",\"'\",");
                }
            }
            sb.append(")");
            return sb.toString();
        }
        return "'" + text + "'";
    }

    // Specific Total Balance (alias of Total Expenses in new UI)
    public static final By TOTAL_BALANCE_CARD = By.xpath(
        "//div[contains(@class,'metric-card')][.//h3[normalize-space()='Total Balance']]"
    );
    public static final By TOTAL_BALANCE_TITLE = By.xpath(
        "//div[contains(@class,'metric-card')]//h3[normalize-space()='Total Balance']"
    );
    public static final By TOTAL_BALANCE_VALUE = By.xpath(
        "//h3[normalize-space()='Total Balance']/following-sibling::div[contains(@class,'metric-value')]"
    );
    public static final By TOTAL_BALANCE_CHANGE = By.xpath(
        "//h3[normalize-space()='Total Balance']/following-sibling::div[contains(@class,'metric-change')]"
    );
    public static final By TOTAL_BALANCE_CHANGE_POSITIVE = By.xpath(
        "//h3[normalize-space()='Total Balance']/following-sibling::div[contains(@class,'metric-change')][contains(@class,'positive')]"
    );
    public static final By TOTAL_BALANCE_CHANGE_NEGATIVE = By.xpath(
        "//h3[normalize-space()='Total Balance']/following-sibling::div[contains(@class,'metric-change')][contains(@class,'negative')]"
    );

    // Daily Spending Chart
    // Daily Spending Chart (supports new class-based markup and legacy data-testid attributes)
    public static final By DAILY_SPENDING_CHART = By.xpath(
        "//div[@data-testid='daily-spending-chart'] | //div[contains(@class,'daily-spending-chart')]"
    );
    public static final By DAILY_SPENDING_TITLE = By.xpath(
        "//h3[contains(text(), '📊 Daily Spending Pattern')]"
    );
    public static final By DAILY_SPENDING_TIMEFRAME_DROPDOWN = By.xpath(
        "//select[@data-testid='daily-timeframe-selector'] | //div[contains(@class,'daily-spending-chart')]//select[contains(@class,'time-selector')]"
    );
    public static final By DAILY_SPENDING_LOSS_TOGGLE = By.xpath(
        "//button[@data-testid='loss-toggle'] | //div[contains(@class,'daily-spending-chart')]//button[contains(@class,'toggle-btn')][contains(normalize-space(.),'Loss')]"
    );
    public static final By DAILY_SPENDING_GAIN_TOGGLE = By.xpath(
        "//button[@data-testid='gain-toggle'] | //div[contains(@class,'daily-spending-chart')]//button[contains(@class,'toggle-btn')][contains(normalize-space(.),'Gain')]"
    );
    public static final By DAILY_SPENDING_CHART_AREA = By.xpath(
        "//div[@data-testid='daily-spending-area-chart'] | //div[contains(@class,'daily-spending-chart')]//div[contains(@class,'recharts-responsive-container')]"
    );
    public static final By DAILY_CHART_TOOLTIP = By.xpath(
        "//div[@data-testid='chart-tooltip'] | //div[contains(@class,'recharts-tooltip-wrapper')]"
    );

    // Category Breakdown Chart
    public static final By CATEGORY_BREAKDOWN_CHART = By.xpath(
        "//div[@data-testid='category-breakdown-chart'] | //div[contains(@class,'category-breakdown')]"
    );
    public static final By CATEGORY_BREAKDOWN_TITLE = By.xpath(
        "//h3[contains(text(), '🏷️ Category Breakdown')]"
    );
    public static final By CATEGORY_PIE_CHART = By.xpath(
        "//div[@data-testid='category-pie-chart'] | //div[contains(@class,'category-breakdown')]//div[contains(@class,'recharts-responsive-container')]"
    );
    public static final By CATEGORY_LEGEND = By.xpath(
        "//div[@data-testid='category-legend'] | //div[contains(@class,'category-breakdown')]//div[contains(@class,'recharts-legend-wrapper')]"
    );
    public static final By CATEGORY_TOTAL_AMOUNT = By.xpath(
        "//div[@data-testid='category-total-amount'] | //div[contains(@class,'category-breakdown')]//div[contains(@class,'total-amount')]"
    );
    public static final By CATEGORY_TIMEFRAME_DROPDOWN = By.xpath(
        "//select[@data-testid='category-timeframe-selector'] | //div[contains(@class,'category-breakdown')]//select[contains(@class,'time-selector')]"
    );
    public static final By CATEGORY_LOSS_TOGGLE = By.xpath(
        "//button[@data-testid='category-loss-toggle'] | //div[contains(@class,'category-breakdown')]//button[contains(@class,'toggle-btn')][contains(normalize-space(.),'Loss')]"
    );
    public static final By CATEGORY_GAIN_TOGGLE = By.xpath(
        "//button[@data-testid='category-gain-toggle'] | //div[contains(@class,'category-breakdown')]//button[contains(@class,'toggle-btn')][contains(normalize-space(.),'Gain')]"
    );

    // Monthly Trend Chart
    public static final By MONTHLY_TREND_CHART = By.xpath(
        "//div[@data-testid='monthly-trend-chart'] | //div[contains(@class,'monthly-trend')]"
    );
    public static final By MONTHLY_TREND_TITLE = By.xpath(
        "//h3[contains(text(), '📈 Monthly Expense Trend')]"
    );
    public static final By MONTHLY_TREND_PREV_YEAR = By.xpath(
        "//button[@data-testid='prev-year-btn'] | //div[contains(@class,'monthly-trend')]//button[contains(@class,'nav-left')]"
    );
    public static final By MONTHLY_TREND_NEXT_YEAR = By.xpath(
        "//button[@data-testid='next-year-btn'] | //div[contains(@class,'monthly-trend')]//button[contains(@class,'nav-right')]"
    );
    public static final By MONTHLY_TREND_YEAR_INDICATOR = By.xpath(
        "//span[@data-testid='current-year'] | //div[contains(@class,'monthly-trend')]//span[contains(@class,'year-chip')]"
    );
    public static final By MONTHLY_TREND_COMPOSED_CHART = By.xpath(
        "//div[@data-testid='monthly-composed-chart'] | //div[contains(@class,'monthly-trend')]//div[contains(@class,'recharts-responsive-container')]"
    );
    public static final By MONTHLY_TREND_STATISTICS = By.xpath(
        "//div[@data-testid='trend-statistics'] | //div[contains(@class,'monthly-trend')]//div[contains(@class,'trend-stats')]"
    );

    // Payment Methods Chart
    public static final By PAYMENT_METHODS_CHART = By.xpath(
        "//div[@data-testid='payment-methods-chart'] | //div[contains(@class,'payment-methods')]"
    );
    public static final By PAYMENT_METHODS_TITLE = By.xpath(
        "//h3[contains(text(), '💳 Payment Methods')]"
    );
    public static final By PAYMENT_METHODS_PIE_CHART = By.xpath(
        "//div[@data-testid='payment-methods-pie-chart'] | //div[contains(@class,'payment-methods')]//div[contains(@class,'recharts-responsive-container')]"
    );
    public static final By PAYMENT_METHODS_TIMEFRAME_DROPDOWN = By.xpath(
        "//select[@data-testid='payment-timeframe-selector'] | //div[contains(@class,'payment-methods')]//select[contains(@class,'time-selector')]"
    );
    public static final By PAYMENT_METHODS_LOSS_TOGGLE = By.xpath(
        "//button[@data-testid='payment-loss-toggle'] | //div[contains(@class,'payment-methods')]//button[contains(@class,'toggle-btn')][contains(normalize-space(.),'Loss')]"
    );
    public static final By PAYMENT_METHODS_GAIN_TOGGLE = By.xpath(
        "//button[@data-testid='payment-gain-toggle'] | //div[contains(@class,'payment-methods')]//button[contains(@class,'toggle-btn')][contains(normalize-space(.),'Gain')]"
    );
    public static final By PAYMENT_METHODS_TOTAL = By.xpath(
        "//div[@data-testid='payment-methods-total'] | //div[contains(@class,'payment-methods')]//div[contains(@class,'total-amount')]"
    );

    // Application Overview Section
    public static final By APPLICATION_OVERVIEW_SECTION = By.xpath(
        "//div[@data-testid='application-overview'] | //div[contains(@class,'summary-overview')]"
    );
    public static final By APPLICATION_OVERVIEW_TITLE = By.xpath(
        "//h3[contains(text(), '🔎 Application Overview')]"
    );
    public static final By OVERVIEW_METRICS = By.xpath(
        "//div[@data-testid='overview-metrics'] | //div[contains(@class,'summary-overview')]//div[contains(@class,'overview-metrics')]"
    );
    public static final By OVERVIEW_MINI_CHART = By.xpath(
        "//div[@data-testid='overview-mini-chart'] | //div[contains(@class,'summary-overview')]//div[contains(@class,'overview-chart')]//div[contains(@class,'recharts-responsive-container')]"
    );
    public static final By OVERVIEW_KPI_CARDS = By.xpath(
        "//div[@data-testid='overview-kpi-cards'] | //div[contains(@class,'summary-overview')]//div[contains(@class,'kpi-row')]"
    );
    public static final By AVERAGE_DAILY_SPEND_KPI = By.xpath("//div[@data-testid='avg-daily-spend']");
    public static final By SAVINGS_RATE_KPI = By.xpath("//div[@data-testid='savings-rate']");
    public static final By UPCOMING_BILLS_KPI = By.xpath("//div[@data-testid='upcoming-bills']");
    public static final By TOP_EXPENSES_LIST = By.xpath("//div[@data-testid='top-expenses-list']");

    // Quick Access Section
    public static final By QUICK_ACCESS_SECTION = By.xpath(
        "//div[@data-testid='quick-access'] | //div[contains(@class,'quick-access')]"
    );
    public static final By QUICK_ACCESS_TITLE = By.xpath(
        "//p[contains(@class,'qa-title')][normalize-space()='Quick Access']"
    );
    public static final By QUICK_ACCESS_ACTION_BOXES = By.xpath(
        "//div[contains(@class,'quick-access')]//button[contains(@class,'qa-box')]"
    );

    // Recent Transactions Section
    public static final By RECENT_TRANSACTIONS_SECTION = By.xpath("//div[@data-testid='recent-transactions'] | //div[contains(@class,'recent-transactions')]");
    public static final By RECENT_TRANSACTIONS_TITLE = By.xpath("//h3[contains(text(), '🕒 Recent Transactions')]");
    public static final By VIEW_ALL_TRANSACTIONS_BUTTON = By.xpath("//button[contains(text(), 'View All')]");
    public static final By TRANSACTION_LIST = By.xpath("//div[@data-testid='transaction-list']");
    public static final By TRANSACTION_ITEM = By.xpath("//div[@class='transaction-item']");
    public static final By TRANSACTION_ICON = By.xpath(".//div[@data-testid='transaction-icon']");
    public static final By TRANSACTION_NAME = By.xpath(".//span[@data-testid='transaction-name']");
    public static final By TRANSACTION_CATEGORY = By.xpath(".//span[@data-testid='transaction-category']");
    public static final By TRANSACTION_DATE = By.xpath(".//span[@data-testid='transaction-date']");
    public static final By TRANSACTION_AMOUNT = By.xpath(".//span[@data-testid='transaction-amount']");
    public static final By LOSS_TRANSACTION = By.xpath("//div[@data-testid='transaction-item' and contains(@class, 'loss')]");
    public static final By GAIN_TRANSACTION = By.xpath("//div[@data-testid='transaction-item' and contains(@class, 'gain')]");

    // Budget Overview Section
    public static final By BUDGET_OVERVIEW_SECTION = By.xpath("//div[@data-testid='budget-overview'] | //div[contains(@class,'budget-overview')]");
    public static final By BUDGET_OVERVIEW_TITLE = By.xpath("//h3[contains(text(), '🎯 Budget Overview')]");
    public static final By BUDGET_PROGRESS_INDICATOR = By.xpath("//div[@class='budget-progress']");
    public static final By BUDGET_PERCENTAGE_USED = By.xpath("//span[@data-testid='budget-percentage']");
    public static final By BUDGET_REMAINING_AMOUNT = By.xpath("//span[@data-testid='budget-remaining']");
    public static final By BUDGET_TOTAL_SPENT = By.xpath("//span[@data-testid='budget-spent']");

    // Loading States
    public static final By DASHBOARD_LOADING = By.xpath("//div[@data-testid='dashboard-loading']");
    public static final By METRIC_CARDS_SKELETON = By.xpath("//div[@data-testid='metric-skeleton']");
    public static final By CHART_SKELETON_BAR = By.xpath("//div[@data-testid='skeleton-bar']");
    public static final By CHART_SKELETON_LINE = By.xpath("//div[@data-testid='skeleton-line']");
    public static final By CHART_SKELETON_PIE = By.xpath("//div[@data-testid='skeleton-pie']");
    public static final By TRANSACTION_SKELETON = By.xpath("//div[@data-testid='transaction-skeleton']");

    // Error States
    public static final By DASHBOARD_ERROR = By.xpath("//div[@data-testid='dashboard-error']");
    public static final By CHART_ERROR_MESSAGE = By.xpath("//div[@data-testid='chart-error']");
    public static final By RETRY_BUTTON = By.xpath("//button[@data-testid='retry-btn']");
    public static final By ERROR_MESSAGE_TEXT = By.xpath("//span[@data-testid='error-message']");

    // Timeframe Options (for dropdowns)
    public static final By TIMEFRAME_THIS_MONTH = By.xpath("//option[@value='this-month']");
    public static final By TIMEFRAME_LAST_MONTH = By.xpath("//option[@value='last-month']");
    public static final By TIMEFRAME_LAST_3_MONTHS = By.xpath("//option[@value='last-3-months']");

    // Toggle States
    public static final By ACTIVE_TOGGLE = By.xpath("//button[contains(@class, 'active')]");
    public static final By INACTIVE_TOGGLE = By.xpath("//button[not(contains(@class, 'active'))]");

    // Mobile Responsive Elements
    public static final By MOBILE_CHART_CONTAINER = By.xpath("//div[@data-testid='mobile-chart-container']");
    public static final By MOBILE_METRIC_CARD = By.xpath("//div[@data-testid='mobile-metric-card']");
    public static final By RESPONSIVE_FONT_SMALL = By.xpath("//*[contains(@class, 'text-sm')]");

    // Common Chart Elements
    public static final By CHART_CONTAINER = By.xpath("//div[contains(@class, 'recharts-wrapper')]");
    public static final By CHART_X_AXIS = By.xpath("//g[@class='recharts-xAxis']");
    public static final By CHART_Y_AXIS = By.xpath("//g[@class='recharts-yAxis']");
    public static final By CHART_TOOLTIP_CONTENT = By.xpath("//div[@class='recharts-tooltip-wrapper']");
    public static final By PIE_CHART_SEGMENT = By.xpath("//path[contains(@class, 'recharts-pie-sector')]");
    public static final By AREA_CHART_PATH = By.xpath("//path[contains(@class, 'recharts-area-area')]");
    public static final By BAR_CHART_BAR = By.xpath("//rect[contains(@class, 'recharts-bar-rectangle')]");

    // ================= Relative / Scoped Locators (to be used with container.findElements) =================
    // Filters (dropdown & toggles)
    public static final By REL_TIMEFRAME_DROPDOWN = By.xpath(".//select[contains(@data-testid, 'timeframe-selector')]");
    public static final By REL_LOSS_TOGGLE = By.xpath(".//button[contains(@data-testid,'loss-toggle')]");
    public static final By REL_GAIN_TOGGLE = By.xpath(".//button[contains(@data-testid,'gain-toggle')]");
    public static final By REL_ACTIVE_TOGGLE = By.xpath(".//button[contains(@class,'active') and contains(@data-testid,'-toggle')]");

    // Chart element shapes (scoped within a specific chart container)
    public static final By REL_AREA_PATH = By.xpath(".//path[contains(@class, 'recharts-area-area')]");
    public static final By REL_PIE_SEGMENT = By.xpath(".//path[contains(@class, 'recharts-pie-sector')]");
    public static final By REL_BAR_RECT = By.xpath(".//rect[contains(@class, 'recharts-bar-rectangle')]");
    public static final By REL_LINE_PATH = By.xpath(".//path[contains(@class, 'recharts-line')]");
    public static final By REL_COMPOSED_ANY = By.xpath(".//*[contains(@class, 'recharts-')]");
    public static final By REL_INTERACTIVE_CHART_ELEMENT = By.xpath(".//*[contains(@class,'recharts-') and @role='button']");
    public static final By REL_LEGEND_ITEM = By.xpath(".//div[@data-testid='legend']//li | .//ul[contains(@class,'legend')]//li");
    public static final By REL_TOTAL_AMOUNT = By.xpath(".//div[@data-testid='total-amount'] | .//div[contains(@class,'total')]");
    public static final By REL_SKELETON_ANY = By.xpath(".//*[@data-testid='skeleton-bar'] | .//*[@data-testid='skeleton-line'] | .//*[@data-testid='skeleton-pie']");

    // Data Validation Elements
    public static final By AMOUNT_FORMATTED = By.xpath("//*[contains(text(), '₹') and contains(@class, 'amount')]");
    public static final By PERCENTAGE_FORMATTED = By.xpath("//*[contains(text(), '%')]");
    public static final By DATE_FORMATTED = By.xpath("//*[@data-testid='formatted-date']");
    public static final By CURRENCY_SYMBOL = By.xpath("//*[contains(text(), '₹')]");

    // Navigation and Interaction Elements
    public static final By CLICKABLE_CHART_ELEMENT = By.xpath("//*[@role='button' and contains(@class, 'chart-element')]");
    public static final By HOVERABLE_ELEMENT = By.xpath("//*[@data-hover='true']");
    public static final By FOCUSABLE_ELEMENT = By.xpath("//*[@tabindex]");
}
