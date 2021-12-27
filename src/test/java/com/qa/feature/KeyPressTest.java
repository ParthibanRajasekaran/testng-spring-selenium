package com.qa.feature;

import com.qa.annotation.LazyAutowired;
import com.qa.common.BaseTest;
import com.qa.pages.HerokuappCheckboxPage;
import com.qa.pages.HerokuappKeyPressPage;
import com.qa.pages.HerokuappLandingPage;
import com.qa.utils.SampleDataProvider;
import com.qa.utils.SeleniumUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest
@Epic("Herokuapp")
@Feature("Herokuapp Application")
@Story("Herokuapp Key Press")
public class KeyPressTest extends BaseTest {

  @LazyAutowired
  private HerokuappLandingPage herokuappLandingPage;

  @LazyAutowired
  private HerokuappKeyPressPage herokuappKeyPressPage;

  @LazyAutowired
  private SeleniumUtils seleniumUtils;

  @LazyAutowired
  private WebDriver driver;

  @Test(
      dataProvider = "testData",
      dataProviderClass = SampleDataProvider.class)
  public void keyPressTest(String Key, String Result) {
    driver.navigate().to(hostUrl);
    herokuappLandingPage.verifyIfPageHeaderIsDisplayed();
    herokuappLandingPage.navigateToSubPage("Key Presses");
    herokuappKeyPressPage.verifyIfPageHeaderIsDisplayed();
    herokuappKeyPressPage.sendKeyAndGetResult(Key, Result);
  }
}
