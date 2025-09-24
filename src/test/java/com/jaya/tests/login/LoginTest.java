package com.jaya.tests.login;

import com.jaya.pages.login.LoginPage;
import com.jaya.tests.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private final String username="jaya@gmail.com";
    private final String password="123456";
    private final String invalidUsername="invalid@gmail.com";
    private final String invalidPassword="123456";

    @Test(priority = 2)
    public void LoginTest()
    {
        LoginPage loginPage=new LoginPage();
        loginPage.login(username,password);
    }

    @Test(priority = 1)
    public void invalidLoginTest()
    {
        LoginPage loginPage=new LoginPage();
        loginPage.invalidLogin(invalidUsername,invalidPassword);
    }
}
