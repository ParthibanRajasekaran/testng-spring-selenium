package com.qa.config;

import com.qa.annotation.LazyConfiguration;
import com.qa.annotation.ThreadSafeBrowser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@LazyConfiguration
@Profile("!remote")
public class WebDriverLibrary {

  @ThreadSafeBrowser
  @ConditionalOnMissingBean
  public WebDriver getChromeDriver(){
    WebDriverManager.chromedriver().setup();
    return new ChromeDriver();
  }

  @ThreadSafeBrowser
  @ConditionalOnProperty(name = "browser", havingValue = "firefox")
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
