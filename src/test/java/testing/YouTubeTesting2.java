package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.WebDriverFactory;

public class YouTubeTesting2 {
    public static void main(String[] args) {
        WebDriver webDriver = WebDriverFactory.getDriver("chrome");

        webDriver.navigate().to("https://www.youtube.com/watch?v=np7S8XR5eFM");

        WebElement videoTitle = webDriver.findElement(By.xpath("//*[@id=\"title\"]/h1/yt-formatted-string"));

        System.out.println(videoTitle.getText());

        WebElement searchBox = webDriver.findElement(By.cssSelector("input[id='search']"));
        searchBox.click();
        searchBox.sendKeys("ExtremeCode");
    }
}
