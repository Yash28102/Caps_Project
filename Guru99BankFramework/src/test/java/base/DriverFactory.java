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

        if (browser == null || browser.isEmpty()) {
            browser = "edge"; // default browser
        }

        switch (browser.toLowerCase()) {

            case "edge":

                System.setProperty(
                        "webdriver.edge.driver",
                        "C:\\Drivers\\msedgedriver.exe"
                );

                EdgeOptions edgeOptions = new EdgeOptions();

                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.addArguments("--disable-blink-features=AutomationControlled");

                if (isJenkins) {
                    System.out.println("Running Edge in Jenkins Headless Mode");

                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--disable-gpu");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                    edgeOptions.addArguments("--window-size=1920,1080");

                } else {
                    System.out.println("Running Edge in Local Mode");
                    edgeOptions.addArguments("--start-maximized");
                }

                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":

                System.setProperty(
                        "webdriver.chrome.driver",
                        "C:\\Drivers\\chromedriver.exe"
                );

                ChromeOptions chromeOptions = new ChromeOptions();

                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");

                if (isJenkins) {
                    System.out.println("Running Chrome in Jenkins Headless Mode");

                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--window-size=1920,1080");

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
