package com.rahulshettyacademy.framework.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UploadDownload extends Base {

	WebElement uploadbtn;
	
	
	
	public void upload(String path) {

		uploadbtn = driver.findElement(By.xpath("//input[@type='file']"));
		uploadbtn.sendKeys(path);
	}

	public void waitforEle() {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
}
