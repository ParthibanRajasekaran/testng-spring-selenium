package com.ea.SpringBasic.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@ConditionalOnProperty(name = "env", havingValue = "qa")
public class MainPage extends BasePage {

    public MainPage() {
        System.out.println("In Main Page");
    }

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private HomePage homePage;

    public void performLogin() {
        homePage.clickLogin();
        loginPage.Login("admin", "adminpassword23432");
        loginPage.clickLogin();
    }
}
