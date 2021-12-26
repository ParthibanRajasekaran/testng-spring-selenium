package com.qa.pages;

import javax.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BasePage {

  @Autowired
  private WebDriver driver;

  @PostConstruct
  public void initPage() {
    PageFactory.initElements(driver, this);
  }

  public void navigatePage(String url){
    driver.navigate().to(url);
  }
}
