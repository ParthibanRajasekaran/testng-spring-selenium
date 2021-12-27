package com.qa.feature;

import com.qa.annotation.LazyAutowired;
import com.qa.common.BaseTest;
import com.qa.pages.HerokuappCheckboxPage;
import com.qa.pages.HerokuappLandingPage;
import com.qa.utils.SeleniumUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest
@Epic("Herokuapp")
@Feature("Herokuapp Application")
@Story("Herokuapp checkbox")
public class CheckBoxTest extends BaseTest {

  @LazyAutowired
  private HerokuappLandingPage herokuappLandingPage;

  @LazyAutowired
  private HerokuappCheckboxPage herokuappCheckboxPage;

  @LazyAutowired
  private SeleniumUtils seleniumUtils;

  @LazyAutowired
  private WebDriver driver;

  @Test
  @Severity(SeverityLevel.MINOR)
  @Description("This is a test for handling checkboxes")
  public void checkboxTest() {
    driver.navigate().to(hostUrl);
    herokuappLandingPage.verifyIfPageHeaderIsDisplayed();
    herokuappLandingPage.navigateToSubPage("Checkboxes");
    herokuappCheckboxPage.verifyCheckboxLandingPage();
    herokuappCheckboxPage.checkOnCheckBox1();
    herokuappCheckboxPage.unCheckCheckbox1();
    herokuappCheckboxPage.unCheckCheckbox2();
    herokuappCheckboxPage.checkOnCheckBox2();
  }
}
