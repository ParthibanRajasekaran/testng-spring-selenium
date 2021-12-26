package com.qa.pages;

import com.qa.annotation.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Page
public class MainPage extends BasePage {

    private static Logger log = LogManager.getLogger(MainPage.class);

    public MainPage() {
        log.info("In Main Page");
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
