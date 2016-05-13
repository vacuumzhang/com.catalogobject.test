package com.catalog.tests;


import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.catalog.common.CaptureScreenshot;
import com.catalog.common.Common;
import com.catalog.common.DataSetters;
import com.catalog.pagefactory.HomePageFactory;
import com.catalog.pagefactory.LoginPageFactory;
import com.catalog.pagefactory.WelcomePageFactory;


public class LoginTest {
	
	private WebDriver driver;
	public HomePageFactory HomePage;
	//public SignOnPage SignOnPage;
	public WelcomePageFactory WelcomePage;
	public LoginPageFactory LoginPage;
	
	public Common CM;

	@BeforeMethod
	public void setUp() throws IOException {
		CM = new Common(driver);
		driver = CM.openBrowser();
		CM.openUrl();
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {
		if(ITestResult.FAILURE == result.getStatus()){
			CaptureScreenshot.takeScreenshot(driver, result.getName());
		}
		driver.manage().deleteAllCookies();
		CM.closeBrowser();
	}
	
	
	

	@Test(description = "right password and username")
	public void login01() throws IOException {
		HomePage = new HomePageFactory(driver);
		HomePage.clickLogYourSelfLink();
		
		DataSetters rd = new DataSetters();
		String username = rd.propertiesReader("config.properties", "email_address");
		String password = rd.propertiesReader("config.properties", "password");
		
		CM.login(username, password);
		WelcomePage = new WelcomePageFactory(driver);
		CM.clickElement(WelcomePage.LogOfButton());

	}

	@Test(dataProvider="getData")  //wrong password, FindBy
	public void login02(String username, String password) {
		HomePage = new HomePageFactory(driver);
		HomePage.clickLogYourSelfLink();
		CM.login(username, password);
		String ActualText = " Error: No match for E-Mail Address and/or Password.";
		LoginPage = new LoginPageFactory(driver);
		CM.verifyText(LoginPage.messageStackError(), ActualText);
	}
	
	@DataProvider
	public Object[][] getData() throws IOException{
		return CM.setCellData();
		
	}

}
