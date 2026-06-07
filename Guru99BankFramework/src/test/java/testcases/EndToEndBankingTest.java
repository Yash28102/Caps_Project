package testcases;

import base.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utilities.ConfigReader;
import utilities.ScreenshotUtil;

@Listeners(base.TestListener.class)
public class EndToEndBankingTest extends BaseTest {

    // ------------------- ADDED FOR 2 CUSTOMERS -------------------
    private String customerId1;
    private String customerId2;
    private String accountId1;
    private String accountId2;

    // ------------------- UTILITIES -------------------

    private void handleAlertSafely() {
        try {
            Alert alert = driver.switchTo().alert();
            System.out.println("[ALERT DETECTED]: " + alert.getText());
            alert.accept();
        } catch (NoAlertPresentException ignored) {}
    }

    private void goToHome() {
        try {
            driver.get("https://demo.guru99.com/V4/manager/Managerhomepage.php");
            handleAlertSafely();
        } catch (Exception e) {
            driver.navigate().refresh();
        }
    }

    // ------------------- TEST CASES -------------------

    @Test(priority = 1, description = "LOGIN")
    public void testLoginModule() {
        LoginPage lp = new LoginPage(driver);
        lp.login(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
       // ScreenshotUtil.takeScreenshot(driver, "Login");
        handleAlertSafely();
        goToHome();
    }

    // ------------------- CREATE CUSTOMER 1 -------------------
    @Test(priority = 2, description = "CREATE CUSTOMER 1")
    public void testNewCustomer1Module() {

        goToHome();

        CustomerPage cp = new CustomerPage(driver);
        cp.clickNewCustomer();

        String uniqueEmail1 = "yash" + System.currentTimeMillis() + "@guru99.com";

        customerId1 = cp.createCustomer(
                "Jane Doe Corporate",
                "14051992",
                "75 Innovation Way",
                "Austin",
                "Texas",
                "733011",
                "9876543210",
                uniqueEmail1,
                "yash123"
        );
       // ScreenshotUtil.takeScreenshot(driver, "NewCustomer1");
        handleAlertSafely();

        // CREATE ACCOUNT 1 FOR CUSTOMER 1
        AccountPage ap = new AccountPage(driver);
        ap.clickNewAccount();
        accountId1 = ap.createAccount(customerId1, "Savings", "8500");

        handleAlertSafely();

        System.out.println("Customer 1 ID: " + customerId1);
        System.out.println("Account 1 ID: " + accountId1);

        Assert.assertNotNull(customerId1);
        Assert.assertNotNull(accountId1);
       // ScreenshotUtil.takeScreenshot(driver, "NewAccount1");
    }

    // ------------------- CREATE CUSTOMER 2 -------------------
    @Test(priority = 3, description = "CREATE CUSTOMER 2")
    public void testNewCustomer2Module() {

        goToHome();

        CustomerPage cp = new CustomerPage(driver);
        cp.clickNewCustomer();

        String uniqueEmail2 = "garg" + System.currentTimeMillis() + "@guru99.com";

        customerId2 = cp.createCustomer(
                "Pablo Picaso",
                "14051993",
                "75 Innovation Way",
                "Austin",
                "Texas",
                "733011",
                "9876543220",
                uniqueEmail2,
                "garg123"
        );
        //ScreenshotUtil.takeScreenshot(driver, "NewCustomer2");
        handleAlertSafely();

        // CREATE ACCOUNT 2 FOR CUSTOMER 2
        AccountPage ap = new AccountPage(driver);
        ap.clickNewAccount();
        accountId2 = ap.createAccount(customerId2, "Savings", "8500");

        handleAlertSafely();

        System.out.println("Customer 2 ID: " + customerId2);
        System.out.println("Account 2 ID: " + accountId2);

        Assert.assertNotNull(customerId2);
        Assert.assertNotNull(accountId2);
        //ScreenshotUtil.takeScreenshot(driver, "NewAccount2");
    }

    // ------------------- OTHER TEST CASES -------------------
    @Test(priority = 4, description = "DEPOSIT")
    public void testDepositModule() {
        goToHome();
        DepositPage dp = new DepositPage(driver);
        String depositMsg = dp.performDeposit(accountId1, "3000", "Framework Initial Topup");
        handleAlertSafely();
        Assert.assertNotNull(depositMsg, "Deposit message should not be null");
    }

    @Test(priority = 5, description = "WITHDRAWAL")
    public void testWithdrawalModule() {
        goToHome();
        WithdrawalPage wp = new WithdrawalPage(driver);
        String withdrawalMsg = wp.performWithdrawal(accountId1, "1500", "Automated Cashout");
        handleAlertSafely();
        Assert.assertNotNull(withdrawalMsg, "Withdrawal message should not be null");
    }

    @Test(priority = 6, description = "FUND TRANSFER")
    public void testFundTransferModule() {
        goToHome();
        TransferPage tp = new TransferPage(driver);
        String transferMsg = tp.fundTransfer(accountId1, accountId2, "500", "Sandbox Transfer");
        handleAlertSafely();
        Assert.assertNotNull(transferMsg, "Transfer message should not be null");
    }

   /* @Test(priority = 7, description = "BALANCE ENQUIRY")
    public void testBalanceEnquiryModule() {
        goToHome();
        BalanceEnquiryPage bep = new BalanceEnquiryPage(driver);
        String balance = bep.checkBalance("183222");
        handleAlertSafely();
        Assert.assertNotNull(balance, "Balance should not be null");
    }

    @Test(priority = 8, description = "MINI STATEMENT")
    public void testMiniStatementModule() {
        goToHome();
        MiniStatementPage msp = new MiniStatementPage(driver);
        String miniMsg = msp.getMiniStatement("183222");
        handleAlertSafely();
        Assert.assertNotNull(miniMsg, "Mini statement should not be null");
    }*/

    @Test(priority = 9, description = "CUSTOM STATEMENT")
    public void testCustomStatementModule() {
        goToHome();
        CustomStatementPage csp = new CustomStatementPage(driver);
        String customMsg = csp.getCustomStatement(accountId1, "01012025", "01012027", "100", "5");
        handleAlertSafely();
        Assert.assertNotNull(customMsg, "Custom statement should not be null");
    }

    @Test(priority = 10, description = "EDIT ACCOUNT")
    public void testEditAccountModule() {
        goToHome();
        driver.get("https://demo.guru99.com/V4/manager/editAccount.php?accountno=" + accountId1);
        handleAlertSafely();
        Assert.assertTrue(driver.getTitle().contains("Edit Account"));
    }

    @Test(priority = 11, description = "CHANGE PASSWORD")
    public void testChangePasswordModule() {
        goToHome();
        ChangePasswordPage cpp = new ChangePasswordPage(driver);
        cpp.navigate();  // Open Change Password page

        handleAlertSafely();

        String oldPwd = "umybyny";   // current password
        String newPwd = "umybyny";   // new password
        cpp.changePassword(oldPwd, newPwd);  // automatically fills confirm

        handleAlertSafely();
        System.out.println("Password changed from " + oldPwd + " to " + newPwd);
    }

    @Test(priority = 12, description = "DELETE ACCOUNT")
    public void testDeleteAccountModule() {
        goToHome();
        driver.get("https://demo.guru99.com/V4/manager/deleteAccountInput.php?accountno=" + accountId1);
        handleAlertSafely();
        driver.get("https://demo.guru99.com/V4/manager/deleteAccountInput.php?accountno=" + accountId2);
        handleAlertSafely();
        Assert.assertTrue(driver.getTitle().contains("Delete Account"));
    }

    @Test(priority = 13, description = "DELETE CUSTOMER")
    public void testDeleteCustomerModule() {
        goToHome();
        driver.get("https://demo.guru99.com/V4/manager/DeleteCustomerInput.php?cusid=" + customerId1);
        handleAlertSafely();
        driver.get("https://demo.guru99.com/V4/manager/DeleteCustomerInput.php?cusid=" + customerId2);
        handleAlertSafely();
        Assert.assertTrue(driver.getTitle().contains("Delete Customer"));
    }

    @Test(priority = 14, description = "LOGOUT")
    public void testLogoutModule() {
        goToHome();
        LogoutPage lop = new LogoutPage(driver);
        lop.logout();
        handleAlertSafely();
        Assert.assertTrue(driver.getTitle().contains("Guru99 Bank"));
    }
}