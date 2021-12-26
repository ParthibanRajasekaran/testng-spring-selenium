package com.qa.common;

import com.qa.report.SeleniumListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({SeleniumListener.class})
public class BaseTest extends AbstractTestNGSpringContextTests {

  private static Logger log = LogManager.getLogger(BaseTest.class);

  @Autowired
  private WebDriver driver;

//  @BeforeSuite(alwaysRun = true)
  @BeforeMethod(alwaysRun = true)
  @Override
  protected void springTestContextPrepareTestInstance() throws Exception {
    super.springTestContextPrepareTestInstance();
  }

  @AfterMethod(alwaysRun = true)
  protected void tearDownBrowser() {
    log.info("Closing Browser");
    driver.quit();
    }

  @AfterSuite(alwaysRun = true)
  protected void tearDownAllBrowserSessions() {
  if(driver != null) {
    log.info("Quitting all browser sessions");
    driver.quit();
    }
  }
}
