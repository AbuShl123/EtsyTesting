package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.Random;

public class GoGoAnimeMainPage {

    Random rd = new Random();

    public GoGoAnimeMainPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy
    WebElement animePoster;
}
