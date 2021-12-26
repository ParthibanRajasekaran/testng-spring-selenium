package com.ea.SpringBasic.report;


import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Configuration
public class SeleniumListener implements ITestListener {
//public class SeleniumListener extends AllureTestListener {

  @Autowired
  protected WebDriver driver;

  private static final Logger log = LoggerFactory.getLogger(SeleniumListener.class);

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

    // saveTextLog(tr.getName());
    log.info(String.format("%s failed and screenshot taken", tr.getName()));
    saveScreenshotPNG();

    Throwable ex = tr.getThrowable();
    if (ex != null) {
      String cause = ex.toString();
      log.error(cause + "\n");
    }
  }

  @SneakyThrows
  @Attachment(value = "page screenshot", type = "image/png")
  public byte[] saveScreenshotPNG() {
    log.debug("Taking screenshot");
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
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
