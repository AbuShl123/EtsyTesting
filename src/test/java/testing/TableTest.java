package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilities.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableTest {

    @AfterMethod
    public void tearDown() {
        Driver.getDriver().close();
    }

    @Test
    public void test1() {
        Driver.getDriver().navigate().to("https://www.w3schools.com/sql/trysql.asp?filename=trysql_select_all");
        Driver.getDriver().findElement(By.cssSelector("button[type='button']")).click();

        Map<Integer, Map<String, String>> table = new HashMap<>();
        String locator = "//table[@class='ws-table-all notranslate']//tr";
        List<WebElement> rows  = Driver.getDriver().findElements(By.xpath(locator));
        List<WebElement> headers = Driver.getDriver().findElements(By.xpath(locator + "[1]/th"));
        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = Driver.getDriver().findElements(By.xpath(locator + "[" + (i+1) + "]/td")); // i+1 to skip the header row
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < headers.size(); j++) {
                map.put(headers.get(j).getText(), cells.get(j).getText());
            }
            table.put(i, map);
        }

        System.out.println(table.get(8).get("CustomerName"));
        System.out.println(table.get(8).get("PostalCode"));
    }

    @Test
    public void test2() {
        Driver.getDriver().navigate().to("https://www.w3schools.com/sql/trysql.asp?filename=trysql_select_all");
        Driver.getDriver().findElement(By.cssSelector("button[type='button']")).click();

        String locator = "//table[@class='ws-table-all notranslate']//tr";
        List<WebElement> rows  = Driver.getDriver().findElements(By.xpath(locator));
        for (int i = 1; i < rows.size(); i++) {
            System.out.println(rows.get(i).getText());
        }
    }
}
