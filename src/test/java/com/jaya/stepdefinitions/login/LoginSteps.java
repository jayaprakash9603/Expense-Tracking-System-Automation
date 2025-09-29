package com.jaya.stepdefinitions.login;

import com.jaya.factory.DriverFactory;


import com.jaya.pages.login.LoginPage;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

public class LoginSteps  {

    private WebDriver driver;
    private LoginPage loginPage;



    @Given("the user is on the Login page")
    public void the_user_is_on_the_login_page() {
        driver=DriverFactory.getInstance().getDriver();
        driver.get("http://localhost:3000/");
        loginPage = new LoginPage();
    }

    @When("the user enters username {string}")
    public void the_user_enters_username(String username) {
        loginPage.enterUsername(username);
    }

    @When("the user enters password {string}")
    public void the_user_enters_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("clicks on the Login button")
    public void clicks_on_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("the user should be redirected to the Home page")
    public void the_user_should_be_redirected_to_the_home_page() {
        loginPage.verifyHomePage();
    }

    @Then("click on logout and confirm")
    public void click_on_logout_and_confirm() {
        loginPage.clickLogoutButton();
        loginPage.confirmLogout();
    }

    @Then("{string} error message should be displayed")
    public void errorMessageShouldBeDisplayed(String errorMessage) {
        loginPage.assertInvalidLoginMessage(errorMessage);
    }

    // Optional: step to assert fragment only (useful if full text varies slightly)
    @Then("an error message containing {string} should be displayed")
    public void errorMessageContainingShouldBeDisplayed(String fragment) {
        loginPage.assertLoginMessageContains(fragment);
    }
}