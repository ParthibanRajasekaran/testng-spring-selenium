package com.qa.config;

import com.qa.annotation.LazyAutowired;
import com.qa.annotation.LazyConfiguration;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@LazyConfiguration
public class DriverWaitConfig {

  @LazyAutowired
  private WebDriver driver;

  @Value("${default.timeout:30}")
  private int timeout;

  @Value("${default.polling.interval:5000}")
  private long pollingInterval;

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public WebDriverWait webDriverWait(WebDriver driver){
    return new WebDriverWait(driver, Duration.ofSeconds(this.timeout));
  }
//
//
//  private FluentWait getFluentWait(long timeoutSeconds) {
//    return new FluentWait<>(driver)
//        .withTimeout(Duration.ofSeconds(timeoutSeconds))
//        .pollingEvery(Duration.ofMillis(pollingInterval))
//        .ignoring(NoSuchElementException.class);
//  }
//
//  @Bean
//  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//  public Wait<WebDriver> getFluentWait() {
//    return getFluentWait(timeout);
//  }

}
