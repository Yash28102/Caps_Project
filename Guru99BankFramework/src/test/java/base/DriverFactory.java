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

    if (browser.equalsIgnoreCase("edge")) {

        // Path to your EdgeDriver executable
        System.setProperty(
                "webdriver.edge.driver",
                "C:\\Drivers\\msedgedriver.exe"
        );

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new");
    options.addArguments("--disable-gpu");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--disable-blink-features=AutomationControlled");
    options.addArguments("--window-size=1920,1080");
        driver = new EdgeDriver(options);

    } else if (browser.equalsIgnoreCase("chrome")) {

        // Path to ChromeDriver (optional if using WebDriverManager)
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Drivers\\chromedriver.exe"
        );

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

    } else {
        throw new RuntimeException("Unsupported Browser: " + browser);
    }

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

    return driver;
}

public static void quitDriver() {
    if (driver != null) {
        driver.quit();
        driver = null;
    }
}
}
