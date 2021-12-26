package com.ea.SpringBasic.pages;


import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HomePage extends BasePage {

    @FindBy(how = How.LINK_TEXT, using = "Login")
    public WebElement lnkLogin;

    @FindBy(how = How.LINK_TEXT, using = "Employee List")
    public WebElement lnkEmployeeList;

    @Step("Click on the Login button")
    public LoginPage clickLogin() {
        lnkLogin.click();
        System.out.println("Click the home page login");
        return new LoginPage();
    }

    public void ClickEmployeeList() {lnkEmployeeList.click();}

}
