package com.catalog.pagefactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.catalog.common.Common;
import com.catalog.common.DataSetters;

public class LoginPageFactory {
	private WebDriver driver;
	private Common CM;
	private static Logger logger = Logger.getLogger(LoginPageFactory.class);
	
	// By userename = By.name("email_address");
	// By pwd = By.name("password");
	// By loginButton = By.xpath("//*[@id='tdb5']/span[2]");

	public LoginPageFactory(WebDriver driver) {
		this.driver = driver;
		CM = new Common(driver);
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(name = "email_address")
	WebElement username;

	@FindBy(name = "password")
	WebElement pwd;

	@FindBy(xpath = "//*[@id='tdb5']/span[2]")
	WebElement loginButton;
	
	@FindBy(xpath = ".//*[@id='bodyContent']/table/tbody/tr/td")
	WebElement messageStackError;

	public void enterUsername(String emailAddress) {
		CM.sendKeyToElement(username, emailAddress);
	}

	public void enterPwd(String password) {
		CM.sendKeyToElement(pwd, password);
	}

	public void clickLoginButton() {
		CM.clickElement(loginButton);
	}
	
	public WebElement messageStackError() {
		return messageStackError;
	}
	
	public void login(String username, String password) {
		try {
			enterUsername(username);
			enterPwd(password);
			clickLoginButton();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void loginMethod() throws IOException{
		DataSetters rd = new DataSetters();
		String emailAddress = rd.propertiesReader("config.properties", "email_address");
		String password = rd.propertiesReader("config.properties", "password");
		CM.sendKeyToElement(username, emailAddress);
		enterUsername(emailAddress);
		//CM.sendKeyToElement(pwd, password);
		enterPwd(password);
		clickLoginButton();
	}

}
