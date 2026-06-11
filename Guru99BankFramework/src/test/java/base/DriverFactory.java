package base;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
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

            ChromeOptions options = new ChromeOptions();

            // General
            options.setAcceptInsecureCerts(true);
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");

            if (isJenkins || isDocker) {

                System.out.println("Running Chrome in Docker/Jenkins Headless Mode");

                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");

                options.addArguments("--disable-background-networking");
                options.addArguments("--disable-sync");
                options.addArguments("--metrics-recording-only");
                options.addArguments("--mute-audio");
                options.addArguments("--disable-default-apps");

            } else {

                System.out.println("Running Chrome in Local Mode");
                options.addArguments("--start-maximized");
            }

            driver = new ChromeDriver(options);
            break;

        default:
            throw new RuntimeException("Unsupported Browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
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
