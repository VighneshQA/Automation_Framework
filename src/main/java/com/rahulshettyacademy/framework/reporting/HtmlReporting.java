package com.rahulshettyacademy.framework.reporting;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.rahulshettyacademy.framework.BasePage.Base;

public class HtmlReporting extends Base implements ITestListener {

	static ExtentReports extent;
	static ExtentTest Test;

	public HtmlReporting() {
		String path = System.getProperty("user.dir") + "//reports//myReport.html";
//	   File reportDir = new File(System.getProperty("user.dir") + "//reports//");
//       if (!reportDir.exists()) {
//           reportDir.mkdirs();
//       }
		ExtentSparkReporter extentspark = new ExtentSparkReporter(path);
		extentspark.config().setReportName("Base Automation Test Results");
		extentspark.config().setDocumentTitle("Test Results");

		extent = new ExtentReports();
		extent.attachReporter(extentspark);
		extent.setSystemInfo("Tester", "Vighnesh Keni");

	}

	@Override
	public void onTestStart(ITestResult result) {

		ITestListener.super.onTestStart(result);
		Test = extent.createTest(result.getMethod().getMethodName());
		System.out.println("Test Started successfully for " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ITestListener.super.onTestSuccess(result);
		Test.log(Status.PASS, "Test Passed");
		System.out.println("Test passed for " + result.getMethod().getMethodName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
		String ss = null;
		ITestListener.super.onTestFailure(result);
		Test.log(Status.FAIL, "Test Failed");

		// Create Instance of the driver
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {

		}

		// Get the error message
		Test.fail(result.getThrowable());
		try {
			ss = screenShot(result.getMethod().getMethodName(), driver);

			Test.addScreenCaptureFromPath(ss, result.getMethod().getMethodName());
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ITestListener.super.onTestSkipped(result);
		Test.skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {

		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {

		ITestListener.super.onFinish(context);
		extent.flush();
		System.out.println("Test flushed successfully for ");
	}

}
