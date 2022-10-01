package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.WebDriverFactory;

import java.util.concurrent.TimeUnit;

import static utilities.TestMethods.*;

public class FaceBook {
    public static void main(String[] args) {
        WebDriver webDriver = WebDriverFactory.getDriver("Chrome");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();

        webDriver.navigate().to("https://www.facebook.com/");

        // webElements
        WebElement emailInput = webDriver.findElement(By.id("email"));
        WebElement password = webDriver.findElement(By.id("pass"));
        WebElement faceBookLogo = webDriver.findElement(By.cssSelector("img[class='fb_logo _8ilh img']"));
        WebElement underLogoText = webDriver.findElement(By.cssSelector("div h2"));
        WebElement underSignBlockText = webDriver.findElement(By.cssSelector("#reg_pages_msg"));
        WebElement underSignBlockTextRef = webDriver.findElement(By.cssSelector("#reg_pages_msg a"));

        // isDisplayed()
        System.out.println("emailInput.isDisplayed() = " + emailInput.isDisplayed());
        System.out.println("faceBookLogo.isDisplayed() = " + faceBookLogo.isDisplayed());
        System.out.println("underLogoText.isDisplayed() = " + underLogoText.isDisplayed());
        System.out.println("underSignBlockText.isDisplayed() = " + underSignBlockText.isDisplayed());
        System.out.println("underSignBlockTextRef.isDisplayed() = " + underSignBlockTextRef.isDisplayed());
        // assertEquals()

        // FaceBook logo test:
        String actualLogo = faceBookLogo.getAttribute("src");
        String expectedLogo = "https://static.xx.fbcdn.net/rsrc.php/y8/r/dF5SId3UHWd.svg";
        System.out.println("actualLogo = " + actualLogo);
        System.out.println("Logo test: " + assertEquals(expectedLogo, actualLogo));

        // Under-Logo-Text:
        String expectedText = "Facebook помогает вам всегда оставаться на связи и общаться со своими знакомыми.";
        System.out.println("Under Logo Text: " + assertEquals(expectedText, underLogoText.getText()));

        // text under sign-in block:
        expectedText = "Создать Страницу знаменитости, музыкальной группы или компании.";
        System.out.println("under sign-in block: " + assertEquals(expectedText, underSignBlockText.getText()));

        // text under sign-in block -- a reference part (verify that a link has certain word
        System.out.println(underSignBlockTextRef.getAttribute("href"));
        expectedText = "registration_form";
        System.out.println("link under sign-in testing: " + assertContains(expectedText, underSignBlockTextRef.getAttribute("href")));

        emailInput.sendKeys("shoalievabubakr@gmail.com");
        password.sendKeys("abubakrshoaliev777");



    }
}
