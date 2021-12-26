package com.qa.feature;

import com.qa.common.BaseTest;
import com.qa.pages.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest
@Epic("Regression Epic")
@Story("Regression Story")
@Feature("Regression Feature")
public class SpringTestNGTestNew extends BaseTest {

  @Autowired
  private WebDriver driver;

  @Value("${app.url}")
  private String apprUrl;

  @Autowired
  private MainPage mainPage;

  @BeforeMethod
  protected void setupWebDriver(){
  driver.navigate().to(apprUrl);
  }

  @Test(description = "This is a new testng test is to validate login")
  @Description("This is a new testng test is to validate login")
  public void testLogin(){
    mainPage.performLogin();
  }
}
