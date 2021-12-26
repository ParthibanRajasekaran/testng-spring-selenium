package com.qa.config;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverWaitConfig {

  @Value("${default.timeout:30}")
  private int timeout;

  @Bean
  public WebDriverWait webDriverWait(WebDriver driver){
    return new WebDriverWait(driver, Duration.ofSeconds(this.timeout));
  }

}
