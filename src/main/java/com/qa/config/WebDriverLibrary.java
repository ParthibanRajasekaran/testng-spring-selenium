package com.qa.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile("!remote")
public class WebDriverLibrary {

  @Bean
//  @ConditionalOnProperty(name = "browser", havingValue = "chrome")
  @ConditionalOnMissingBean
  @Scope("driverscope")
  public WebDriver getChromeDriver(){
    WebDriverManager.chromedriver().setup();
    return new ChromeDriver();
  }

  @Bean
  @ConditionalOnProperty(name = "browser", havingValue = "firefox")
  @Scope("driverscope")
  public WebDriver getFirefoxDriver(){
    WebDriverManager.firefoxdriver().setup();
    return new FirefoxDriver();
  }

  @Bean
  @ConditionalOnProperty(name = "browser", havingValue = "edge")
  @Scope("driverscope")
  public WebDriver getEdgeDriver(){
    WebDriverManager.edgedriver().setup();
    return new EdgeDriver();
  }

  @Bean
  @ConditionalOnProperty(name = "browser", havingValue = "safari")
  @Scope("driverscope")
  public WebDriver getSafariDriver(){
    WebDriverManager.safaridriver().setup();
    return new SafariDriver();
  }

}
