package testcases;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utilities.ScreenshotUtil;
import pages.CustomerPage;

public class Failures extends BaseTest {

    // Valid manager credentials from your config
    private String validUser = "mngr662552";
    private String validPassword = "umybyny";

    // ------------------ STEP 1: Invalid login screenshot ------------------
    @Test(priority = 1)
    public void invalidLoginScreenshot() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        
        login.login("mngr12345r", "wrpasss"); // triggers alert

        // Wait explicitly for alert
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Optional: small pause to ensure alert fully renders
        Thread.sleep(1000);

        // Take screenshot immediately while alert is visible
        ScreenshotUtil.takeScreenshot(driver, "InvalidLogin_ALERT");

        // Accept the alert so test can continue
        alert.accept();

        // Fail the test to mark it as negative
        Assert.fail("Invalid login triggered alert");
    }
    // ------------------ STEP 2: Valid login ------------------
    @Test(priority = 2)
    public void validLogin() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        login.login(validUser, validPassword);

        Thread.sleep(3000); // wait for page to fully load

        Assert.assertEquals(driver.getTitle(), "Guru99 Bank Manager HomePage");
    }

    // ------------------ STEP 3: Negative Customer Test Cases ------------------

    @Test(priority = 3, dependsOnMethods = "validLogin")
    public void customerNameWithIntegers() throws InterruptedException {
        CustomerPage customer = new CustomerPage(driver);
        customer.clickNewCustomer();
        Thread.sleep(3000);

        customer.createCustomer(
                "12345", // invalid numeric name
                "01-01-1990",
                "Some Address",
                "City",
                "State",
                "123456",
                "9876543210",
                "test@example.com",
                "password"
        );

        Thread.sleep(3000);
        Assert.fail("Customer name cannot contain numbers");
    }

    @Test(priority = 4, dependsOnMethods = "validLogin")
    public void invalidPinTest() throws InterruptedException {
        CustomerPage customer = new CustomerPage(driver);
        customer.clickNewCustomer();
        Thread.sleep(3000);

        customer.createCustomer(
                "John Doe",
                "01-01-1990",
                "Some Address",
                "City",
                "State",
                "ABCDE", // invalid PIN
                "9876543210",
                "test@example.com",
                "password"
        );

        Thread.sleep(3000);
        Assert.fail("PIN should be numeric only");
    }

    @Test(priority = 5, dependsOnMethods = "validLogin")
    public void blankEmailTest() throws InterruptedException {
        CustomerPage customer = new CustomerPage(driver);
        customer.clickNewCustomer();
        Thread.sleep(3000);

        customer.createCustomer(
                "John Doe",
                "01-01-1990",
                "Some Address",
                "City",
                "State",
                "123456",
                "9876543210",
                "", // blank email
                "password"
        );

        Thread.sleep(3000);
        Assert.fail("Email cannot be blank");
    }

    @Test(priority = 6, dependsOnMethods = "validLogin")
    public void invalidMobileTest() throws InterruptedException {
        CustomerPage customer = new CustomerPage(driver);
        customer.clickNewCustomer();
        Thread.sleep(3000);

        customer.createCustomer(
                "John Doe",
                "01-01-1990",
                "Some Address",
                "City",
                "State",
                "123456",
                "abcd1234", // invalid mobile
                "test@example.com",
                "password"
        );

        Thread.sleep(3000);
        Assert.fail("Mobile number must be numeric");
    }

    @Test(priority = 7, dependsOnMethods = "validLogin")
    public void invalidCityStateTest() throws InterruptedException {
        CustomerPage customer = new CustomerPage(driver);
        customer.clickNewCustomer();
        Thread.sleep(3000);

        customer.createCustomer(
                "John Doe",
                "01-01-1990",
                "Some Address",
                "123City", // invalid city
                "!@#State", // invalid state
                "123456",
                "9876543210",
                "test@example.com",
                "password"
        );

        Thread.sleep(3000);
        Assert.fail("City and State should not contain numbers or symbols");
    }
}