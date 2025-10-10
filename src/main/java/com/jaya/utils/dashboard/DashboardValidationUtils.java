package com.jaya.utils.dashboard;

import org.testng.Assert;

import java.util.regex.Pattern;

/**
 * Utility class for validating dashboard-specific formats and values
 */
public class DashboardValidationUtils {
    
    private static final Pattern CURRENCY_PATTERN = Pattern.compile("^₹\\s?([0-9]{1,3}(,[0-9]{3})*|[0-9]+)(\\.[0-9]{1,2})?$");
    private static final Pattern PERCENTAGE_PATTERN = Pattern.compile("^-?\\d{1,3}(\\.\\d+)?%$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+\\d{1,2}$");
    
    /**
     * Validate currency format with ₹ symbol
     */
    public static void assertCurrencyFormat(String value) {
        Assert.assertNotNull(value, "Currency value should not be null");
        Assert.assertTrue(value.contains("₹"), "Currency should contain ₹ symbol: " + value);
        // Remove common formatting issues before regex
        String normalized = value.replaceAll("\\s+", " ").trim();
        Assert.assertTrue(CURRENCY_PATTERN.matcher(normalized).find(), 
            "Invalid currency format: " + value);
    }
    
    /**
     * Validate percentage format
     */
    public static void assertPercentageFormat(String value) {
        Assert.assertNotNull(value, "Percentage value should not be null");
        Assert.assertTrue(value.contains("%"), "Percentage should contain % symbol: " + value);
        String normalized = value.replaceAll("\\s+", "").trim();
        Assert.assertTrue(PERCENTAGE_PATTERN.matcher(normalized).find(), 
            "Invalid percentage format: " + value);
    }
    
    /**
     * Validate date format for chart axis (e.g., Jan 15)
     */
    public static void assertDateFormat(String value) {
        Assert.assertNotNull(value, "Date value should not be null");
        String normalized = value.trim();
        Assert.assertTrue(DATE_PATTERN.matcher(normalized).find(), 
            "Invalid date format: " + value);
    }
    
    /**
     * Validate value is numeric (integer or decimal)
     */
    public static void assertNumericValue(String value) {
        Assert.assertNotNull(value, "Numeric value should not be null");
        String normalized = value.replaceAll("[₹,%\\s]", "").trim();
        Assert.assertTrue(normalized.matches("-?\\d+(\\.\\d+)?"), 
            "Value should be numeric: " + value);
    }
    
    /**
     * Validate trend indicator is one of accepted values (up/down/flat)
     */
    public static void assertTrendIndicator(String trend) {
        Assert.assertTrue(trend.equals("up") || trend.equals("down") || trend.equals("flat"), 
            "Invalid trend indicator: " + trend);
    }
}
