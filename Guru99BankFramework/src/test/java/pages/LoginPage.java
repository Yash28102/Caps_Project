package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    // Strategic use of attributes natively exposed by the form markup
    @FindBy(name = "uid") private WebElement uid;
    @FindBy(name = "password") private WebElement pwd;
    @FindBy(xpath = "//input[@type='submit']") private WebElement btn;

    public LoginPage(WebDriver d) { PageFactory.initElements(d, this); }
    public void login(String u, String p) { uid.sendKeys(u); pwd.sendKeys(p); btn.click(); }
}