package com.ea.SpringBasic.pages;


import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HomePage extends BasePage {

    private static Logger log = LogManager.getLogger(HomePage.class);

    @FindBy(how = How.LINK_TEXT, using = "Login")
    public WebElement lnkLogin;

    @FindBy(how = How.LINK_TEXT, using = "Employee List")
    public WebElement lnkEmployeeList;

    @Step("Click on the Login button")
    public LoginPage clickLogin() {
        lnkLogin.click();
        log.debug("Click the home page login");
        return new LoginPage();
    }

    public void ClickEmployeeList() {lnkEmployeeList.click();}

}
