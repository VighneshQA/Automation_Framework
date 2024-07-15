package com.rahulshettyacademy.framework.pages;

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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	protected WebDriver driver;
	public Properties prop;
	public WebElement element = null;

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

			String browser = System.getProperty("browser") != null ? System.getProperty("browser")
					: prop.getProperty(browserName);
			if (browser.equalsIgnoreCase("Chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				if (browser.contains("headless")) {
					options.addArguments("headless");
				}
				driver = new ChromeDriver(options);

			} else if (browser.equalsIgnoreCase("Firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("Microsoft Edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			} else {

				System.out.println("Invalid browser name :" + browser);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/******************* Invoke URL from Property file *********************/

	public void invokeURL(String URL) {
		try {
			driver.get(prop.getProperty(URL));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/***************** Identify Locator ******************/

	public enum LocatorType {
		id, name, css, xpath, linktext,
	}

	public WebElement getWebElement(String locatorKey, LocatorType type) {

		try {
			switch (type) {
			case id:
				element = driver.findElement(By.id(locatorKey));
				break;
			case name:
				element = driver.findElement(By.name(locatorKey));
				break;
			case css:
				element = driver.findElement(By.cssSelector(locatorKey));
				break;
			case xpath:
				element = driver.findElement(By.xpath(locatorKey));
				break;
			case linktext:
				element = driver.findElement(By.linkText(locatorKey));
				break;
			default:
				throw new IllegalArgumentException("This is an invalid LocatorType or locator not registered in enum");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

	/*
	 * $$ This code is used when the data is retrieved from property file $$ public
	 * WebElement getWebElement(String locatorKey) { WebElement element = null;
	 * 
	 * try { if (locatorKey.endsWith("_id")) { element =
	 * driver.findElement(By.id(prop.getProperty(locatorKey))); } if
	 * (locatorKey.endsWith("_name")) { element =
	 * driver.findElement(By.name(prop.getProperty(locatorKey))); } if
	 * (locatorKey.endsWith("_css")) { element =
	 * driver.findElement(By.cssSelector(prop.getProperty(locatorKey))); } if
	 * (locatorKey.endsWith("_xpath")) { element =
	 * driver.findElement(By.xpath(prop.getProperty(locatorKey))); } if
	 * (locatorKey.endsWith("_LinkText")) { element =
	 * driver.findElement(By.linkText(prop.getProperty(locatorKey))); } } catch
	 * (Exception e) { e.getMessage(); } return element; }
	 * 
	 */

	/**************** Clear Field **************/
	public void clear(String locatorKey, LocatorType type) {

		getWebElement(locatorKey, type).clear();
	}

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

	public void enterText(String locatorKey, LocatorType type, String text) {
		try {
			getWebElement(locatorKey, type).sendKeys(text);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** Click Element **************/

	public void clickElement(String locatorKey, LocatorType type) {
		try {
			getWebElement(locatorKey, type).click();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** StaticDropdown by Index **************/

	public void staticDropdownIndex(String locatorKey, LocatorType type, int indexValue) {
		try {
			getWebElement(locatorKey, type).click();
			WebElement element = driver.findElement(By.xpath(locatorKey));
			Select dropdown = new Select(element);
			dropdown.selectByIndex(indexValue);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** StaticDropdown by value **************/

	public void staticDropdownValue(String locatorKey, LocatorType type, String attValue) {
		try {
			getWebElement(locatorKey, type).click();
			WebElement element = driver.findElement(By.xpath(locatorKey));
			Select dropdown = new Select(element);
			dropdown.selectByValue(attValue);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** StaticDropdown by visible_text **************/

	public void staticDropdownVisibleText(String locatorKey, LocatorType type, String visibleText) {
		try {
			getWebElement(locatorKey, type).click();
			WebElement element = driver.findElement(By.xpath(locatorKey));
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(visibleText);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**************** DropdownList by Auto suggestive **************/

	public void autoSuggestiveDropdown(String locatorKey, LocatorType type, String value, String text) {
		try {
			getWebElement(locatorKey, type).sendKeys(value);
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
		// Instant time = Instant.now();
		File destFile = new File(System.getProperty("user.dir") + "//Screenshots//" + ".png");
		try {
			FileUtils.copyFile(srcFile, destFile);
			System.out.println(destFile.getAbsolutePath());
			return destFile.getAbsolutePath();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/*************** Abstract Methods ********************/

	@BeforeSuite
	public void basicMethod() {

		loadProperties();
		invokeBroswser("browserName");
		driver.manage().window().maximize();
		invokeURL("URL");

	}

}
