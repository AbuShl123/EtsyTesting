package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.WebDriverFactory;
import static utilities.TestMethods.*;

import java.util.concurrent.TimeUnit;

public class YouTubeTesting {
    public static void main(String[] args) {
        WebDriver webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        webDriver.get("https://YouTube.com");

        WebElement singIn = webDriver.findElement(By.cssSelector("div[id='end'] > div[id='buttons'] ytd-button-renderer"));

        System.out.println("singIn.isDisplayed() = " + singIn.isDisplayed());
        singIn.click();

        String actualUrl = webDriver.getCurrentUrl();
        String expectedUrl = "https://accounts.google";

        String currentTitle = webDriver.getTitle();
        String expectedTitle = "YouTube";

        System.out.println("url test: " + assertContains(expectedUrl, actualUrl));
        System.out.println("title test: " + assertEquals(expectedTitle, currentTitle));

        WebElement email = webDriver.findElement(By.cssSelector("input[type='email'] "));
        email.click();
        email.sendKeys("wrongEmail@gmai.com", Keys.ENTER);

        String actualWarningMes = webDriver.findElement(By.cssSelector("div.o6cuMc")).getText();
        String expectedWarningMes = "Не удалось найти аккаунт Google.";

        System.out.println("warning msg test: " + assertEquals(expectedWarningMes, actualWarningMes));

        email.click();
        email.click();
        email.sendKeys(Keys.SHIFT);

        WebElement nextBtn = webDriver.findElement(By.cssSelector("div[class='F9NWFb'] button span[jsname]"));
        System.out.println("nextBtn.isEnabled() = " + nextBtn.isEnabled());


    }
}
