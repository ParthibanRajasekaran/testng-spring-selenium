package com.ea.SpringBasic.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DriverScopePostProcessor implements BeanFactoryPostProcessor {

  @Override
  public void postProcessBeanFactory(
      ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
      configurableListableBeanFactory.registerScope("driverscope", new DriverScope());
  }
}
