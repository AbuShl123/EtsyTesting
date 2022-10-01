package testing;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.WebDriverFactory;

import java.util.concurrent.TimeUnit;

public class Zoro {
    WebDriver webDriver;
    @BeforeMethod
    public void beforeMethod() {
        webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        webDriver.navigate().to("https://zoro.to/");
    }

    @AfterMethod
    public void afterMethod() {
        webDriver.quit();
    }

    @Test
    public void test1() {
        WebElement search = webDriver.findElement(By.tagName("input"));
        search.click();
        search.sendKeys("Melancholy of Haruhi Suzumia");
        search.sendKeys(Keys.ENTER);

        WebElement firstVideo = webDriver.findElement(By.xpath("//div[@class='film_list-wrap']/*[1]//h3/a"));
        Assert.assertTrue(firstVideo.isDisplayed());
        Assert.assertTrue(firstVideo.getText().contains("Melancholy"));
        firstVideo.click();

        for (int i = 1; i <= 6; i++) {
            String xpath = "//div[@class='bac-list-wrap']/div[" + i + "]";
            WebElement infoBox = webDriver.findElement(By.xpath(xpath));
            Assert.assertTrue(infoBox.isDisplayed());
            WebElement left = webDriver.findElement(By.xpath(xpath + "//h4"));
            String character = left.getText();
            left = webDriver.findElement(By.xpath("(" + xpath + "//h4)[2]"));
            String actor = left.getText();
            System.out.println(character + " -- " + actor);
        }

        WebElement recommendTitle = webDriver.findElement(By.xpath("//section[@class='block_area block_area_category']//h2"));
        System.out.println(recommendTitle.getText());

        for (int i = 1; i <= 18; i++) {
            try {
                WebElement content = webDriver.findElement(By.xpath("//div[@class='flw-item'][" + i + "]//h3/a"));
                System.out.println(content.getText());
            } catch (Exception e) {
                System.out.println("something went wrong!");
            }
        }
    }

    @Test
    public void test2() {
        webDriver.findElement(By.cssSelector("#action-button > a")).click();
        WebElement randomAnime;
        String memory = "";
        int encounter = 0;
        for (int i = 0; i < 5; i++) {
            randomAnime = webDriver.findElement(By.xpath("(//div[@class='hs-toggles'])[2]/a[2]"));
            randomAnime.click();
            WebElement title = webDriver.findElement(By.tagName("h2"));
            Assert.assertTrue(title.isDisplayed());
            String result = title.getText();
            if (memory.contains(result)) encounter++;
            else memory += " " + result;
            System.out.println(result);

            WebElement watchBtn = webDriver.findElement(By.cssSelector("a[class='btn btn-radius btn-primary btn-play'] "));
            WebElement addToList = webDriver.findElement(By.cssSelector("div[id='watch-list-content']"));
            Assert.assertTrue(addToList.isDisplayed());
            Assert.assertTrue(watchBtn.isDisplayed());

            WebElement shareMore = webDriver.findElement(By.xpath("//div[@class='at-share-btn-elements']/a[5]"));
            shareMore.click();
            WebElement shareTitle = webDriver.findElement(By.cssSelector("span[id='at-expanded-menu-title']"));
            System.out.println("SHARE TITLE:   " + shareTitle.getText());
            for (int j = 1; ; j++) {
                try {
                    WebElement icons = webDriver.findElement(By.xpath("//ul[@id='at-expanded-menu-top-service-list-container']/li[" + j + "]"));
                }catch (org.openqa.selenium.NoSuchElementException e) {
                    Assert.assertEquals(j-1, 10);
                    break;
                }
            }
            WebElement closeButton = webDriver.findElement(By.xpath("//button[@class='at-expanded-menu-close']"));
            closeButton.click();
        }

        Assert.assertTrue(encounter <= 5);
    }
}
