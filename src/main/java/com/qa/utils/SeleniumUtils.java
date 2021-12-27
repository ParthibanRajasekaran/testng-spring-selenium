package com.qa.utils;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

import com.qa.annotation.LazyAutowired;
import com.qa.annotation.Utils;
import io.qameta.allure.Step;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v95.network.Network;
import org.openqa.selenium.devtools.v95.network.model.Headers;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Value;

@Utils
public class SeleniumUtils {

  private static Logger log = LogManager.getLogger(SeleniumUtils.class);

  @LazyAutowired
  private WebDriver driver;

  @LazyAutowired
  private Waiters waiters;

  @LazyAutowired
  private Random random;

  @Value("${default.timeout:30}")
  private long timeout;

  @Value("${default.polling.interval:5000}")
  private long pollingInterval;

  public Wait<WebDriver> getFluentWait() {
    return getFluentWait(timeout);
  }

  private Wait<WebDriver> getFluentWait(long timeoutSeconds) {
    return new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(timeoutSeconds))
        .pollingEvery(Duration.ofMillis(pollingInterval))
        .ignoring(NoSuchElementException.class);
  }

  /**
   * Check if the webelement is present.
   *
   * @param element A valid xpath locator.
   * @return true only if the element is found and is visible
   */
  @Step
  public boolean isElementPresent(final WebElement element) {
    try {
      return null
          != getFluentWait()
          .until(ExpectedConditions.visibilityOf(element));
    } catch (final TimeoutException ignored) {
      return false;
    }
  }

  /**
   * Check if the webelement is displayed and assert if the element does not exist.
   *
   * @param element A valid xpath locator.
   */
  @Step("Assert if element is not present: {0}")
  public void assertElementPresent(final WebElement element) {
    try {
      getFluentWait().until(ExpectedConditions.visibilityOf(element));
      log.debug(String.format("Element is present: %s", element));
    } catch (Exception ex) {
      fail(String.format("Element %s is not present", element));
    }
  }

  /**
   * Check if the webelement is not displayed and assert if the element exists.
   *
   * @param element A valid xpath locator.
   */
  @Step("Assert if element is present: {0}")
  public void assertElementNotPresent(final WebElement element) {
    try {
      getFluentWait().until(ExpectedConditions.invisibilityOf(element));
      log.debug(String.format("Element %s is present: ", element));
    } catch (Exception ex) {
      fail(String.format("Element %s is still present", element));
    }
  }

  /**
   * Check if the webelement is enabled.
   *
   * @param element A valid xpath locator.
   * @return true only if the element is found and is enabled
   */
  @Step
  public boolean isElementEnabled(final WebElement element) {
    return element.isEnabled();
  }

  /**
   * Check if the webelement is disabled.
   *
   * @param element A valid xpath locator
   * @return true if the element is either not found or is enabled
   */
  @Step
  public boolean isElementDisabled(final WebElement element) {
    return !element.isEnabled();
  }

  /**
   * Check if the checkbox is checked on.
   *
   * @param element A valid xpath locator.
   * @return true if the element is found and is selected
   */
  @Step
  public Boolean isCheckBoxSelected(final WebElement element) {
    try {
      getFluentWait().until(ExpectedConditions.elementToBeSelected(element));
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  /**
   * Check if the webelement contains the text. The {@code text} should not be a regular expression,
   * just plain text.
   *
   * @param element A valid xpath locator.
   * @param text Which should be present on the webelement located by {@code locator}
   * @return true only if the webelement is found and contains the text
   */
  @Step
  public boolean isTextPresent(final WebElement element, final String text) {
    try {
      return element.getText().contains(text);
    } catch (TimeoutException ignored) {
      log.debug("Timeout looking for {}", element, ignored);
      return false;
    }
  }

  /**
   * Check if the webelement contains the text and assert if not.
   *
   * @param element A valid xpath locator.
   * @param text Which should be present on the webelement located by {@code locator}
   */
  @Step
  public void assertTextPresent(final WebElement element, String text) {
    assertTrue(element.getText().contains(text));
  }

  /**
   * Get the value attribute of an element identified by the xpath {@code locator}.
   *
   * @param element Must be a valid not null xpath
   * @return The value, potentially null
   */
  private String getAttributeValue(final WebElement element) {
    return element.getAttribute("value");
  }

  /**
   * Navigate to the URL specified from the current page.
   *
   * @param url The URL to load. It is best to use a fully qualified URL.
   */
  @Step
  public void navigateToPage(String url) {
    log.info(String.format("Navigating to web page %s", url));
    driver.navigate().to(url);
  }

  /**
   * Switch to the iframe window.
   *
   * @param mainFrame A valid webelement.
   * @param elementOnFrame
   */
  @Step
  public void switchToFrame(final WebElement mainFrame, final WebElement elementOnFrame) {
    driver.switchTo().frame(mainFrame);
    assertTrue(elementOnFrame.isDisplayed());
    log.debug("Switched to expected frame");
  }

  /**
   * Switch back to the parent window.
   *
   * @param elementOnFrame
   */
  @Step
  public void switchBackFromFrame(final WebElement  elementOnFrame) {
    driver.switchTo().defaultContent();
    assertTrue(elementOnFrame.isDisplayed());
    log.debug("Switched back from frame");
  }

  /** Scroll up in the current page. */
  @Step
  public void scrollUp() throws InterruptedException {
    final JavascriptExecutor jse = (JavascriptExecutor) driver;
    jse.executeScript("window.scrollBy(0, -1000);");
    sleep(1500);
  }

  /** Scroll down in the current page. */
  @Step
  public void scrollDown() throws InterruptedException {
    final JavascriptExecutor jse = (JavascriptExecutor) driver;
    jse.executeScript("window.scrollBy(0,800)");
    sleep(1000);
  }

  @Step
  public void scrollToElement(final WebElement element) {
    final Actions actions = new Actions(driver);
    actions.moveToElement(element).perform();
  }

  /** Reload the current page. */
  @Step
  public void reload() {
    driver.navigate().refresh();
  }

  /**
   * Click on the weblement.
   *
   * @param element A valid web element.
   */
  @Step
  public void clickElement(final WebElement element) {
    waiters.waitUntilElementClickable(element);
    element.click();
  }

  /**
   * Clear the input field.
   *
   * @param field A valid web element
   */
  @Step
  public void clearField(final WebElement field) {
    field.clear();
    waiters.waitUntilValueWillBePresentInElement(field, "");
  }

  /**
   * Clear and enter the value in the input field.
   *
   * @param field A valid Web element.
   * @param text
   */
  @Step
  public void clearAndFillInFieldWith(final WebElement field, final String text) {
    field.clear();
    waiters.waitUntilValueWillBePresentInElement(field, "");
    field.sendKeys(text);
    waiters.waitUntilValueWillBePresentInElement(field, text);
  }

  /**
   * Enter the value in the input field.
   *
   * @param field A valid Web element
   * @param text
   */
  @Step
  public void fillInFieldWith(final WebElement field, final String text) {
    field.sendKeys(text);
    waiters.waitUntilValueWillBePresentInElement(field, text);
  }

  /**
   * Generate a random number between 2 limits. Note that these random numbers are not
   * cryptographically strong.
   *
   * @param min The minimum value (inclusive) that may be returned
   * @param max The maximum value (inclusive) that may be returned.
   * @return An integer that will be equal to or between the bounds provided
   */
  @Step
  public int getRandomNumberBetween(final int min, final int max) {
    return random.nextInt((max - min) + 1) + min;
  }

  /**
   * Switch between the tabs.
   *
   * @param tabNumber
   */
  @Step
  public void switchToTab(int tabNumber) {
    final List<String> tabs = new ArrayList<>(driver.getWindowHandles());
    driver.switchTo().window(tabs.get(tabNumber));
  }

  /**
   * Get the current time stamp.
   *
   * @return The current time expressed in milliseconds
   */
  @Step
  public long getTimeStamp() {
    return Calendar.getInstance().getTime().getTime();
  }

  /**
   * Hover the mouse pointer over the webelement.
   *
   * @param element A valid xpath locator.
   */
  @Step
  public void hoverOverElement(final WebElement element) {
    final Actions builder = new Actions(driver);
    builder.moveToElement(element).perform();
  }

  /**
   * Get the text from the weblement.
   *
   * @param element A valid xpath locator.
   * @return
   */
  @Step
  public String getElementText(final WebElement element) {
    return element.getText();
  }

  /**
   * Get the number out of a string.
   *
   * @param text
   * @return
   */
  @Step
  public static Integer getNumberFromText(String text) {
    return Integer.parseInt(text.replaceAll("[^0-9]", ""));
  }

  /** Delete all browser cookies. */
  @Step
  public void deleteAllCookies() {
    driver.manage().deleteAllCookies();
  }

  /** @return the title name on the browser tab for the website loaded in the browser */
  public String getTitleNameFromSiteWithJavascriptExecutor() {
    // Creating the JavascriptExecutor interface object by Type casting
    JavascriptExecutor js = (JavascriptExecutor) driver;
    return js.executeScript("return document.title;").toString();
  }

  /** @param element perform a click operation on the webelement via JavaScriptExecutor */
  public void clickWithJavaScriptExecutor(final WebElement element) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript("arguments[0].click();", element);
  }

  /** @param element perform a double click on the webelement */
  public void doubleClick(final WebElement element) {
    Actions actions = new Actions(driver);
    actions.doubleClick(element).perform();
  }

  /** Handle basic auth via bi-directional api
   * @param hostName as String
   * @param username as String
   * @param password as String
   */
  public void handleBasicAuthViaBiDiApi(
      final String hostName, final String username, final String password) {
    driver = getAugmentedDriver();
    Predicate<URI> uriPredicate = uri -> uri.getHost().contains(hostName);
    ((HasAuthentication) driver)
        .register(uriPredicate, UsernameAndPassword.of(username, password));
  }

  /**
   * Handle basic auth via chrome dev tools
   * @param username as String
   * @param password as String
   */
  public void handleBasicAuthViaChromeDevTools(
      final String username, final String password) {
    Augmenter augmenter = new Augmenter();
    DevTools devTools = ((HasDevTools) augmenter.augment(driver)).getDevTools();
    devTools.createSession();
    devTools.send(Network.enable(Optional.of(100000), Optional.of(100000), Optional.of(100000)));
    final String auth = username + ":" + password;
    String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());
    Map<String, Object> headers = new HashMap<>();
    headers.put("Authorization", "Basic " + encodeToString);
    devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
  }

  private WebDriver getAugmentedDriver() {
    Augmenter augmenter = new Augmenter();
    DevTools devTools = ((HasDevTools) augmenter.augment(driver)).getDevTools();
    devTools.createSession();
    return augmenter.
        addDriverAugmentation("chrome", HasAuthentication.class,
            (caps, exec) -> (whenThisMatches, useTheseCredentials) -> devTools.getDomains()
                .network().addAuthHandler(whenThisMatches, useTheseCredentials))
        .augment(driver);
  }

}
