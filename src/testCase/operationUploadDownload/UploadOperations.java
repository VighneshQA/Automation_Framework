package operationUploadDownload;

import org.testng.annotations.Test;

import com.rahulshettyacademy.framework.pages.UploadDownload;

public class UploadOperations extends UploadDownload {
	String path = "C:\\Users\\vther\\Downloads\\download.xlsx";
	
	@Test
	public void uploadFile() {
		waitforEle();
		upload(path);
	}
}
