package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TransferPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='FundTransInput.php']")
    private WebElement lnkFundTransfer;

    @FindBy(name = "payersaccount")
    private WebElement payerAccount;

    @FindBy(name = "payeeaccount")
    private WebElement payeeAccount;

    @FindBy(name = "ammount")
    private WebElement amount;

    @FindBy(name = "desc")
    private WebElement desc;

    @FindBy(name = "AccSubmit")
    private WebElement submitBtn;

    public TransferPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String fundTransfer(String payerAcc, String payeeAcc, String amt, String description) {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(lnkFundTransfer)).click();
        } catch (Exception e) {
            driver.get("https://demo.guru99.com/V4/manager/FundTransInput.php");
        }

        wait.until(ExpectedConditions.visibilityOf(payerAccount)).clear();
        payerAccount.sendKeys(payerAcc);

        wait.until(ExpectedConditions.visibilityOf(payeeAccount)).clear();
        payeeAccount.sendKeys(payeeAcc);

        wait.until(ExpectedConditions.visibilityOf(amount)).clear();
        amount.sendKeys(amt);

        wait.until(ExpectedConditions.visibilityOf(desc)).clear();
        desc.sendKeys(description);

        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Return current URL or confirmation message
        return driver.getCurrentUrl();
    }
}