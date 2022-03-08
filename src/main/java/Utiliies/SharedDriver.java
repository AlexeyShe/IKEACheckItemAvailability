package Utiliies;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;


public class SharedDriver {

    private static WebDriver webDriver;

    public enum Browser {

        CHROME,
        FIREFOX,
        IE,
    }

    protected static WebDriver getWebDriver() {

        switch (Browser.CHROME) {

            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless", "--window-size=1920,1400");
                webDriver = new ChromeDriver(options);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                webDriver = new InternetExplorerDriver();
                break;

        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return webDriver;

    }

    protected static void closeDriver() {
        try {


            if (webDriver != null) {
                webDriver.close();
            }
        } catch (NoSuchSessionException e) {
            System.out.println("hava a nice day!");
        }
    }

}

