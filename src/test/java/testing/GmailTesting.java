package testing;

import SECRET.Secret;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.WebDriverFactory;

import java.util.concurrent.TimeUnit;

public class GmailTesting {
    WebDriver webDriver;

    @BeforeMethod
    public void setUp() {
        webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    @Test
    public void test1() {
        webDriver.navigate().to("https://gmail.com");

        WebElement email = webDriver.findElement(By.cssSelector("input[type='email']"));
        email.click();
        email.sendKeys(Secret.email);

        WebElement next = webDriver.findElement(By.cssSelector("div#identifierNext"));
        next.click();

        // as a result we cannot sign in Gmail  because of the security
    }
}
