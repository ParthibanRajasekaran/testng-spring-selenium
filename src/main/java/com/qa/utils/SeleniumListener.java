package com.qa.utils;


import com.qa.annotation.LazyAutowired;
import com.qa.annotation.LazyConfiguration;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@LazyConfiguration
@Slf4j
//public class SeleniumListener extends AllureTestNg {
public class SeleniumListener implements ITestListener {

  @LazyAutowired
  private WebDriver driver;

  @Override
  public void onStart(ITestContext arg0) {
    log.info("Test suite: " + arg0.getName());
  }

  @Override
  public void onTestStart(ITestResult arg0) {
    log.info("Starting test: " + arg0.getMethod());
  }

  @Override
  public void onTestSuccess(ITestResult tr) {
    log.info(tr.getName() + " \n--- SUCCESS ---\n");
  }

  @Step("Step on Failure")
  @Override
  public void onTestFailure(ITestResult tr) {
    log.error(tr.getName() + " --- FAILED --- ");

    saveScreenshotPNG();
    log.debug("{} failed and screenshot taken", tr.getName());

    Throwable ex = tr.getThrowable();
    if (ex != null) {
      String cause = ex.toString();
      log.error(cause + "\n");
    }

    driver.close();
  }

  @SneakyThrows
  @Attachment(value = "page screenshot", type = "image/png")
  public byte[] saveScreenshotPNG() {
    log.debug("Taking screenshot");
    return ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
  }

  @Override
  public void onTestSkipped(ITestResult tr) {
    log.info(tr.getName() + " --- SKIPPED ---\n");
    Throwable ex = tr.getThrowable();
    if (ex != null) {
      String cause = ex.toString();
      log.error(cause);
    }
  }
}
