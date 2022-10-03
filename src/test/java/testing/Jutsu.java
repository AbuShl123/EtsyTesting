package testing;

import SECRET.Secret;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.WebDriverFactory;

import java.util.concurrent.TimeUnit;

public class Jutsu {
    WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod() {
        webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.navigate().to("https://jut.su");
    }

    @AfterMethod
    public void afterMethod() {
        webDriver.quit();
    }



    @Test
    public void test1() {
        /*
        Steps:
            1.	User is on Home Page
            2.	User clicks on “Войти на Сайт” button
            3.	User Email
            4.	User enters Password
            5.	User clicks on “Войти” Button
         */
        // step 2:
        WebElement logIn = webDriver.findElement(By.xpath("//a[@title='Форма авторизации']"));
        logIn.click();

        // step 3:
        WebElement emailInput = webDriver.findElement(By.cssSelector("#login_input1"));
        emailInput.sendKeys("shoalievabubakr@gmail.com");

        // step 4:
        WebElement passwordInput = webDriver.findElement(By.cssSelector("#login_input2"));
        passwordInput.sendKeys(Secret.myPassword);

        // step 5:
        WebElement submitButton = webDriver.findElement(By.cssSelector("#login_submit"));
        submitButton.click();

        // --- checking that user really logged in ---
        WebElement profile = webDriver.findElement(By.linkText("Профиль"));
        WebElement messages = webDriver.findElement(By.linkText("Сообщения"));
        WebElement logout = webDriver.findElement(By.linkText("[Выйти]"));

        Assert.assertTrue(profile.isDisplayed());
        Assert.assertTrue(messages.isDisplayed());
        Assert.assertTrue(logout.isDisplayed());
    }

    @Test
    public void test2() {
        /*
        Steps:
            1.	User is on Hope Page
            2.	User clicks on “Войти на Сайт” button
            3.	User enters invalid Email
            4.	User enters invalid Password
            5.	User clicks on “Войти” button
         */
        WebElement logIn = webDriver.findElement(By.xpath("//a[@title='Форма авторизации']"));
        logIn.click();

        // step 3:
        WebElement emailInput = webDriver.findElement(By.cssSelector("#login_input1"));
        emailInput.sendKeys("sdkfjhfsdfa");

        // step 4:
        WebElement passwordInput = webDriver.findElement(By.cssSelector("#login_input2"));
        passwordInput.sendKeys("sdfsdfk@gmail.com");

        // step 5:
        WebElement submitButton = webDriver.findElement(By.cssSelector("#login_submit"));
        submitButton.click();

        // --- Checking that user gets error message and “Войти на Сайт” button remains unchanged ---
        WebElement errorMessage = webDriver.findElement(By.xpath("//div[@class='clear berrors'] "));
        Assert.assertTrue(errorMessage.isDisplayed());

        logIn = webDriver.findElement(By.xpath("//a[@title='Форма авторизации']"));
        Assert.assertTrue(logIn.isDisplayed());
    }

    // this method is just practicing.
    public void registration() throws InterruptedException {
        WebElement logIn = webDriver.findElement(By.xpath("//a[@title='Форма авторизации']"));
        Assert.assertTrue(logIn.isDisplayed());
        logIn.click();

        WebElement reg = webDriver.findElement(By.linkText("Регистрация"));
        reg.click();

        String expectedUrl = "https://jut.su/register.html";
        String actualUrl = webDriver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);

        WebElement login = webDriver.findElement(By.id("name"));
        login.click();
        login.sendKeys("AbuShl123");

        WebElement pass = webDriver.findElement(By.xpath("//input[@name='password1']"));
        pass.click();
        pass.sendKeys(Secret.myPassword);

        WebElement email = webDriver.findElement(By.xpath("//input[@name='email'] "));
        email.click();
        email.sendKeys("shoalievabubakr@gmail.com");

        WebElement answer = webDriver.findElement(By.xpath("//input[@name='question_answer']"));
        System.out.println("answer.isDisplayed() = " + answer.isDisplayed());
        answer.sendKeys("Наруто");

        WebElement send = webDriver.findElement(By.xpath("//div[@class='fieldsubmit']/*"));
        System.out.println("send.isDisplayed() = " + send.isDisplayed());

        Thread.sleep(10_000);
        send.click();
    }
}
