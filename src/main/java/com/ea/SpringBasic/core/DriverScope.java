package com.ea.SpringBasic.core;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.SimpleThreadScope;

public class DriverScope extends SimpleThreadScope {

  @Override
  public Object get(String name, ObjectFactory<?> objectFactory) {
    return super.get(name, objectFactory);
  }

  @Override
  public void registerDestructionCallback(String name, Runnable callback) {

  }
}
