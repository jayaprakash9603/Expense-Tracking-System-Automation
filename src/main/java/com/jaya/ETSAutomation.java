package com.jaya;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ETSAutomation {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Go to JIRA page (assume already logged in)
		driver.get("https://jira.tools.telstra.com/projects/O2AB?jwupdated=58790&selectedItem=com.thed.zephyr.je:zephyr-tests-page#test-summary-tab");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		// Click on 'Create' button
		WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("createGlobalItem")));
		createButton.click();

		// Wait for popup to appear
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("issue-create-dialog")));

		// Select Issue Type as 'Test'
		WebElement issueTypeDropdown = driver.findElement(By.id("issuetype-field"));
		issueTypeDropdown.clear();
		issueTypeDropdown.sendKeys("Test");

		// Enter Summary
		WebElement summaryField = driver.findElement(By.id("summary"));
		summaryField.sendKeys("TC_01_Verify_Tecams funtionality");

		// Select Test Phase (SIT)
		WebElement testPhaseDropdown = driver.findElement(By.id("customfield_XXXXX")); // <-- Replace with actual ID
		Select selectTestPhase = new Select(testPhaseDropdown);
		selectTestPhase.selectByVisibleText("SIT");

		// Select Test Type (Manual)
		WebElement testTypeDropdown = driver.findElement(By.id("customfield_YYYYY")); // <-- Replace with actual ID
		Select selectTestType = new Select(testTypeDropdown);
		selectTestType.selectByVisibleText("Manual");

		// Select Execution Cycle (Progression)
		WebElement executionCycleDropdown = driver.findElement(By.id("customfield_ZZZZZ")); // <-- Replace with actual ID
		Select selectExecutionCycle = new Select(executionCycleDropdown);
		selectExecutionCycle.selectByVisibleText("Progression");

		// Enter Team name and select Dublin Team
		WebElement teamField = driver.findElement(By.id("customfield_AAAAA")); // <-- Replace with actual ID
		teamField.sendKeys("dublin");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'dublin')]"))).click();

		// Click Create
		WebElement submitButton = driver.findElement(By.id("create-issue-submit"));
		submitButton.click();

		// Wait for issue to be created (Optionally check confirmation)

		// Close driver
		// driver.quit();
	}
}
