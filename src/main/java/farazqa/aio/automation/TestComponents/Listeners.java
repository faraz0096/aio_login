package farazqa.aio.automation.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listeners extends BaseClass implements ITestListener {
	
	   ExtentTest test;
	   ExtentReports extent =  ExtentReportNG.getReportObject();

	    @Override
	    public void onTestStart(ITestResult result){

	       test= extent.createTest(result.getMethod().getMethodName());

	    }

	    @Override
	    public void onTestSuccess(ITestResult result){

	        test.log(Status.PASS, "Test Case Passed");

	    }

	    @Override
	    public void onTestFailure(ITestResult result){

	        test.fail(result.getThrowable());

	        try {
	            driver= (WebDriver) result.getTestClass().getRealClass().getField("driver")
	                    .get(result.getInstance());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        String filePath = null;
	        try {
	            filePath = getScreenshot(result.getMethod().getMethodName());
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	        //Screenshot, Attach to report
	        test.addScreenCaptureFromPath(filePath , result.getMethod().getMethodName());

	    }

	    @Override
	    public void onTestSkipped(ITestResult result){


	    }

	    @Override
	    public void onFinish(ITestContext context){
	        extent.flush();
	    }


}
