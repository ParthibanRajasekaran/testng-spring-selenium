package com.qa.pages;

import com.qa.annotation.LazyAutowired;
import com.qa.annotation.Page;
import com.qa.utils.AuthUtils;
import com.qa.utils.CipherUtils;
import com.qa.utils.SeleniumUtils;
import com.qa.utils.Waiters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Value;

@Page
public class HerokuappBasicAuthPage {

  private static final Logger log = LogManager.getLogger(HerokuappBasicAuthPage.class);

  @FindBy(how = How.XPATH, using = "//h3")
  private WebElement pageHeader;

  @FindBy(how = How.XPATH, using = "//div[@class='example']/child::p")
  private WebElement successMessage;

  @FindBy(how = How.XPATH, using = "/div[@id='page-footer']")
  private WebElement pageFooter;

  @FindBy(how = How.XPATH, using = "//div[@id='page-footer']//child::a")
  private WebElement pageFooterLink;


  @Value("${host.url}")
  private String hostUrl;

  @LazyAutowired
  private Waiters waiters;

  @LazyAutowired
  private AuthUtils authUtils;

  @LazyAutowired
  private CipherUtils cipherUtils;

  @LazyAutowired
  private SeleniumUtils seleniumUtils;

  public String generateEncodedURL() {
    return authUtils.protocol + ":" + "//" + authUtils.username + ":" + cipherUtils.decrypt(authUtils.password) + "@" + authUtils.host + "/basic_auth";
  }

  public final String generateURL() {
    return hostUrl + "/basic_auth";
  }

  public void verifyIfPageHeaderIsDisplayed() {
    waiters.waitUntilElementVisible(pageHeader);
    log.debug("Page header: {} is displayed", seleniumUtils.getElementText(pageHeader));
  }

  public void verifyIfSuccessMessageIsDisplayed() {
    seleniumUtils.assertElementPresent(successMessage);
    log.debug("Message: {} }is displayed on the landing page", seleniumUtils.getElementText(successMessage));
  }

  public void verifyIfPageFooterIsPresent() {
    waiters.waitUntilElementVisible(pageFooter);
    log.debug("Page footer contains the link: {}", seleniumUtils.getElementText(pageFooter));
  }
}
