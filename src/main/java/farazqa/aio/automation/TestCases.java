package farazqa.aio.automation;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

public class TestCases {

    public ExtentReports extent;
   public WebDriver driver;
    public  String savedSuccessfullyChangeLoginURL = "Change Login URL settings saved successfully";
    public  String adminPage = "Log In ‹ wordpress-1077016-4396807.cloudwaysapps.com — WordPress";
    public   String LimitLoginSavedSuccess = "Limit Login Attempts settings saved successfully.";




    @BeforeMethod
    public void Launch() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    @Test
    public void VerifyChangeURL() throws InterruptedException, IOException {

        driver.get("https://wordpress-1077016-4396807.cloudwaysapps.com/wp-admin");
        driver.findElement(By.id("user_login")).sendKeys("farazshaikh.objects@gmail.com");
        driver.findElement(By.id("user_pass")).sendKeys("faraz0096");
        driver.findElement(By.id("wp-submit")).click();
        driver.findElement(By.xpath("//li[@id='toplevel_page_aio-login']")).click();
        driver.findElement(By.xpath("//a[@href='admin.php?page=aio-login&tab=login-protection']")).click();
        driver.findElement(By.linkText("Change Login URL")).click();
        driver.findElement(By.cssSelector("label.aio-login__toggle-switch")).click();
        driver.findElement(By.id("rwl-page-input")).clear();
        driver.findElement(By.id("rwl-page-input")).sendKeys("login");
        driver.findElement(By.id("rwl_redirect_field")).clear();
        driver.findElement(By.id("rwl_redirect_field")).sendKeys("my-account");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        Assert.assertEquals(savedSuccessfullyChangeLoginURL, driver.findElement(By.xpath("//div[@class='toast-body']")).getText(), "Change Login URL Saved Successfully");
        Thread.sleep(2000);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver.get("https://wordpress-1077016-4396807.cloudwaysapps.com/login");
        Assert.assertEquals(adminPage, driver.getTitle());

    }

    @Test
    public void VerifyLimitLoginAttempts() {
        driver.get("https://wordpress-1077016-4396807.cloudwaysapps.com/login");
        driver.findElement(By.id("user_login")).sendKeys("farazshaikh.objects@gmail.com");
        driver.findElement(By.id("user_pass")).sendKeys("faraz0096");
        driver.findElement(By.id("wp-submit")).click();
        driver.findElement(By.xpath("//li[@id='toplevel_page_aio-login']")).click();
        driver.findElement(By.xpath("//a[@href='admin.php?page=aio-login&tab=login-protection']")).click();
        driver.findElement(By.linkText("Limit Login Attempts")).click();
        driver.findElement(By.cssSelector("label[for='aio_login_limit_attempts_enable'] span")).click();
        driver.findElement(By.id("aio_login_limit_attempts_maximum_attempts")).clear();
        driver.findElement(By.id("aio_login_limit_attempts_maximum_attempts")).sendKeys("5");
        driver.findElement(By.id("aio_login_limit_attempts_timeout")).clear();
        driver.findElement(By.id("aio_login_limit_attempts_timeout")).sendKeys("1");
        driver.findElement(By.cssSelector("input[value='Save Changes']")).click();
        Assert.assertEquals(LimitLoginSavedSuccess, driver.findElement(By.xpath("//div[@class='toast-body']")).getText(), "Limit Login Attempts Saved Successfully");
        int getMaxAttempts = Integer.parseInt(driver.findElement(By.id("aio_login_limit_attempts_maximum_attempts")).getAttribute("value"));
        int getMaxTimeOuts = Integer.parseInt(driver.findElement(By.id("aio_login_limit_attempts_timeout")).getAttribute("value"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver.get("https://wordpress-1077016-4396807.cloudwaysapps.com/login");

        int i = 1;
        int previousAttempts = -1;

        while (i < getMaxAttempts) {
            driver.findElement(By.id("user_login")).sendKeys("farazshaikh.objects@gmail.com");
            driver.findElement(By.id("user_pass")).sendKeys("faraz009");
            driver.findElement(By.id("wp-submit")).click();
            i++;

            // Get the current number of attempts from user
            int getUserAttempts = Integer.parseInt(driver.findElement(By.cssSelector("div[class='notice notice-error'] b")).
                    getText().split("have")[1].split("attempts")[0].trim());

            // Check if attempts are properly decreasing
            if (previousAttempts != -1) { // Skip the first iteration
                if (getUserAttempts == previousAttempts - 1) {
                    System.out.println("Attempts are properly decreasing.");
                } else {
                    System.out.println("Attempts are not properly decreasing.");
                }
            }

            // Update the previousAttempts for the next iteration
            previousAttempts = getUserAttempts;

        }
    }


   @Test
    public void VerifyLockOuts() {

       String expectedBlockedText = "You have been blocked due to too many unsuccessful login attempts";
       driver.get("https://wordpress-1077016-4396807.cloudwaysapps.com/login");
           driver.findElement(By.id("user_login")).sendKeys("farazshaikh.objects@gmail.com");
           driver.findElement(By.id("user_pass")).sendKeys("faraz009");
           driver.findElement(By.id("wp-submit")).click();
           String getBlockedText = driver.findElement(By.id("login_error")).getText();

           if (getBlockedText.contains(expectedBlockedText)) {

               Assert.assertTrue(true);
           }






   }


    public void TearDown(){

        driver.close();
    }

}
