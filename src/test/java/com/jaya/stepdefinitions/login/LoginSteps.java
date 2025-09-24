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
        driver= DriverFactory.getInstance().getDriver();
        driver.get("https://jjayaprakash.netlify.app/");
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
    public void the_user_should_be_redirected_to_the_home_page(String exceptedMessage) {
        loginPage.verifyHomePage();
    }

//    @Then("an error message should be displayed")
//    public void an_error_message_should_be_displayed(String expectedMessage) {
//        loginPage.verifyInvalidLoginText();
//    }
}