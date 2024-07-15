package com.rahulshettyacademy.framework.pages;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class LoginPage extends Base {

	public static String errorEmail = "Incorrect email or password.";
	
	
	@Test(enabled=false)
	public void loginTest() {
	enterText("userEmail",Base.LocatorType.id,"vtherider6342@gmail.com");
	enterText("userPassword",Base.LocatorType.id,"12345678");
	clickElement("login",Base.LocatorType.id);
	}

	public void sendKeystoUserEmail(String text) {
		enterText("userEmail",LocatorType.id,text);
	}
	
	public void sendKeystoPass(String text) {
		enterText("userPassword",LocatorType.id,text);
	}
	
	public void clickLoginBtn() {
		clickElement("login",LocatorType.id);
	}
	
	public void signOutBtn() {
		
		clickElement("//button//i[@class='fa fa-sign-out']",LocatorType.xpath);
	}
	
	public void clrLognPass() {
		
		clear("userEmail",LocatorType.id);
		clear("userPassword",LocatorType.id);
	}
	
	public boolean homePageLogo() {
		
		boolean logoStatus = driver.findElement(By.xpath("//h3")).isDisplayed();
		return logoStatus;
		
	}
	
	public String invalidEmailToast() {
		
		String invalidEmailToastText = driver.findElement(By.id("//div[@id='toast-container']")).getText();
		return invalidEmailToastText;
	}



}
