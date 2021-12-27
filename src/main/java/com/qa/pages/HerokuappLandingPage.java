package com.qa.pages;

import com.qa.annotation.LazyAutowired;
import com.qa.annotation.Page;
import com.qa.utils.SeleniumUtils;
import com.qa.utils.Waiters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

@Page
public class HerokuappLandingPage {

  private static final Logger log = LogManager.getLogger(HerokuappKeyPressPage.class);

  @FindBy(how = How.XPATH, using = "//h1")
  private WebElement pageHeader;

  @FindBy(how = How.XPATH, using = "//h2")
  private WebElement pageSubHeader;

  @LazyAutowired
  private Waiters waiters;

  @LazyAutowired
  private SeleniumUtils seleniumUtils;

  @LazyAutowired
  private WebDriver driver;

  public void navigateToSubPage(String subPageName) {
    final String subPageLink = "//li/a[contains(text(),'" + subPageName + "')]";
    final WebElement element = driver.findElement(By.xpath(subPageLink));
    seleniumUtils.assertElementPresent(element);
    seleniumUtils.clickElement(element);
  }

  public void verifyIfPageHeaderIsDisplayed() {
    waiters.waitUntilElementVisible(pageHeader);
    log.debug("Page header:" + seleniumUtils.getElementText(pageHeader) + " is displayed");
    waiters.waitUntilElementVisible(pageSubHeader);
    log.debug("Sub header:" + seleniumUtils.getElementText(pageSubHeader) + " is displayed");
  }
}
