package base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import utilities.ConfigReader;
import utilities.ExtentManager;
import utilities.LoggerManager;
import utilities.ScreenshotUtil;

import org.apache.logging.log4j.Logger;

public class BaseTest {

    protected WebDriver driver;
    private static final Logger log = LoggerManager.getLogger(BaseTest.class);

    // Global IDs to share across tests if needed
    public static String generatedCustomerId = "";
    public static String generatedAccountId = "";

    // ----------------- Suite Level Hooks -----------------
    @BeforeSuite(description = "Global Suite Setup Hook")
    public void globalSetupHook() {
        log.info(">>>> INITIATING GLOBAL SUITE EXECUTION HOOKS <<<<");
        ExtentManager.getInstance(); // Initialize ExtentReports
    }

    @AfterSuite(description = "Global Suite Teardown Hook")
    public void globalTeardownHook() {
        log.info(">>>> FLUSHING ENGINE METRICS AND REPORTS <<<<");
        if (ExtentManager.getInstance() != null) {
            ExtentManager.getInstance().flush();
        }
    }

    // ----------------- Class Level Hooks -----------------
    @BeforeClass(description = "Class Browser Instantiation Hook")
    public void classSetupHook() {
        log.info("Executing Hook: Instantiating web driver environment.");

        String targetBrowser = ConfigReader.getProperty("browser");
        String targetUrl = ConfigReader.getProperty("url");

        log.info("Selected Browser: {}", targetBrowser);
        log.info("Target URL: {}", targetUrl);

        driver = DriverFactory.initDriver(targetBrowser);

        if (driver == null) {
            throw new RuntimeException("Driver initialization failed. Check DriverFactory configuration.");
        }

        driver.get(targetUrl);
        log.info("Application launched successfully.");
    }

    @AfterClass(description = "Class Browser Teardown Hook")
    public void classTeardownHook() {
        log.info("Executing Hook: Tearing down active web driver session.");
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    // ----------------- Method Level Hook: Screenshot -----------------
    @AfterMethod
    public void captureSS(ITestResult result) {
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.takeScreenshot(driver, result.getName());

            try {
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                // no alert
            }
        }
    }
    // ----------------- Getter for WebDriver -----------------
    public WebDriver getDriver() {
        return driver;
    }
}