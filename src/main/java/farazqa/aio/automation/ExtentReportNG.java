package farazqa.aio.automation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {
	
	
	 public static ExtentReports getReportObject()
	    {
	        String path = System.getProperty("user.dir")+"\\reports\\index.html";
	        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
	        reporter.config().setReportName("AIO Login Test Results");
	        reporter.config().setDocumentTitle("AIO Login");
	        ExtentReports extent = new ExtentReports();
	        extent.attachReporter(reporter);
	        extent.setSystemInfo("Automation Tester", "Faraz Shaikh");
	        return extent;


	    }

}
