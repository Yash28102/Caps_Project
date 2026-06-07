package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WithdrawalPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='WithdrawalInput.php']")
    private WebElement lnkWithdrawal;

    @FindBy(name = "accountno")
    private WebElement account;

    @FindBy(name = "ammount")
    private WebElement amount;

    @FindBy(name = "desc")
    private WebElement desc;

    @FindBy(name = "AccSubmit")
    private WebElement submitBtn;

    public WithdrawalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String performWithdrawal(String accNo, String amt, String description) {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(lnkWithdrawal)).click();
        } catch (Exception e) {
            driver.get("https://demo.guru99.com/V4/manager/WithdrawalInput.php");
        }

        wait.until(ExpectedConditions.visibilityOf(account)).clear();
        account.sendKeys(accNo);

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

        // Return a confirmation signal, like current URL or page message
        return driver.getCurrentUrl();
    }
}