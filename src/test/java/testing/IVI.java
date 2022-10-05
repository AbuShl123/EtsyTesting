package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.WebDriverFactory;

import java.util.concurrent.TimeUnit;

public class IVI {
    WebDriver webDriver;

    @BeforeMethod
    public void setUp() {
        webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    @Test
    public void test1() {
        webDriver.navigate().to("https://ivi.ru");

        WebElement movies = webDriver.findElement(By.cssSelector("ul.headerMenu__list > li:nth-of-type(3)"));
        System.out.println(movies.isDisplayed());
        System.out.println(movies.isEnabled());
        movies.click();

        WebElement genresDropdown = webDriver.findElement(By.cssSelector("div.filtersDesktop__plank"));
        genresDropdown.click();

        WebElement musical = webDriver.findElement(By.xpath("//li//div[.='Музыкальные']"));
        musical.click();

        System.out.println(webDriver.getTitle());

        genresDropdown = webDriver.findElement(By.cssSelector("div.filtersDesktop__plank"));
        genresDropdown.click();

        WebElement activeGenre = webDriver.findElement(By.xpath("//li//div[.='Музыкальные']/../.."));
        String expected = "checked";
        String actual = activeGenre.getAttribute("class");
        Assert.assertTrue(actual.endsWith(expected));
    }
}
