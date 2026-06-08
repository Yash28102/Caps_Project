package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver initDriver(String browser) {

        boolean isJenkins = System.getenv("JENKINS_HOME") != null;

        if (browser.equalsIgnoreCase("edge")) {

            System.setProperty(
                    "webdriver.edge.driver",
                    "C:\\Drivers\\msedgedriver.exe"
            );

            EdgeOptions options = new EdgeOptions();

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-blink-features=AutomationControlled");

            if (isJenkins) {
                System.out.println("Running Edge in Jenkins Headless Mode");

                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            } else {
                System.out.println("Running Edge in Local Mode");

                options.addArguments("--start-maximized");
            }

            driver = new EdgeDriver(options);

        }

        else if (browser.equalsIgnoreCase("chrome")) {

            System.setProperty(
                    "webdriver.chrome.driver",
                    "C:\\Drivers\\chromedriver.exe"
            );

            ChromeOptions options = new ChromeOptions();

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-blink-features=AutomationControlled");

            if (isJenkins) {
                System.out.println("Running Chrome in Jenkins Headless Mode");

                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            } else {
                System.out.println("Running Chrome in Local Mode");

                options.addArguments("--start-maximized");
            }

            driver = new ChromeDriver(options);

        }

        else {
            throw new RuntimeException("Unsupported Browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
