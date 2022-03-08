package MainClasses;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class BasePage {

    protected static WebDriver webDriver;
    protected static WebDriverWait wait;

    public void takeScreenshot(String name) {

        TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
        File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("ScreenShots/" + name + ".jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setWebDriver(WebDriver webDriver) {

        BasePage.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, 30);
    }

    protected void clickElementByXpath(String xpath) {

        findElementByXpath(xpath).click();

    }

    protected void doubleClickElementByXpath(String xpath) {

        Actions act = new Actions(webDriver);
        act.doubleClick(findElementByXpath(xpath)).perform();

    }

    protected void clickByKeysEnter(String xpath) {

        findElementByXpath(xpath).sendKeys(Keys.ENTER);
    }

    protected boolean isElementVisible(String xpath) {
        try {
            findElementByXpath(xpath);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    public void sendKeys(String xpath, String text) {

        sendTextToElementByXpath(xpath, text);
    }


    protected void sendTextToElementByXpath(String xpath, String text) {

        findElementByXpath(xpath).sendKeys(text);

    }

    protected boolean elementExistsByXpath(String xpath) {

        try {
            findElementByXpath(xpath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    protected String getCurrentPageURL() {

        return webDriver.getCurrentUrl();
    }

    protected void clickElementByName(String xpath, int text) {
        WebElement element = webDriver.findElement(By.xpath(xpath));
        element.click();
        Select select = new Select(element);
        select.selectByIndex(text);
    }


    protected WebElement findElementByXpath(String xpath) {

        WebElement element;
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        return element;

    }


}
