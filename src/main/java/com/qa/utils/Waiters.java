package com.qa.utils;

import static org.awaitility.Awaitility.await;

import com.qa.annotation.LazyAutowired;
import com.qa.annotation.Utils;
import com.qa.config.DriverWaitConfig;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Utils
public class Waiters {

  private static Logger log = LogManager.getLogger(Waiters.class);

  @LazyAutowired
  private DriverWaitConfig driverWaitConfig;

  @LazyAutowired
  private WebDriver driver;

  @LazyAutowired
  private Random random;

  /**
   * Wait until the element is displayed - waits on a polling basis until timeout.
   *
   * @param element a valid WebElement
   */
  @Step
  public void waitUntilElementVisible(final WebElement element) {
    log.trace("Waiting for {} to be visible", element);
    driverWaitConfig.webDriverWait(driver).until(ExpectedConditions.visibilityOf(element));
  }

  /**
   * Wait until the element is not displayed anymore - waits on a polling basis until timeout.
   *
   * @param element a valid WebElement
   */
  @Step
  public void waitUntilElementInvisible(final WebElement element) {
    log.debug("Waiting for {} to be invisible", element);
    driverWaitConfig.webDriverWait(driver).until(ExpectedConditions.invisibilityOf(element));
  }

  /**
   * Wait until the element containing the text is displayed - waits on a polling basis until
   * timeout.
   *
   * @param element a valid WebElement
   * @param text Not null text we expect to be present on the webelement
   */
  @Step
  public void waitUntilTextWillBePresent(final WebElement element, final String text) {
    log.debug("Waiting for text {} to be present", text);
    driverWaitConfig.webDriverWait(driver).until(ExpectedConditions.textToBePresentInElementValue(element, text));
  }

  /**
   * Wait until the element is clickable - waits on a polling basis until timeout.
   *
   * @param element a valid WebElement
   */
  @Step
  public void waitUntilElementClickable(final WebElement element) {
    log.trace("Waiting for element {} to be clickable", element);
    driverWaitConfig.webDriverWait(driver).until(ExpectedConditions.elementToBeClickable(element));
  }

  /**
   * Wait until the element containing the value is displayed - waits on a polling basis until
   * timeout.
   *
   * @param element a valid WebElement
   * @param text Text for which we will search
   */
  @Step
  public void waitUntilValueWillBePresentInElement(final WebElement element, final String text) {
    log.trace("Waiting for text {} to be present in {}", text, element);
    driverWaitConfig.webDriverWait(driver).until(ExpectedConditions.textToBePresentInElementValue(element, text));
  }

  /**
   * Wait until the particular element is reloaded
   *
   * @param element a valid WebElement
   * @param timeout timeout limit in seconds
   */
  public void waitUntilElementGoesStale(final WebElement element, int timeout) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    try {
      wait.pollingEvery(Duration.ofSeconds(5)).until(ExpectedConditions.stalenessOf(element));
      log.info("Webelement {} reloaded successfully", element);
    } catch (TimeoutException e) {
      log.error(String.format("Page is not refreshed in %d seconds", timeout));
    }
  }

  /**
   * Wait until the element is displayed with awaitility with polling every 5 seconds
   *
   * @param element A valid WebElement
   * @param timeout timeout limit in seconds
   */
  public void awaitUntilElementIsDisplayed(final WebElement element, int timeout) {
    await(String.format("Wait for element: %s to be displayed", element))
        .atMost(timeout, TimeUnit.SECONDS)
        .pollInterval(Duration.ofSeconds(5))
        .ignoreExceptions()
        .until(element::isDisplayed);
  }

  /**
   * Wait until the element is enabled with awaitility with polling every 5 seconds
   *
   * @param element A valid WebElement
   * @param timeout timeout limit in seconds
   */
  public void awaitUntilElementIsEnabled(final WebElement element, int timeout) {
    await(String.format("Wait for element: %s to be enabled", element))
        .atMost(timeout, TimeUnit.SECONDS)
        .pollInterval(Duration.ofSeconds(5))
        .ignoreExceptions()
        .until(element::isEnabled);
  }

  /**
   * Wait until the element is selected with awaitility with polling every 5 seconds
   *
   * @param element A valid WebElement
   * @param timeout timeout limit in seconds
   */
  public void awaitUntilElementIsSelected(final WebElement element, int timeout) {
    await(String.format("Wait for element: %s to be selected", element))
        .atMost(timeout, TimeUnit.SECONDS)
        .pollInterval(Duration.ofSeconds(5))
        .ignoreExceptions()
        .until(element::isSelected);
  }

}
