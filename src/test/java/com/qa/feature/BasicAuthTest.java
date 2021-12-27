package com.qa.feature;

import static com.qa.utils.Groups.AUTH;

import com.qa.annotation.LazyAutowired;
import com.qa.common.BaseTest;
import com.qa.pages.HerokuappBasicAuthPage;
import com.qa.utils.AuthUtils;
import com.qa.utils.CipherUtils;
import com.qa.utils.SeleniumUtils;
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
@Epic("Herokuapp")
@Feature("Herokuapp Application")
@Story("Herokuapp Basic Authorization")
public class BasicAuthTest extends BaseTest {

  @LazyAutowired
  private WebDriver driver;

  @LazyAutowired
  private SeleniumUtils seleniumUtils;

  @LazyAutowired
  private AuthUtils authUtils;

  @LazyAutowired
  private CipherUtils cipherUtils;

  @LazyAutowired
  private HerokuappBasicAuthPage herokuappBasicAuthPage;

  @BeforeMethod(alwaysRun = true)
  public void setup() {
    herokuappBasicAuthPage = new HerokuappBasicAuthPage();
  }

  @Test(groups = {AUTH})
  @Description(
      "This is to validate the authorization via encoded URL without chrome dev tool protocol")
  public void navigateToEncodedURL() {
    final String url = herokuappBasicAuthPage.generateEncodedURL();
    driver.navigate().to(url);
    herokuappBasicAuthPage.verifyIfPageHeaderIsDisplayed();
    herokuappBasicAuthPage.verifyIfSuccessMessageIsDisplayed();
    herokuappBasicAuthPage.verifyIfPageFooterIsPresent();
  }

  @Test(groups = {AUTH})
  @Description("This is to validate the authorization via Bi-Directional protocol")
  public void basicAuthUsingBiDiApi() {
    seleniumUtils.handleBasicAuthViaBiDiApi(
        hostUrl,
        authUtils.username,
        cipherUtils.decrypt(authUtils.password));
    driver.navigate().to(herokuappBasicAuthPage.generateURL());
    herokuappBasicAuthPage.verifyIfPageHeaderIsDisplayed();
    herokuappBasicAuthPage.verifyIfSuccessMessageIsDisplayed();
    herokuappBasicAuthPage.verifyIfPageFooterIsPresent();
  }

  @Test(groups = {AUTH})
  @Description("This is to validate the authorization via Chrome Dev Tools protocol")
  public void basicAuthUsingChromeDevTools() {
    seleniumUtils.handleBasicAuthViaChromeDevTools(
        authUtils.username,
        cipherUtils.decrypt(authUtils.password));
    driver.navigate().to(herokuappBasicAuthPage.generateURL());
    herokuappBasicAuthPage.verifyIfPageHeaderIsDisplayed();
    herokuappBasicAuthPage.verifyIfSuccessMessageIsDisplayed();
    herokuappBasicAuthPage.verifyIfPageFooterIsPresent();
  }
}
