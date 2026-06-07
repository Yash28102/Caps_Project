package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LogoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='Logout.php']")
    private WebElement logoutLink;

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Executes session termination by triggering the logout link 
     * via JavaScript execution to bypass overlapping ad layouts.
     */
    public void logout() {
        // Step 1: Ensure the logout link is attached to the DOM and clickable
        try {
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        } catch (Exception e) {
            // Fallback: If previous page state distorted the sidebar DOM, navigate directly to logout URL
            driver.get("https://demo.guru99.com/V4/manager/Logout.php");
        }

        // Step 2: Trigger click using JavaScript Executor to avoid ElementClickInterceptedException
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutLink);
        } catch (Exception e) {
            // Direct fall-through protection if session already invalidated
        }

        // Step 3: Short sync delay to allow the application server to push the logout alert popup
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}