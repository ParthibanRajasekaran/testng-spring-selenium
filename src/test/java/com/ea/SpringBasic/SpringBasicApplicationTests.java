package com.ea.SpringBasic;

import com.ea.SpringBasic.pages.HomePage;
import com.ea.SpringBasic.pages.LoginPage;
import com.ea.SpringBasic.pages.MainPage;
import com.sun.tools.javac.Main;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@SpringBootTest
@Profile("qa")
class SpringBasicApplicationTests {

	@Autowired
	private MainPage mainPage;

	@Value("${app.url}")
	private String appUrl;

	@Value("chrome,firefox,edge")
	private List<String> browsers;

	@Test
	void loginTest() {
		mainPage.performLogin();
	}

}
