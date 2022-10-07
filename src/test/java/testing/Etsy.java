package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.WebDriverFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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


    @Test
    public void test2() {
        WebElement furniture = webDriver.findElement(By.cssSelector("a[href$='1241257053']")); // select furniture
        furniture.click();

        String expectedTitle = "Handmade furniture - Etsy";
        String actualTitle = webDriver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);

        Random rd = new Random();
        int index = rd.nextInt(64)+1;
        WebElement product = webDriver.findElement(By.xpath("//ul[@class='wt-grid wt-grid--block wt-pl-xs-0 tab-reorder-container']/li[" + index + ']')); // find random furniture from the page
        product.click(); // this opens new tap

        ArrayList<String> taps = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(taps.get(1)); // switch to new tap

        System.out.println(webDriver.getTitle());
        WebElement productTitle = webDriver.findElement(By.xpath("(//h1)[1]")); // find product name
        String productName = productTitle.getText();
        System.out.println(productName);
        String partialTitle = "";
        for (int i = 0; i < 5; i++) {
            partialTitle += webDriver.getTitle().split("")[i]; // getting first 5 words from title
        }
        Assert.assertTrue(productName.startsWith(partialTitle)); // verifying that title starts with the same words as the product name


    }
}
