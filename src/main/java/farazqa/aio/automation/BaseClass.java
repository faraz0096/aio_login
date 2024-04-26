package farazqa.aio.automation;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class BaseClass {
	
	
	 WebDriver driver;

	    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

	      TakesScreenshot ts=   ((TakesScreenshot)driver);
	      File source =  ts.getScreenshotAs(OutputType.FILE);
	      File file = new File(System.getProperty("user.dir")+"\\reports"+testCaseName+".png");
	      FileUtils.copyFile(source, file);
	      return System.getProperty("user.dir")+"\\reports"+testCaseName+".png";

	    }

}
