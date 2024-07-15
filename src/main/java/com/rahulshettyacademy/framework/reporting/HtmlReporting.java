package com.rahulshettyacademy.framework.reporting;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.rahulshettyacademy.framework.pages.Base;

public class HtmlReporting extends Base implements ITestListener {

	static ExtentReports extent;
	static ExtentTest Test;
	String methodName;

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
		methodName = result.getMethod().getMethodName();
		Test = extent.createTest(methodName);
		System.out.println("Test Started successfully for " + methodName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ITestListener.super.onTestSuccess(result);
		Test.log(Status.PASS, "Test Passed");
		System.out.println("Test passed for " + methodName);

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
			ss = screenShot(methodName, driver);

			Test.addScreenCaptureFromPath(ss, methodName);
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
