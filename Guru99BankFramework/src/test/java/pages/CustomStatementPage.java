package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CustomStatementPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='CustomisedStatementInput.php']")
    private WebElement lnkCustom;

    @FindBy(name = "accountno")
    private WebElement acc;

    @FindBy(name = "fdate")
    private WebElement fromDate;

    @FindBy(name = "tdate")
    private WebElement toDate;

    @FindBy(name = "amountlowerlimit")
    private WebElement minTxn;

    @FindBy(name = "numtransaction")
    private WebElement numTxn;

    @FindBy(name = "AccSubmit")
    private WebElement submit;

    public CustomStatementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getCustomStatement(String accNo, String fDate, String tDate,
                                     String amount, String count) {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(lnkCustom)).click();
        } catch (Exception e) {
            driver.get("https://demo.guru99.com/V4/manager/CustomisedStatementInput.php");
        }

        wait.until(ExpectedConditions.visibilityOf(acc)).clear();
        acc.sendKeys(accNo);

        fromDate.clear();
        fromDate.sendKeys(fDate);

        toDate.clear();
        toDate.sendKeys(tDate);

        minTxn.clear();
        minTxn.sendKeys(amount);

        numTxn.clear();
        numTxn.sendKeys(count);

        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // return current page URL as confirmation signal
        return driver.getCurrentUrl();
    }
}