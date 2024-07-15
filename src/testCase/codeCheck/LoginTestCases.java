package codeCheck;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.rahulshettyacademy.framework.dataproviders.DataProviders;
import com.rahulshettyacademy.framework.pages.Base;
import com.rahulshettyacademy.framework.pages.LoginPage;

public class LoginTestCases extends LoginPage {

	SoftAssert s_assert = new SoftAssert();
	
	@Test(dataProvider = "excelData", dataProviderClass = DataProviders.class)
	public void testCase1(String Username, String Password, String Comment) throws InterruptedException {
		sendKeystoUserEmail(Username);
		sendKeystoPass(Password);
		clickLoginBtn();
		if(Comment.equalsIgnoreCase("Invalid username")) {
			s_assert.assertEquals(invalidEmailToast(), LoginPage.errorEmail);
			
		}
		Thread.sleep(2000);
		if (homePageLogo() == true) {
			signOutBtn();
		}
		Thread.sleep(2000);
		clrLognPass();
	}

}
