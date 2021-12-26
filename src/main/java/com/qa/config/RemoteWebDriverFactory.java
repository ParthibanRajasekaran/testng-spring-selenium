package com.qa.config;

import com.qa.annotation.LazyConfiguration;
import com.qa.annotation.ThreadSafeBrowser;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;

@LazyConfiguration
@Profile("remote")
public class RemoteWebDriverFactory {

  @Value("${grid.url}")
  public URL hubUrl;

  @ConditionalOnMissingBean
  @ThreadSafeBrowser
  public WebDriver getRemoteWebDriverForChrome() {
    return new RemoteWebDriver(hubUrl, new ChromeOptions());
  }

  @ThreadSafeBrowser
  @ConditionalOnProperty(name = "browser", havingValue = "firefox")
  public WebDriver getRemoteWebDriverForFirefox() {
    return new RemoteWebDriver(hubUrl, new FirefoxOptions());
  }

}
