//package com.jaya.stepdefinitions.expenses;
//
//package com.jaya.stepdefinitions;
//
//import io.cucumber.java.en.*;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.By;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.interactions.Actions;
//import org.junit.Assert;
//import java.time.Duration;
//import java.util.List;
//
//public class CashFlowSteps {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//    private Actions actions;
//
//    public CashFlowSteps() {
//        this.driver = DriverManager.getDriver();
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        this.actions = new Actions(driver);
//    }
//
//    @Given("I am on the cash flow page")
//    public void i_am_on_the_cash_flow_page() {
//        driver.get(ConfigReader.getProperty("base.url") + "/cashflow");
//    }
//
//    @Given("the cash flow data has loaded")
//    public void the_cash_flow_data_has_loaded() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("cash-flow-chart")));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(
//                By.className("loading-spinner")));
//    }
//
//    @When("the cash flow page initializes")
//    public void the_cash_flow_page_initializes() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("cash-flow-container")));
//    }
//
//    @Then("I should see a bar chart displaying cash flow data")
//    public void i_should_see_a_bar_chart_displaying_cash_flow_data() {
//        WebElement barChart = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'recharts-wrapper')]")));
//        Assert.assertTrue("Bar chart should be visible", barChart.isDisplayed());
//
//        List<WebElement> bars = driver.findElements(
//                By.xpath("//rect[contains(@class, 'recharts-bar')]"));
//        Assert.assertTrue("Should have bar elements", bars.size() > 0);
//    }
//
//    @Then("I should see positive values for income \\(gain)")
//    public void i_should_see_positive_values_for_income_gain() {
//        List<WebElement> positiveBars = driver.findElements(
//                By.xpath("//rect[contains(@class, 'recharts-bar') and @height > 0]"));
//        Assert.assertTrue("Should have positive bars for income", positiveBars.size() > 0);
//    }
//
//    @Then("I should see negative values for expenses \\(loss)")
//    public void i_should_see_negative_values_for_expenses_loss() {
//        List<WebElement> negativeBars = driver.findElements(
//                By.xpath("//rect[contains(@class, 'recharts-bar') and @y > 0]"));
//        Assert.assertTrue("Should have negative bars for expenses", negativeBars.size() > 0);
//    }
//
//    @Then("I should see a reference line at zero")
//    public void i_should_see_a_reference_line_at_zero() {
//        WebElement referenceLine = driver.findElement(
//                By.xpath("//line[contains(@class, 'recharts-reference-line')]"));
//        Assert.assertTrue("Reference line should be visible", referenceLine.isDisplayed());
//    }
//
//    @Then("I should see proper axis labels and formatting")
//    public void i_should_see_proper_axis_labels_and_formatting() {
//        List<WebElement> xAxisLabels = driver.findElements(
//                By.xpath("//g[contains(@class, 'recharts-xAxis')]//text"));
//        List<WebElement> yAxisLabels = driver.findElements(
//                By.xpath("//g[contains(@class, 'recharts-yAxis')]//text"));
//
//        Assert.assertTrue("Should have X-axis labels", xAxisLabels.size() > 0);
//        Assert.assertTrue("Should have Y-axis labels", yAxisLabels.size() > 0);
//
//        // Verify Y-axis shows currency formatting
//        boolean hasCurrencyFormat = yAxisLabels.stream()
//                .anyMatch(label -> label.getText().contains("₹") || label.getText().matches("\\d+"));
//        Assert.assertTrue("Y-axis should show currency formatting", hasCurrencyFormat);
//    }
//
//    @Given("the cash flow chart is displayed")
//    public void the_cash_flow_chart_is_displayed() {
//        WebElement chart = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("cash-flow-chart")));
//        Assert.assertTrue("Cash flow chart should be displayed", chart.isDisplayed());
//    }
//
//    @When("I hover over a bar in the chart")
//    public void i_hover_over_a_bar_in_the_chart() {
//        WebElement bar = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//rect[contains(@class, 'recharts-bar')]")));
//        actions.moveToElement(bar).perform();
//    }
//
//    @Then("I should see a tooltip with detailed information")
//    public void i_should_see_a_tooltip_with_detailed_information() {
//        WebElement tooltip = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("recharts-tooltip-wrapper")));
//        Assert.assertTrue("Tooltip should be visible", tooltip.isDisplayed());
//    }
//
//    @Then("the tooltip should show the date")
//    public void the_tooltip_should_show_the_date() {
//        WebElement tooltip = driver.findElement(By.className("recharts-tooltip-wrapper"));
//        String tooltipText = tooltip.getText();
//        Assert.assertTrue("Tooltip should contain date",
//                tooltipText.matches(".*\\d{1,2}[/-]\\d{1,2}[/-]\\d{4}.*") ||
//                        tooltipText.matches(".*\\b(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\b.*"));
//    }
//
//    @Then("the tooltip should show the amount formatted as currency")
//    public void the_tooltip_should_show_the_amount_formatted_as_currency() {
//        WebElement tooltip = driver.findElement(By.className("recharts-tooltip-wrapper"));
//        String tooltipText = tooltip.getText();
//        Assert.assertTrue("Tooltip should show currency formatted amount",
//                tooltipText.contains("₹") && tooltipText.matches(".*₹[\\d,]+.*"));
//    }
//
//    @Then("the tooltip should indicate if it's income or expense")
//    public void the_tooltip_should_indicate_if_its_income_or_expense() {
//        WebElement tooltip = driver.findElement(By.className("recharts-tooltip-wrapper"));
//        String tooltipText = tooltip.getText().toLowerCase();
//        Assert.assertTrue("Tooltip should indicate transaction type",
//                tooltipText.contains("income") || tooltipText.contains("expense") ||
//                        tooltipText.contains("gain") || tooltipText.contains("loss"));
//    }
//
//    @When("I click on a specific bar")
//    public void i_click_on_a_specific_bar() {
//        WebElement bar = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//rect[contains(@class, 'recharts-bar')]")));
//        bar.click();
//    }
//
//    @Then("the bar should be highlighted or selected")
//    public void the_bar_should_be_highlighted_or_selected() {
//        WebElement selectedBar = driver.findElement(
//                By.xpath("//rect[contains(@class, 'recharts-bar') and contains(@class, 'selected')]"));
//        Assert.assertTrue("Bar should be highlighted", selectedBar.isDisplayed());
//    }
//
//    @Then("I should see additional details for that time period")
//    public void i_should_see_additional_details_for_that_time_period() {
//        WebElement detailsPanel = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("cash-flow-details")));
//        Assert.assertTrue("Details panel should be visible", detailsPanel.isDisplayed());
//    }
//
//    @Then("I should be able to drill down into the transactions")
//    public void i_should_be_able_to_drill_down_into_the_transactions() {
//        WebElement drillDownButton = driver.findElement(
//                By.xpath("//button[contains(text(), 'View Transactions') or contains(text(), 'Details')]"));
//        Assert.assertTrue("Drill down option should be available", drillDownButton.isDisplayed());
//    }
//
//    @When("I change the time period filter")
//    public void i_change_the_time_period_filter() {
//        WebElement timePeriodFilter = wait.until(ExpectedConditions.elementToBeClickable(
//                By.className("time-period-filter")));
//        timePeriodFilter.click();
//
//        WebElement option = driver.findElement(By.xpath("//option[text()='Last 3 Months']"));
//        option.click();
//    }
//
//    @Then("the chart should update to show data for the selected period")
//    public void the_chart_should_update_to_show_data_for_the_selected_period() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'recharts-wrapper')]")));
//
//        List<WebElement> bars = driver.findElements(
//                By.xpath("//rect[contains(@class, 'recharts-bar')]"));
//        Assert.assertTrue("Chart should have updated with new data", bars.size() > 0);
//    }
//
//    @Then("the x-axis should adjust to show appropriate time labels")
//    public void the_x_axis_should_adjust_to_show_appropriate_time_labels() {
//        List<WebElement> xAxisLabels = driver.findElements(
//                By.xpath("//g[contains(@class, 'recharts-xAxis')]//text"));
//        Assert.assertTrue("X-axis should have updated labels", xAxisLabels.size() > 0);
//    }
//
//    @Then("the y-axis should scale to fit the new data range")
//    public void the_y_axis_should_scale_to_fit_the_new_data_range() {
//        List<WebElement> yAxisLabels = driver.findElements(
//                By.xpath("//g[contains(@class, 'recharts-yAxis')]//text"));
//        Assert.assertTrue("Y-axis should have updated scale", yAxisLabels.size() > 0);
//    }
//
//    @Given("I am viewing the cash flow on different screen sizes")
//    public void i_am_viewing_the_cash_flow_on_different_screen_sizes() {
//        // This would typically involve changing browser window size
//        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
//    }
//
//    @When("the screen width changes")
//    public void the_screen_width_changes() {
//        driver.manage().window().setSize(new org.openqa.selenium.Dimension(480, 800));
//    }
//
//    @Then("the chart should maintain readability")
//    public void the_chart_should_maintain_readability() {
//        WebElement chart = driver.findElement(By.className("cash-flow-chart"));
//        Assert.assertTrue("Chart should remain visible", chart.isDisplayed());
//
//        // Verify chart dimensions are appropriate for screen size
//        int chartWidth = chart.getSize().getWidth();
//        int screenWidth = driver.manage().window().getSize().getWidth();
//        Assert.assertTrue("Chart should fit screen width", chartWidth <= screenWidth);
//    }
//
//    @Then("the bars should remain proportional")
//    public void the_bars_should_remain_proportional() {
//        List<WebElement> bars = driver.findElements(
//                By.xpath("//rect[contains(@class, 'recharts-bar')]"));
//        Assert.assertTrue("Bars should be visible", bars.size() > 0);
//
//        for (WebElement bar : bars) {
//            Assert.assertTrue("Each bar should be visible", bar.isDisplayed());
//        }
//    }
//
//    @Then("the axis labels should adjust appropriately")
//    public void the_axis_labels_should_adjust_appropriately() {
//        List<WebElement> axisLabels = driver.findElements(
//                By.xpath("//g[contains(@class, 'recharts-xAxis') or contains(@class, 'recharts-yAxis')]//text"));
//
//        for (WebElement label : axisLabels) {
//            Assert.assertTrue("Axis labels should be readable", label.isDisplayed());
//        }
//    }
//
//    @When("I apply filters for specific categories or payment methods")
//    public void i_apply_filters_for_specific_categories_or_payment_methods() {
//        WebElement categoryFilter = wait.until(ExpectedConditions.elementToBeClickable(
//                By.className("category-filter")));
//        categoryFilter.click();
//
//        WebElement foodCategory = driver.findElement(
//                By.xpath("//option[text()='Food' or text()='Groceries']"));
//        foodCategory.click();
//    }
//
//    @Then("the chart should update to show only filtered data")
//    public void the_chart_should_update_to_show_only_filtered_data() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//div[contains(@class, 'recharts-wrapper')]")));
//
//        WebElement chart = driver.findElement(By.className("cash-flow-chart"));
//        Assert.assertTrue("Filtered chart should be visible", chart.isDisplayed());
//    }
//
//    @Then("the reference line should remain at zero")
//    public void the_reference_line_should_remain_at_zero() {
//        WebElement referenceLine = driver.findElement(
//                By.xpath("//line[contains(@class, 'recharts-reference-line')]"));
//        Assert.assertTrue("Reference line should remain visible", referenceLine.isDisplayed());
//    }
//
//    @Then("the totals should reflect the filtered amounts")
//    public void the_totals_should_reflect_the_filtered_amounts() {
//        WebElement totalElement = driver.findElement(By.className("cash-flow-total"));
//        String totalText = totalElement.getText();
//        Assert.assertTrue("Total should show filtered amount",
//                totalText.contains("₹") && !totalText.equals("₹0"));
//    }
//
//    @Given("there is an error loading cash flow data")
//    public void there_is_an_error_loading_cash_flow_data() {
//        // Simulate network error or invalid data response
//        driver.executeScript("window.localStorage.setItem('simulateError', 'true');");
//        driver.navigate().refresh();
//    }
//
//    @When("the cash flow component tries to render")
//    public void the_cash_flow_component_tries_to_render() {
//        wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("cash-flow-container")));
//    }
//
//    @Then("I should see an appropriate error message")
//    public void i_should_see_an_appropriate_error_message() {
//        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.className("error-message")));
//        Assert.assertTrue("Error message should be visible", errorMessage.isDisplayed());
//        Assert.assertTrue("Error message should be meaningful",
//                errorMessage.getText().contains("error") || errorMessage.getText().contains("failed"));
//    }
//
//    @Then("I should have an option to retry loading the data")
//    public void i_should_have_an_option_to_retry_loading_the_data() {
//        WebElement retryButton = driver.findElement(
//                By.xpath("//button[contains(text(), 'Retry') or contains(text(), 'Try Again')]"));
//        Assert.assertTrue("Retry button should be available", retryButton.isDisplayed());
//    }
//
//    @Then("the chart area should show a placeholder or skeleton")
//    public void the_chart_area_should_show_a_placeholder_or_skeleton() {
//        WebElement placeholder = driver.findElement(
//                By.xpath("//div[contains(@class, 'skeleton') or contains(@class, 'placeholder')]"));
//        Assert.assertTrue("Placeholder should be visible", placeholder.isDisplayed());
//    }
//}