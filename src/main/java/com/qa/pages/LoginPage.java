package com.qa.pages;

import com.qa.annotation.Page;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

@Page
public class LoginPage extends BasePage {

    private static Logger log = LogManager.getLogger(LoginPage.class);

    @FindBy(how = How.NAME, using = "UserName")
    public WebElement txtUserName;
    @FindBy(how = How.NAME, using = "Password")
    public WebElement txtPassword;
    @FindBy(how = How.CSS, using = ".btn-default")
    public WebElement btnLogin;

    @Step("Enter the Login credentials for username: {}")
    public void Login(String userName, String password)
    {
        txtUserName.sendKeys(userName);
        txtPassword.sendKeys(password);
        log.debug("UserName and password: " + userName + "---" + password);
    }

    @Step("Click on the Login button in the home page")
    public HomePage clickLogin() {
        btnLogin.click();
        log.debug("Click login from login page");
        return new HomePage();
    }

}
