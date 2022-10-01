package testing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login
{
    public WebDriver ldriver;

    public Login(WebDriver webDriver) {
        ldriver = webDriver;
        PageFactory.initElements(ldriver, this);
    }

    @FindBy(id="email")
    @CacheLookup
    WebElement email;
}
