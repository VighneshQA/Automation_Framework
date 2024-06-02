package codeCheck;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.rahulshettyacademy.framework.BasePage.Base;

public class BaseClassCode extends Base {

	@Test(enabled = false)
	public void testOne() {
		// loadProperties();
		invokeBroswser("browserName");
		invokeURL("URL");
		browserClose();

	}

	@Test(enabled = true)
	public void testTwo() {

		String Title = driver.getTitle();
		System.out.println(Title);
		Assert.assertEquals(Title, "Let's Show");
	//	browserClose();

	}

}
