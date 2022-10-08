package testing;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.WebDriverFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Etsy {
    WebDriver webDriver;

    @BeforeMethod
    public void setUp() {
        webDriver = WebDriverFactory.getDriver("chrome");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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


    @Test
    public void test2() {
        //------- Choosing clothing section
        WebElement clothing = webDriver.findElement(By.cssSelector("a[href$='256485377']")); // select clothing
        clothing.click();

        Map<String, String> allProducts = new HashMap<>(); // there we will store chosen clothes.

        //------- setting 5 products and adding them to the cart
        for (int g = 0; g < 5; g++) {
            //------- clicking to random product
            Random rd = new Random();
            int index = rd.nextInt(64)+1; // gives random number from 1-64
            WebElement product = webDriver.findElement(By.xpath("//ul[@class='wt-grid wt-grid--block wt-pl-xs-0 tab-reorder-container']/li[" + index + ']')); // find random dress from the page
            product.click(); // this opens new tap

            ArrayList<String> taps = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(taps.get(1)); // switch to new tap

            // verifying product name
            WebElement productTitle = webDriver.findElement(By.xpath("(//h1)[1]")); // find product's name
            String productName = productTitle.getText();
            String partialTitle = "";
            String[] arr = webDriver.getTitle().split(" "); // getting each word from title
            for (int i = 0; i < 3; i++) {
                partialTitle += arr[i] + " "; // getting first 3 words from title
            }
            productName = productName.replaceAll("\\p{Punct}", ""); // removing all punctuations
            partialTitle = partialTitle.replaceAll("\\p{Punct}", ""); // removing all punctuations
            Assert.assertTrue(productName.startsWith(partialTitle)); // verifying that the title starts with the same words as the product name

            allProducts.put(productName, null); // add this product to map

            //------- selecting properties
            // checking if there are any parameters to select:
            List<WebElement> variations = webDriver.findElements(By.xpath("//div[@data-selector='listing-page-variations']/*"));
            ArrayList<String> props = new ArrayList<>(); // there we will store selected info about product

            for (int i = 0; i < variations.size(); i++) { // if they ARE parameters (which means that variations' size is more than 0) -> select something
                while (true) {
                    try {
                        Select variationsSelect = new Select(webDriver.findElement(By.cssSelector("select#variation-selector-" + i)));
                        List<WebElement> options = variationsSelect.getOptions();
                        String defaultOption = options.get(0).getText();
                        do {
                            index = rd.nextInt(options.size());
                            variationsSelect.selectByIndex(index); // selecting random option
                        } while (defaultOption.equals(variationsSelect.getFirstSelectedOption().getText()));
                        WebElement variationType = webDriver.findElement(By.xpath("//select[@id='variation-selector-" + i + "']/../preceding-sibling::label")); // searching for variation name
                        String name = variationType.getText();
                        String attribute = variationsSelect.getFirstSelectedOption().getText();
                        props.add(name + "===" + attribute); // storing the information in the array
                        break;
                    } catch (StaleElementReferenceException ignored) {}
                }
            }

            //------- Personalization issue
            String personalizationInfo = "";
            try { // <- we need to check if there is a personalization block below the parameters. It there is -> sendKeys since otherwise it may give an error message
                WebElement personalization = webDriver.findElement(By.xpath("//textarea[@id='listing-page-personalization-textarea']"));
                personalizationInfo = "Not too big, the smaller the better.";
            } catch (Exception e) {
                personalizationInfo = "No personalization";
            }

            allProducts.put(productName, props.toString() + "===" + personalizationInfo);

            //------- Adding to the cart
            WebElement addToCart = webDriver.findElement(By.xpath("//div[@data-buy-box]/*[last()]"));
            addToCart.click(); // adding the product to the cart

            //------- return back to the main page and closing current tab
            try {
                WebElement continueShopping = webDriver.findElement(By.xpath("//button[@class='wt-btn wt-btn--secondary']"));
            } catch (Exception ignored) {}

            webDriver.close();
            webDriver.switchTo().window(taps.get(0));
        }

        WebElement cartIcon = webDriver.findElement(By.xpath("(//a[@aria-label])[1]"));
        cartIcon.click();


    }
}
