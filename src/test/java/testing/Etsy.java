package testing;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestMethods;
import utilities.WebDriverFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Etsy {
    // that's a big one
    WebDriver webDriver;

    @BeforeMethod
    public void setUp() {
        webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
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

        Assert.assertTrue(freeShippingLabels.size() >= saleBlocks.size()*0.80); // verifying that "free shipping" products are 80% of all total goods in the page;
    }

    /*
    java.lang.AssertionError: 'Льняные шорты WENDYЭластичная талия ЛетоОдежда ручной работы для женщин' != 'Льняные шорты WENDY '
    Expected :true
    Actual   :false
     */

    @Test
    public void test2() {
        // Choosing 'clothing' section
        WebElement clothing = webDriver.findElement(By.cssSelector("a[href$='256485377']")); // select clothing
        clothing.click();

        Map<String, Map<String, String>> allProducts = new HashMap<>(); // there we will store chosen clothes.

        // Setting 5 products and adding them to the cart
        for (int g = 0; g < 5; g++) {
            //clicking to random product
            Random rd = new Random();
            int index = rd.nextInt(64)+1; // gives random number from 1-64
            WebElement product = webDriver.findElement(By.xpath("//ul[@class='wt-grid wt-grid--block wt-pl-xs-0 tab-reorder-container']/li[" + index + ']')); // find random dress from the page
            System.out.println("index: " + index);
            try {
                product.click(); // this opens new tab
            } catch (ElementClickInterceptedException e) {
                System.out.println("EXCEPTION GET");
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                js.executeScript("arguments[0].click()", product); // this opens new tab
            }

            ArrayList<String> taps = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(taps.get(1));

            // VERIFY that title is valid -> It should start with first the several words of the selected product's name:
                // 1. Find product's name
            String productName = webDriver.findElement(By.xpath("(//h1)[1]")).getText().trim();
            if (allProducts.containsKey(productName)) {
                g--; continue; // if there is a
            }
                // 2. Find current Title of the page and get first several words from this title
            String currentTitle = webDriver.getTitle().trim();
            Assert.assertTrue(TestMethods.ignoreStarting(currentTitle, productName));

            allProducts.put(productName, null);

            //------- selecting properties
            // checking if there are any parameters to select:
            List<WebElement> variations = webDriver.findElements(By.xpath("//div[@data-selector='listing-page-variations']/*"));
            Map<String, String> props = new HashMap<>(); // there we will store selected info about products

            for (int i = 0; i < variations.size(); i++) { // if they ARE parameters (which means that variations' size is more than 0) -> select something
                while (true) {
                    try {
                        WebElement select = webDriver.findElement(By.cssSelector("select#variation-selector-" + i));
                        Select variationsSelect = new Select(select);
                        List<WebElement> options = variationsSelect.getOptions();
                        String defaultOption = options.get(0).getText();
                        do {
                            index = rd.nextInt(options.size());
                            variationsSelect.selectByIndex(index); // selecting random option
                        } while (defaultOption.equals(variationsSelect.getFirstSelectedOption().getText())); // if the selected option equals to default one --> reselect
                        WebElement variationType = webDriver.findElement(By.xpath("//select[@id='variation-selector-" + i + "']/../preceding-sibling::label")); // searching for variation name
                        String name = variationType.getText();
                        String attribute = variationsSelect.getFirstSelectedOption().getText();
                        props.put(name, attribute); // storing the information in the array
                        break;
                    } catch (StaleElementReferenceException ignored) {}
                }
            }

            //------- Personalization issue
            String personalizationInfo = "";
            try { // <- we need to check if there is a personalization block below the parameters. It there is -> sendKeys since otherwise it may give an error message
                WebElement personalization = webDriver.findElement(By.xpath("//textarea[@id='listing-page-personalization-textarea']"));
                personalizationInfo = "Not too big, the smaller the better.";
                personalization.sendKeys(personalizationInfo);
            } catch (Exception e) {
                personalizationInfo = "No personalization";
            }

            allProducts.put(productName, props);

            //------- Adding to the cart
            WebElement addToCart = webDriver.findElement(By.xpath("//div[@data-buy-box]/*[last()]"));
            addToCart.click(); // adding the product to the cart

            //------- return back to the main page and closing current tab
            try {
                WebElement continueShopping = webDriver.findElement(By.xpath("//button[@class='wt-btn wt-btn--secondary']"));
                continueShopping.click();
            } catch (Exception ignored) {}

            webDriver.close();
            webDriver.switchTo().window(taps.get(0));
        }

        System.out.println("=".repeat(20));
        for (Map.Entry<String, Map<String, String>> entry : allProducts.entrySet()) {
            System.out.println(entry.getKey() + "~~" + entry.getValue());
        }
        System.out.println("=".repeat(20));

        WebElement cartIcon = webDriver.findElement(By.xpath("(//a[@aria-label])[1]")); // clicking to Cart icon
        cartIcon.click();

        List<WebElement> names = webDriver.findElements(By.xpath("//a[@data-title]")); // getting all product names
        // Verify that we have 5 products in the cart
        Assert.assertEquals(names.size(), 5);

        // Verify that all the properties products are as expected
        for (int j = 0; j < 5; j++) {
            String titleOfProduct = names.get(j).getAttribute("data-title");
            if (!allProducts.containsKey(titleOfProduct)) { // if we don't such a product in the map --> failed
                Assert.fail();
            }
            String x = "(//a[@data-title])["+j+"]/../.. // li[@class='wt-text-black wt-text-caption wt-pb-xs-1']";// now we will go through all properties
            List<WebElement> props = webDriver.findElements(By.xpath(x)); // properties are here
            for (int i = 1; i <= props.size(); i++) { // there maybe more than 1 property (ex: size, color)
                WebElement prop = webDriver.findElement(By.xpath(x + "[" + i + "]/span"));
                String[] keyAndValue = prop.getText().split(": ");
                String propName = keyAndValue[0];
                String actualValue = keyAndValue[1];
                System.out.println("prop name = '" + propName + "'");
                System.out.println("prop value = '" + actualValue + "'");
                System.out.println(titleOfProduct);
                System.out.println(allProducts.get(titleOfProduct));
                String expectedValue = allProducts.get(titleOfProduct).get(propName); // 'color'='red' e.g -> ..get(propName) = 'red'
                System.out.println("value: " + expectedValue);
                //Assert.assertEquals("", expectedValue);
            }
        }
    }
}
