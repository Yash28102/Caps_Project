package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MiniStatementPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By lnkMiniStatement = By.xpath("//a[@href='MiniStatementInput.php']");
    private By accField = By.id("accountno");
    private By submitBtn = By.name("AccSubmit");
    private By resultTable = By.xpath("//table[@id='mini']//tr");

    public MiniStatementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Navigate to Mini Statement page, fill account number, submit, and return result
     */
    public String getMiniStatement(String accountId) {

        if (accountId == null || accountId.isEmpty()) {
            throw new RuntimeException("Account ID must be provided for Mini Statement.");
        }

        // Navigate safely
        try {
            wait.until(ExpectedConditions.elementToBeClickable(lnkMiniStatement)).click();
        } catch (Exception e) {
            driver.get("https://demo.guru99.com/V4/manager/MiniStatementInput.php");
        }

        // Fill account number
        WebElement accInput = wait.until(ExpectedConditions.visibilityOfElementLocated(accField));
        accInput.clear();
        accInput.sendKeys(accountId);

        // Submit form
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();

        // Wait for result table
        try {
            WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(resultTable));
            return table.getText();  // returns the text of the first row (or entire table if you want)
        } catch (Exception e) {
            throw new RuntimeException("Mini Statement retrieval failed or page not loaded properly.");
        }
    }
}