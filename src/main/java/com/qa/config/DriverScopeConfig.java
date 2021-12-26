package com.qa.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverScopeConfig {

  @Bean
  public static BeanFactoryPostProcessor beanFactoryPostProcessor(){
    return new DriverScopePostProcessor();
  }

}
