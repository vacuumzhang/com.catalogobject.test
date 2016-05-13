package com.catalog.common;


import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;



public class CaptureScreenshot {
	
	private static Logger logger = Logger.getLogger(CaptureScreenshot.class);
	
	public static void takeScreenshot(WebDriver driver,String screenshotName){
		try {
			TakesScreenshot ts=(TakesScreenshot)driver;
			
			File source=ts.getScreenshotAs(OutputType.FILE);
			
			FileUtils.copyFile(source, new File("./Screenshots/"+ screenshotName +".png"));
			logger.info("Screenshot for " + screenshotName + " taken");
		} catch (Exception e){
			logger.info("Exception while taking screenshot " + e.getMessage());
		} 
	}
}