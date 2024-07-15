package com.rahulshettyacademy.framework.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.rahulshettyacademy.framework.pages.Base;

/*This class is used to read the Excel file and provide the data to the test Cases*/

public class ReadExcel {

	String sheet_path;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	
	public void loadSheet() {
		Base base = new Base();
		try {
			Properties prop_value = base.loadProperties();
			sheet_path = System.getProperty("user.dir") + prop_value.getProperty("Excel_path");
			System.out.println(sheet_path);
			workbook = new XSSFWorkbook(sheet_path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getSheetCount() {
		int count = workbook.getNumberOfSheets();
		return count;

	}

	
	public Object[][] getSheetDatabyRow(String sheetName) {
		
		loadSheet();

		sheet = workbook.getSheet(sheetName);
		int countRow = sheet.getPhysicalNumberOfRows();
		int countCol = sheet.getLastRowNum();
		Object data[][] = new Object[countRow-1][countCol-1];
		for (int currRow = 0; currRow < countRow-1; currRow++) {
			for (int currCol = 0; currCol < countCol-1; currCol++) {

				row = sheet.getRow(currRow+1);
				cell = row.getCell(currCol+1);

				DataFormatter format = new DataFormatter();

				data[currRow][currCol] = format.formatCellValue(cell);

			}
		}
		return data;
	}

	public ArrayList<Object> getSheetDatabByCol(String sheetName, String columnName) {
		ArrayList<Object> dataList = new ArrayList<Object>();
		int columnIndex = -1;
		sheet = workbook.getSheet(sheetName);
		Iterator<Row> rows = sheet.iterator();
		Row currentrow = rows.next();
		Iterator<Cell> cell = currentrow.cellIterator();
		Cell curCellValue;
		while (cell.hasNext()) {
			/* This will iterate to find the column value in Header Row */
			curCellValue = cell.next();
			if (curCellValue.getStringCellValue().equalsIgnoreCase(columnName)) {
				columnIndex = curCellValue.getColumnIndex();
			}
		}

		/* Using Column Index fetch the Column Data */

		for (Row row : sheet) {
			row.getCell(columnIndex);

			dataList.add(row.getCell(columnIndex).getStringCellValue());
		}
		return dataList;
	}

	public void printValues() {

		ArrayList<Object> list = getSheetDatabByCol("Login Data", "Comment");
		for (Object value : list) {
			System.out.println(value);
		}
	}

	@Test
	public void printArrayValue() {

		Object[][] theData = getSheetDatabyRow("Login Data");

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.println(theData[i][j]);
			}

		}

	}
}
