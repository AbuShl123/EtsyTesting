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

    @BeforeClass
    public void setUp() {
        webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {

    }

    @BeforeMethod
    public void beforeMethod() {
        webDriver.navigate().to("https://jut.su");
    }

    @AfterMethod
    public void afterMethod() {

    }



    @Test
    public void test1() {
        WebElement logIn = webDriver.findElement(By.xpath("//a[@title='Форма авторизации']"));
        Assert.assertTrue(logIn.isDisplayed());

        logIn.click();

        WebElement emailInput = webDriver.findElement(By.cssSelector("#login_input1"));
        Assert.assertTrue(emailInput.isDisplayed());

        emailInput.sendKeys("shoalievabubakr@gmail.com");

        WebElement passwordInput = webDriver.findElement(By.cssSelector("#login_input2"));
        passwordInput.sendKeys("");
    }

    @Test
    public void test2() throws InterruptedException {
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
