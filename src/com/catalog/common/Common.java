package com.catalog.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import com.catalog.pagefactory.LoginPageFactory;

public class Common {

	private static Logger logger = Logger.getLogger(Common.class);
	private WebDriver driver;

	public LoginPageFactory LoginPage;
	public String chromePath = "./drivers/chromedriver";
	public String excelPath = "/Users/Simon/Documents/Selenium/Workspace/com.catalogobjct.selenium/src/test/resources/test.xlsx";
	public XSSFWorkbook excelWorkbook;
	public XSSFSheet excelSheet;
	public XSSFRow row;
	public XSSFCell cell;

	public Common(WebDriver driver) {
		this.driver = driver;
	}

	public void setDriver(WebDriver driver) {
		try{
			this.driver = driver;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public WebDriver openBrowser() throws IOException {

		DataSetters rd = new DataSetters();
		String browerType = rd.propertiesReader("./config.properties", "browser");
		logger.info("Brower Type: " + browerType);
		if (browerType.equals("Firefox")) {
			driver = new FirefoxDriver();
		} else if (browerType.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", chromePath);
			driver = new ChromeDriver();
		} else if (browerType.equals("Safari")) {
			System.setProperty("webdriver.safari.noinstall", "true");
			driver = new SafariDriver();
		} else if (browerType.equals("Remote")) {
			driver = new RemoteWebDriver(new URL("http://10.1.10.146:5566/wd/hub"), DesiredCapabilities.firefox());
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		logger.info("open Browser");
		return driver;
	}

	public void closeBrowser() {
		driver.close();
		logger.info("close Browser");
	}

	public void openUrl() {
		DataSetters rd = new DataSetters();
		try {
			String url = rd.propertiesReader("./config.properties", "url");
			driver.get(url);
			logger.info("openUrl: " + url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void browerBack() {
		driver.navigate().back();
		logger.info("back to " + driver.getTitle());
	}

	public void login(String username, String password) {
		try {
			LoginPage = new LoginPageFactory(driver);
			LoginPage.enterUsername(username);
			LoginPage.enterPwd(password);
			LoginPage.clickLoginButton();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void clickElement(WebElement target) {
		try{
			String element = target.getText();
			logger.info("trying to click " + element);
			highlightElement(driver, target);
			target.click();
			logger.info(element + " clicked");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void sendKeyToElement(WebElement target, String input) {
		try{
		String element = target.getText();
		highlightElement(driver, target);
		target.sendKeys(input);
		logger.info(input + "send to " + element);
		} catch (Exception e) {
			logger.error(e.getMessage());
			
		}
	}

	public void mouseMove(WebElement target) {
		try{
			String element = target.getText();
			highlightElement(driver, target);
			Actions action = new Actions(driver);
			action.moveToElement(target).build().perform();
			logger.info("mouse move to " + element);		
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
				
}

	public void verifyText(WebElement target, String expected) {
		try {
			String element = target.getText();
			highlightElement(driver, target);
			Assert.assertEquals(element, expected);
			logger.info("expected text verified: " + element);
		} catch (Exception e) {
			logger.error("expected text not found: " + expected);
		}
	}

	public void checkBrokenLinks() throws MalformedURLException, IOException {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		logger.info("checking broken links for: " + driver.getCurrentUrl());
		for (int i = 0; i < links.size(); i++) {
			try {
				if ((links.get(i).getAttribute("href") != null) && !(links.get(i).getAttribute("href").equals(""))) {
					if (links.get(i).getAttribute("href").contains("http")) {
						int linkCode = getResponseCode(links.get(i).getAttribute("href").trim());
						if (linkCode == 404) {
							logger.error(i + ": 404 found -----" + links.get(i).getAttribute("href").trim());
						} else {
							logger.info(i + ": workes fine -----" + links.get(i).getAttribute("href").trim());
						}
					}
				}
			} catch (StaleElementReferenceException elementHasDisappeared) {
				logger.error("StaleElementReferenceException index: " + i);
			}
		}
	}

	public int getResponseCode(String sURL) throws MalformedURLException, IOException {
		URL url = new URL(sURL);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		huc.connect();
		return huc.getResponseCode();
	}

	public static void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.info(e.getMessage());
		}
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element);
	}

	// get data from
	public Object[][] setCellData() {

		FileInputStream fis;
		try {
			fis = new FileInputStream(excelPath);
			excelWorkbook = new XSSFWorkbook(fis);
			excelSheet = excelWorkbook.getSheet("sheet1");
			int rowCount = excelSheet.getLastRowNum() - excelSheet.getFirstRowNum();
			int colCount = excelSheet.getRow(0).getLastCellNum();

			Object[][] data = new Object[rowCount + 1][colCount];
			for (int rowNum = 0; rowNum <= rowCount; rowNum++) {
				for (int colNum = 0; colNum < colCount; colNum++) {
					data[rowNum][colNum] = excelSheet.getRow(rowNum).getCell(colNum).getStringCellValue();
				}
			}

			return data;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;

		}

	}
}
