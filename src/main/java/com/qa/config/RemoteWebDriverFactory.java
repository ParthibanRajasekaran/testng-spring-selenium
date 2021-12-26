package com.qa.config;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile("remote")
public class RemoteWebDriverFactory {

  @Value("${grid.url}")
  public URL hubUrl;

  @Bean
//  @ConditionalOnProperty(name = "browser", havingValue = "chrome")
  @ConditionalOnMissingBean
  @Scope("driverscope")
  public WebDriver getRemoteWebDriverForChrome() {
    return new RemoteWebDriver(hubUrl, new ChromeOptions());
  }

  @Bean
  @ConditionalOnProperty(name = "browser", havingValue = "firefox")
  @Scope("driverscope")
  public WebDriver getRemoteWebDriverForFirefox() {
    return new RemoteWebDriver(hubUrl, new FirefoxOptions());
  }

}
