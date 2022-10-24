package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Driver {
    private static WebDriver driver;

    private Driver() {}

    public static WebDriver getDriver() {

        if (driver == null) {
            String browser = ConfigurationReader.getProperty("browser");

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
            }

            driver.manage().window().maximize();
        }

        return driver;
    }

    public static void closeDriver() {
        driver.close();
    }
}
