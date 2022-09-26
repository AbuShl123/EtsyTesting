package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.WebDriverFactory;

public class GettingWebElements {
    public static void main(String[] args) {
        WebDriver webDriver = WebDriverFactory.getDriver("chrome");

        webDriver.get("https://www.etsy.com/");


        WebElement element1 = webDriver.findElement(By.cssSelector("a[class$='underline'] > span[role]"));
        // attributeName+$ -> means ends-with
        System.out.println("element1.isDisplayed() = " + element1.isDisplayed());
        element1 = webDriver.findElement(By.cssSelector("li[data-node-id = '10855'] > a")); // one another way
        System.out.println("element1.isDisplayed() = " + element1.isDisplayed());

        element1 = webDriver.findElement(By.xpath("//a[substring(@class, string-length(@class) - string-length('underline')+1)='underline']")); // the same value, but with xpath
        // string-length(@A) -> returns a length of the value of the given attribute
        // substring(@A, ind) -> basic substring, which returns cut value of attribute, BUT holds only one index, so it cuts the string from the index 'ind' until the end
        System.out.println("element1.isDisplayed() = " + element1.isDisplayed());

        WebElement element2;
        try { // <- elements in this block are unique. Each time we open the Etsy they change. That's why it is possible for FailedToFindElement
            element2 = webDriver.findElement(By.xpath("(//div[(substring(@class, string-length(@class) - 3))='full']/div/img[starts-with(@alt, 'Demon')])[1]"));
            System.out.println("element2.isDisplayed() = " + element2.isDisplayed());

            element2 = webDriver.findElement(By.xpath("(//img[contains(@alt, 'Demon slayer')])[1]")); // <- same webElement but different xpath
            System.out.println("element2.isDisplayed() = " + element2.isDisplayed());

            element2 = webDriver.findElement(By.cssSelector("div[class$='auto'] img[alt^='Demon slayer']")); // <- the same as well, but with css
            System.out.println("element2.isDisplayed() = " + element2.isDisplayed());

            WebElement element3 = webDriver.findElement(By.xpath("//span[text()='28.71']"));
            System.out.println("element3.isDisplayed() = " + element3.isDisplayed());

            element3 = webDriver.findElement(By.cssSelector("div[class$= 'masonry-grid'] img[alt^='Natural Large']"));

        } catch (Exception e) {
            System.out.println(e.getMessage().substring(0, e.getMessage().indexOf('{')-1));
        }


        webDriver.quit();
    }
}
