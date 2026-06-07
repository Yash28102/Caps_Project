package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccountPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "cusid")
    private WebElement customerIdField;

    @FindBy(name = "selaccount")
    private WebElement accountTypeDropdown;

    @FindBy(name = "inideposit")
    private WebElement initialDepositField;

    @FindBy(name = "button2")
    private WebElement submitBtn;

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void clickNewAccount() {
        driver.get("https://demo.guru99.com/V4/manager/addAccount.php");
    }

    // 🔥 FIXED: Account creation requires valid Customer ID
    public String createAccount(String customerId, String type, String deposit) {
        if (customerId == null || customerId.trim().isEmpty())
            throw new RuntimeException("Customer ID must be provided");

        wait.until(ExpectedConditions.visibilityOf(customerIdField)).clear();
        customerIdField.sendKeys(customerId);

        Select select = new Select(accountTypeDropdown);
        select.selectByVisibleText(type);

        initialDepositField.clear();
        initialDepositField.sendKeys(deposit);

        submitBtn.click();

        WebElement accId = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        org.openqa.selenium.By.xpath("//table//td[text()='Account ID']/following-sibling::td")
                )
        );

        String id = accId.getText().trim();
        if (id.isEmpty()) throw new RuntimeException("Account ID not generated");
        return id;
    }
}