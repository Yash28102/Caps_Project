package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage {

    WebDriver driver;

    public ChangePasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    // open page properly
    public void navigate() {
        driver.get("https://demo.guru99.com/V4/manager/PasswordInput.php");
    }

    // change password
    public void changePassword(String oldPwd, String newPwd) {

        driver.findElement(By.name("oldpassword")).sendKeys(oldPwd);
        driver.findElement(By.name("newpassword")).sendKeys(newPwd);
        driver.findElement(By.name("confirmpassword")).sendKeys(newPwd);

        driver.findElement(By.name("sub")).click();
    }
}