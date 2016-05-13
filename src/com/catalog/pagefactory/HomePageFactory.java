package com.catalog.pagefactory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.catalog.common.Common;

public class HomePageFactory {
	private WebDriver driver;
	private Common CM;
	
	public HomePageFactory(WebDriver driver) {
		this.driver = driver;
		CM = new Common(driver);
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		PageFactory.initElements(this.driver, this);
	}
	
	@FindBy(how = How.XPATH, using ="//*[@id='bodyContent']/div/div[1]/a[1]/u")
	WebElement logYourSelfLink;
	
	public void clickLogYourSelfLink(){
		CM.clickElement(logYourSelfLink);
	}
}
