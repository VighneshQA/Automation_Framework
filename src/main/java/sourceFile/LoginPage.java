package sourceFile;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;

public class LoginPage extends Driver {

	@BeforeTest
	public void Login() {

		super.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		super.driver.navigate().to("https://rahulshettyacademy.com/client/");
		super.driver.findElement(By.id("userEmail")).sendKeys("vtherider6342@gmail.com");
		super.driver.findElement(By.id("userPassword")).sendKeys("Richdad@6342");
		super.driver.findElement(By.xpath("//input[@value='Login']")).click();
	}

}
