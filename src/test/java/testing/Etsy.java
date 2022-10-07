package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.WebDriverFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Etsy {
    WebDriver webDriver;

    @BeforeMethod
    public void setUp() {
        webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        webDriver.navigate().to("https://etsy.com");
    }

    @AfterMethod
    public void tearDown() {

    }

    @Test
    public void test1() {
        WebElement menu = webDriver.findElement(By.xpath("//ul[@role='menubar']/li"));
        menu.click();

        WebElement filter = webDriver.findElement(By.cssSelector("button#search-filter-button"));
        filter.click(); // clicking on filter button

        WebElement freeShip = webDriver.findElement(By.cssSelector("input#special-offers-free-shipping")); // selecting free shipping option
        JavascriptExecutor js = (JavascriptExecutor)webDriver;
        js.executeScript("arguments[0].click()", freeShip);

        WebElement apply = webDriver.findElement(By.xpath("(//button[@type='submit'])[4]")); // clicking on apply button
        apply.click();

        List<WebElement> freeShippingLabels = webDriver.findElements(By.xpath("//span[@class='wt-badge wt-badge--small wt-badge--sale-01']"));
        List<WebElement> saleBlocks = webDriver.findElements(By.xpath("//ul[@class='wt-grid wt-grid--block wt-pl-xs-0 tab-reorder-container']/li"));

        System.out.println("Total amount of goods: " + saleBlocks.size());
        System.out.println("Free Shipping goods: " + freeShippingLabels.size());

        Assert.assertTrue(freeShippingLabels.size() >= saleBlocks.size()*0.85); // verifying that "free shipping" products are 85% of all total goods in the page;
    }
}
