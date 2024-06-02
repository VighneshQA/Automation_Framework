package com.rahulshettyacademy.framework.BasePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public WebDriver driver;
	Properties prop;

	/*************** Load Properties File **********************/

//	@BeforeSuite
	public Properties loadProperties() {
		prop = new Properties();
		try {
			FileInputStream file = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\resource\\configFile\\ProjectConfig.properties");
			prop.load(file);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}

	/*************** Invoke Browser **********************/

	public void invokeBroswser(String browserName) {

		try {

			String browser = prop.getProperty(browserName);
			if (browser.equalsIgnoreCase("Chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("Firefox")) {
				WebDriverManager.firefoxdriver().setup();
			} else if (browser.equalsIgnoreCase("Microsoft Edge")) {
				WebDriverManager.edgedriver().setup();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/******************* Invoke URL *********************/

	public void invokeURL(String URL) {
		try {
			driver.get(prop.getProperty(URL));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/***************** Identify Locator ******************/

	public WebElement getWebElement(String locatorKey) {
		WebElement element = null;

		try {
			if (locatorKey.endsWith("_id")) {
				element = driver.findElement(By.id(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_name")) {
				element = driver.findElement(By.name(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_css")) {
				element = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			}
			if (locatorKey.endsWith("_LinkText")) {
				element = driver.findElement(By.linkText(prop.getProperty(locatorKey)));
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return element;
	}

	/**************** Timeouts **************/

	/**************** Close Browser **************/
	

	public void browserClose() {
		driver.close();

	}
	
	/**************** Quit Browser **************/

//	@AfterSuite
	public void browserQuit() {
		driver.quit();
	}

	/**************** Enter Text **************/

	public void enterText(String locatorKey, String text) {
		try {
			getWebElement(locatorKey).sendKeys(text);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** Click Element **************/

	public void clickElement(String locatorKey) {
		try {
			getWebElement(locatorKey).click();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** StaticDropdown by Index **************/

	public void staticDropdownIndex(String locatorKey, int indexValue) {
		try {
			getWebElement(locatorKey).click();
			WebElement element = driver.findElement(By.xpath(locatorKey));
			Select dropdown = new Select(element);
			dropdown.selectByIndex(indexValue);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** StaticDropdown by value **************/

	public void staticDropdownValue(String locatorKey, String attValue) {
		try {
			getWebElement(locatorKey).click();
			WebElement element = driver.findElement(By.xpath(locatorKey));
			Select dropdown = new Select(element);
			dropdown.selectByValue(attValue);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** StaticDropdown by visible_text **************/

	public void staticDropdownVisibleText(String locatorKey, String visibleText) {
		try {
			getWebElement(locatorKey).click();
			WebElement element = driver.findElement(By.xpath(locatorKey));
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(visibleText);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** DropdownList by Auto suggestive **************/

	public void autoSuggestiveDropdown(String locatorKey, String value, String text) {
		try {
			getWebElement(locatorKey).sendKeys(value);
			List<WebElement> element = driver.findElements(By.xpath(locatorKey));
			element.stream().filter(ele -> ele.getText().equalsIgnoreCase(text)).forEach(ele -> ele.click());
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** Switch to Frames **************/

	public void switchtoFrame(String frameLocator) {
		try {
			driver.switchTo().frame(frameLocator);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void switchtoFrameIndex(int index) {
		try {
			driver.switchTo().frame(index);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void switchFrametoDefault() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** Verify Elements **************/

	/**************** Assertions **************/

	/**************** Reporting **************/

	/**************** Take SS **************/

	public String screenShot(String testCaseName, WebDriver driver) {
		this.driver = driver;
		
		TakesScreenshot takeShot = (TakesScreenshot) driver;
		File srcFile = takeShot.getScreenshotAs(OutputType.FILE);
		Instant time = Instant.now();
		File destFile = new File(System.getProperty("user.dir") + "//Screenshots//" + testCaseName + time + ".png");
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (Exception e) {
			e.getMessage();
		}
		System.out.println(destFile.getAbsolutePath());
		return destFile.getAbsolutePath();
	}
	
	/***************Abstract Methods********************/
	
	@BeforeSuite
	public void basicMethod() {
	
		loadProperties();
		invokeBroswser("browserName");
		invokeURL("URL");

	}

}
