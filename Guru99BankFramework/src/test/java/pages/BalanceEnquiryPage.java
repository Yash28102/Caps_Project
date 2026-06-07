package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BalanceEnquiryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='BalEnqInput.php']")
    private WebElement lnkBalance;

    @FindBy(name = "accountno")
    private WebElement acc;

    @FindBy(name = "AccSubmit")
    private WebElement submit;

    @FindBy(xpath = "//table[@id='balenquiry']//tr[2]/td[2]")
    private WebElement balanceResult;

    public BalanceEnquiryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Navigate + fill + submit + return result
    public String checkBalance(String accNo) {

        // Step 1: Navigate safely
        try {
            wait.until(ExpectedConditions.elementToBeClickable(lnkBalance)).click();
        } catch (Exception e) {
            driver.get("https://demo.guru99.com/V4/manager/BalEnqInput.php");
        }

        // Step 2: Validate input
        if (accNo == null || accNo.isEmpty()) {
            throw new RuntimeException("Account number must not be empty");
        }

        // Step 3: Wait until input is visible and enabled
        wait.until(ExpectedConditions.visibilityOf(acc));
        wait.until(ExpectedConditions.elementToBeClickable(acc));

        // Clear and type account number
        acc.clear();
        acc.sendKeys(accNo);

        // Step 4: Click submit
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();

        // Step 5: Wait for result page and return balance text
        try {
            wait.until(ExpectedConditions.visibilityOf(balanceResult));
            return balanceResult.getText();
        } catch (Exception e) {
            throw new RuntimeException("Balance enquiry failed or result page not loaded properly");
        }
    }
}