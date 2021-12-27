package com.qa.utils;

import org.testng.annotations.DataProvider;

public class SampleDataProvider {

  @DataProvider(name = "testData")
  public static Object[][] testData() {
    return new Object[][] {{"A", "A"}, {"1", "1"}};
  }
}
