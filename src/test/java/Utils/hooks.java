package Utils;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class hooks {

    WebDriver driver;
    ExtentTest test;

    @BeforeClass
    public void setUpClass() {
        // Initialize the WebDriver before all tests
        driver = driverManager.getDriver();
    }

    @BeforeMethod
    public void setUpTest(ITestResult result) {
        // Initialize ExtentTest for each test method
        test = ExtentReportManager.getExtentReports().createTest(result.getMethod().getMethodName());
        ExtentReportManager.setTest(test);
    }

    @AfterMethod
    public void tearDownTest(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot on failure
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Format timestamp for file name
            String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());

            try {
                // Define screenshot path
                String screenshotPath = "D://Cucumber7.xTestNGLatestPOC-master//Task1//src//test//java//screenshots"
                        + result.getMethod().getMethodName() + "_" + timestamp + ".png";
                Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
                System.out.println("Screenshot saved at: " + screenshotPath);

                // Attach screenshot to Extent report
                byte[] fileContent = Files.readAllBytes(screenshot.toPath());
                test.fail(result.getMethod().getMethodName() + " failed")
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            test.pass(result.getMethod().getMethodName() + " passed");
        }


        ExtentReportManager.flush();
    }

    @AfterClass
    public void tearDownClass() {

        driverManager.closeDriver();
    }
}
