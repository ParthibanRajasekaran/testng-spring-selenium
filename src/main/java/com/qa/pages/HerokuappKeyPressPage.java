package com.qa.pages;

import static org.testng.Assert.assertTrue;

import com.qa.annotation.LazyAutowired;
import com.qa.annotation.Page;
import com.qa.utils.SeleniumUtils;
import com.qa.utils.Waiters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

@Page
public class HerokuappKeyPressPage {

  private static final Logger log = LogManager.getLogger(HerokuappKeyPressPage.class);

  @FindBy(how = How.XPATH, using = "//h3")
  private WebElement pageHeader;

  @FindBy(how = How.XPATH, using = "//input[@id='target']")
  private WebElement inputField;

  @FindBy(how = How.XPATH, using = "//form/following-sibling::p[@id='result']")
  private WebElement result;

  @LazyAutowired
  private Waiters waiters;

  @LazyAutowired
  private SeleniumUtils seleniumUtils;

  public void verifyIfPageHeaderIsDisplayed() {
    waiters.waitUntilElementVisible(pageHeader);
    log.debug("Page header:" + seleniumUtils.getElementText(pageHeader) + " is displayed");
  }

  public String getResult() {
    return seleniumUtils.getElementText(result);
  }

  public void sendKeyAndGetResult(String Key, String result) {
    inputField.sendKeys(Key);
    log.debug(String.format("Result is %s", getResult()));
    assertTrue(getResult().contains(result));
  }
}
