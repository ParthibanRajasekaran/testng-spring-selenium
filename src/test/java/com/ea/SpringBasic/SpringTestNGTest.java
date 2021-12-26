package com.ea.SpringBasic;

import com.ea.SpringBasic.pages.MainPage;
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
@Epic("Smoke Epic")
@Story("Smoke Story")
@Feature("Smoke Feature")
public class SpringTestNGTest extends BaseTest {

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

  @Test(description = "This testng test is to validate login")
  @Description("This testng test is to validate login")
  public void testLogin(){
    mainPage.performLogin();
  }

}
