package com.qa.pages;

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
public class HerokuappCheckboxPage {

  private static final Logger log = LogManager.getLogger(HerokuappCheckboxPage.class);

  @FindBy(how = How.XPATH, using = "//h3")
  private WebElement pageHeader;

  @FindBy(how = How.XPATH, using = "//form[@id='checkboxes']/input[1]")
  private WebElement checkbox1;

  @FindBy(how = How.XPATH, using = "//form[@id='checkboxes']/input[2]")
  private WebElement checkbox2;

  @LazyAutowired
  private Waiters waiters;

  @LazyAutowired
  private SeleniumUtils seleniumUtils;

  public void verifyIfPageHeaderIsDisplayed() {
    waiters.waitUntilElementVisible(pageHeader);
    log.debug("Page header:" + seleniumUtils.getElementText(pageHeader) + " is displayed");
  }

  public void verifyCheckboxLandingPage() {
    verifyIfPageHeaderIsDisplayed();
    waiters.waitUntilElementVisible(checkbox1);
    seleniumUtils.assertElementPresent(checkbox2);
    log.debug("Checkboxes displayed as expected");
  }

  public void checkOnCheckBox1() {
    if (!checkbox1.isSelected()) {
      seleniumUtils.clickElement(checkbox1);
    }
    log.debug("Checkbox 1 is checked on");
  }

  public void checkOnCheckBox2() {
    if (!checkbox2.isSelected()) {
      seleniumUtils.clickElement(checkbox2);
    }
    log.debug("Checkbox 2 is checked on");
  }

  public void unCheckCheckbox1() {
    if (checkbox1.isSelected()) {
      seleniumUtils.clickElement(checkbox1);
    }
    log.debug("Checkbox 1 is unchecked");
  }

  public void unCheckCheckbox2() {
    if (checkbox2.isSelected()) {
      seleniumUtils.clickElement(checkbox2);
    }
    log.debug("Checkbox 2 is unchecked");
  }
}
