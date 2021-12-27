package com.qa.utils;


import com.qa.annotation.Utils;
import org.springframework.beans.factory.annotation.Value;

@Utils
public class AuthUtils {

  @Value("${host}")
  public String host;

  @Value("${protocol}")
  public String protocol;

  @Value("${username}")
  public String username;

  @Value("${password}")
  public String password;

}
