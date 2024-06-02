package sourceFile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Driver {

	public WebDriver driver;

	public void InitiateDriver() {
		driver = new ChromeDriver();
		
	}

}
