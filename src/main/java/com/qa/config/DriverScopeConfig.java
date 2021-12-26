package com.qa.config;

import com.qa.annotation.LazyConfiguration;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;

@LazyConfiguration
public class DriverScopeConfig {

  @Bean
  public static BeanFactoryPostProcessor beanFactoryPostProcessor(){
    return new DriverScopePostProcessor();
  }

}
