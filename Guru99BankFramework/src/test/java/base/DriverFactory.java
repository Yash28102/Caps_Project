package base;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver initDriver(String browser) {

        boolean isJenkins = System.getenv("JENKINS_HOME") != null;
        boolean isDocker = new File("/.dockerenv").exists();

        if (browser == null || browser.trim().isEmpty()) {
            browser = "chrome";
        }

        switch (browser.toLowerCase()) {

        case "chrome":

            ChromeOptions chromeOptions = new ChromeOptions();

            // General options
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--disable-infobars");
            chromeOptions.setAcceptInsecureCerts(true);

            // Docker/Jenkins/Linux Headless
            if (isJenkins || isDocker) {

                System.out.println("Running Chrome in Docker/Jenkins Headless Mode");

                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");

                // Uncomment if your Docker image requires it
                // chromeOptions.setBinary("/usr/bin/google-chrome");

            } else {

                System.out.println("Running Chrome in Local Mode");

                chromeOptions.addArguments("--start-maximized");
            }

            driver = new ChromeDriver(chromeOptions);
            break;

        default:
            throw new RuntimeException("Unsupported Browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Driver already closed.");
            } finally {
                driver = null;
            }
        }
    }
}
