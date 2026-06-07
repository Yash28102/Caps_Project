package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomerPage {

    WebDriver driver;
    WebDriverWait wait;

    public CustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ------------------- LOCATORS -------------------

    private By newCustomerLink = By.xpath("//a[text()='New Customer']");

    private By nameField = By.name("name");
    private By genderMale = By.xpath("//input[@value='m']");
    private By dobField = By.id("dob");

    private By addressField = By.name("addr");
    private By cityField = By.name("city");
    private By stateField = By.name("state");
    private By pinField = By.name("pinno");
    private By mobileField = By.name("telephoneno");
    private By emailField = By.name("emailid");
    private By passwordField = By.name("password");

    private By submitBtn = By.name("sub");

    // Correct locator for Customer ID (Guru99 page)
    private By customerIdText =
            By.xpath("//td[text()='Customer ID']/following-sibling::td");

    // ------------------- ACTIONS -------------------

    public void clickNewCustomer() {
        wait.until(ExpectedConditions.elementToBeClickable(newCustomerLink)).click();
    }

    public String createCustomer(
            String name,
            String dob,
            String address,
            String city,
            String state,
            String pin,
            String mobile,
            String email,
            String password
    ) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);

        driver.findElement(genderMale).click();

        driver.findElement(dobField).sendKeys(dob);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(stateField).sendKeys(state);
        driver.findElement(pinField).sendKeys(pin);
        driver.findElement(mobileField).sendKeys(mobile);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);

        driver.findElement(submitBtn).click();

        // Wait for success page and extract Customer ID
        WebElement id = wait.until(
                ExpectedConditions.visibilityOfElementLocated(customerIdText)
        );

        return id.getText();
    }
}