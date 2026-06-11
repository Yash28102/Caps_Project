package testcases;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CustomerPage;
import pages.LoginPage;

public class Failures extends BaseTest {
	private void loginToApplication() {

	    driver.get("https://demo.guru99.com/V4/");

	    LoginPage login = new LoginPage(driver);
	    login.login(validUser, validPassword);

	    handleAlertIfPresent();

	    new WebDriverWait(driver, Duration.ofSeconds(10))
	            .until(ExpectedConditions.titleContains("Guru99 Bank"));
	}

    private final String validUser = "mngr662552";
    private final String validPassword = "umybyny";

    private void handleAlertIfPresent() {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (TimeoutException e) {
        }
    }

    // Valid Login

    @Test(priority = 1)
    public void validLogin() {

        driver.get("https://demo.guru99.com/V4/");

        LoginPage login = new LoginPage(driver);
        login.login(validUser, validPassword);

        handleAlertIfPresent();

        Assert.assertTrue(
                driver.getTitle().contains("Guru99 Bank"),
                "Login failed");
    }

    // Invalid Login

    @Test(priority = 2)
    public void invalidLoginTest() {

        driver.get("https://demo.guru99.com/V4/");

        LoginPage login = new LoginPage(driver);
        login.login("wronguser", "wrongpass");

        String error =
                driver.findElement(
                        By.id("message23"))
                        .getText();

        Assert.assertTrue(
                error.contains("Characters"));
    }

    // invalid email

   @Test(priority = 4)
  public void invalidemail() {

    	    loginToApplication();

    	    CustomerPage customer = new CustomerPage(driver);

    	    customer.clickNewCustomer();

        customer.createCustomer(
                "John",
                "01-01-1990",
                "Delhi Address",
                "Delhi",
                "Delhi",
                "110001",
                "9876543210",
                "email",
                "pass123");

        String error =
                driver.findElement(
                        By.id("message9"))
                        .getText();

        Assert.assertTrue(
                error.contains("Characters"));
    }

    // Invalid Customer Name

   @Test(priority = 5)
   public void invalidCustomerName() throws InterruptedException {

       loginToApplication();

       CustomerPage customer = new CustomerPage(driver);
       customer.clickNewCustomer();

       // Scroll down
       JavascriptExecutor js = (JavascriptExecutor) driver;
       js.executeScript("window.scrollBy(0,400)");

       customer.createCustomer(
               "12345",
               "01-01-1990",
               "Delhi",
               "Delhi",
               "Delhi",
               "110001",
               "9876543210",
               "test" + System.currentTimeMillis() + "@gmail.com",
               "pass123");
       Thread.sleep(2000);
       // Scroll further if needed
       js.executeScript("window.scrollBy(0,-900)");
       Thread.sleep(3000);       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
       String error = wait.until(
               ExpectedConditions.visibilityOfElementLocated(
                       By.id("message")))
               .getText();

       Assert.assertTrue(error.contains("Numbers"));
   }

    // Invalid PIN

    @Test(priority = 6)
    public void invalidPin() {
    	loginToApplication();
        CustomerPage customer =
                new CustomerPage(driver);

        customer.clickNewCustomer();

        customer.createCustomer(
                "John",
                "01-01-1990",
                "Delhi",
                "Delhi",
                "Delhi",
                "ABC12",
                "9876543210",
                "pin"
                        + System.currentTimeMillis()
                        + "@gmail.com",
                "pass123");

        String error =
                driver.findElement(
                        By.id("message6"))
                        .getText();

        Assert.assertTrue(
                error.contains("Characters"));
    }

    // Invalid Mobile

    @Test(priority = 7)
    public void invalidMobile() {
    	loginToApplication();
        CustomerPage customer =
                new CustomerPage(driver);

        customer.clickNewCustomer();

        customer.createCustomer(
                "John",
                "01-01-1990",
                "Delhi",
                "Delhi",
                "Delhi",
                "110001",
                "abcd1234",
                "mobile"
                        + System.currentTimeMillis()
                        + "@gmail.com",
                "pass123");

        String error =
                driver.findElement(
                        By.id("message7"))
                        .getText();

        Assert.assertTrue(
                error.contains("Characters"));
    }


}
