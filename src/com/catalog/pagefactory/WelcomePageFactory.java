package com.catalog.pagefactory;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.catalog.common.Common;
import com.catalog.pagefactory.HomePageFactory;

public class WelcomePageFactory {
private Logger logger = Logger.getLogger(HomePageFactory.class);
	
	private WebDriver driver;
	private Common CM;

	public WelcomePageFactory(WebDriver driver){
		this.driver = driver;
		CM = new Common(driver);
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		PageFactory.initElements(this.driver, this);
		
	}
	
	public String LOGOFFBUTTON="//*[@id='tdb4']/span";
	
	@FindBy(how = How.XPATH, using ="//*[@id='tdb4']/span")
	WebElement LogOfButton;
	
	public WebElement LogOfButton(){
		return LogOfButton;
	}
	
	

}
