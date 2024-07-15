package com.rahulshettyacademy.framework.dataproviders;

import org.testng.annotations.DataProvider;

public class DataProviders {

	ReadExcel readxl = new ReadExcel();
	
	@DataProvider (name = "excelData")
	public Object[][] excelData() {
		
		Object[][] data = readxl.getSheetDatabyRow("Login data");
		return data;
	}
	
}
