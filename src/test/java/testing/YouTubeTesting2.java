package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.TestMethods;
import utilities.WebDriverFactory;

import java.util.concurrent.TimeUnit;

public class YouTubeTesting2 {
    public static void main(String[] args) {
        WebDriver webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.navigate().to("https://www.youtube.com/watch?v=np7S8XR5eFM");
        webDriver.manage().window().maximize();

        WebElement videoTitle = webDriver.findElement(By.xpath("//div[@id='container'] / h1"));
        System.out.println(videoTitle.getText());
        String expected = "Seong-Jin Cho – Piano Concerto in E minor Op. 11 (Prize-winners' Concert)";
        String actual = videoTitle.getText();
        System.out.println(TestMethods.assertEquals(expected, actual));

        WebElement searchBox = webDriver.findElement(By.cssSelector("input[id='search']"));
        searchBox.click();
        searchBox.sendKeys("ExtremeCode", Keys.ENTER);

        WebElement img = webDriver.findElement(By.cssSelector("div[id='content-section'] img"));
        System.out.println("img.isDisplayed() = " + img.isDisplayed());

        WebElement channelText = webDriver.findElement(By.cssSelector("div[id='content-section'] #text-container > *"));
        System.out.println(channelText.getText());

        WebElement subscribeBtn = webDriver.findElement(By.cssSelector("div[id='content-section'] #buttons"));
        System.out.println("subscribeBtn.isDisplayed() = " + subscribeBtn.isDisplayed());

        WebElement countSubscribers = webDriver.findElement(By.cssSelector("span#subscribers"));
        System.out.println(countSubscribers.getText());

        WebElement countVideos = webDriver.findElement(By.cssSelector("span#video-count"));
        System.out.println(countVideos.getText());
    }
}
