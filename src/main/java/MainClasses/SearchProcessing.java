package MainClasses;

import java.util.*;

import Utiliies.FileSaver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.logging.Logger;


public class SearchProcessing extends BasePage {


    private static final String SHOW_MORE_RESULTS = "//a[@class = 'show-more__button button button--secondary button--small']";
    private static final String SEARCH_INPUT_XPATH = "//input[@name='q']";
    private static final String LOGIN_PAGE_URL = "https://www.ikea.com/ca/en/profile/login";
    private static final String PASSW_LABEL = "//*[@id='root']/div/div[3]/div[1]/form/div[3]/div/label";

    Logger logger = Logger.getLogger("log");

    public void navigateToPage(String url) {
        webDriver.get(url);
    }

    public void navigateToLoginPage() {
        webDriver.get(LOGIN_PAGE_URL);
    }

    public String getCurrentURL() {
        return getCurrentPageURL();
    }

    public void search(String searchText) {
        findElementByXpath(SEARCH_INPUT_XPATH).sendKeys(searchText + Keys.ENTER);
    }

    public boolean elementIsPresent() {
        return isElementVisible("//*[contains(text(), 'Table')]");
    }


    public void getSearchResults(String zipcode, String item) throws InterruptedException, IOException {


        logger.info("opening login page");

        navigateToPage(LOGIN_PAGE_URL);

        try {
            LoginPage loginPage = new LoginPage();
            loginPage.login();
        } catch (Exception e) {
            System.out.println("Unable to login");
        }

        try {
            this.takeScreenshot("scr ");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPage.CONTINUE_BTN_XPATH))).click();
            logger.info("Logged In successfully");

        } catch (Exception e) {
            System.out.println("failed to take screenshot");
        }

//        Thread.sleep(10000);


        this.takeScreenshot("screen1");

//        InputDataOperations inputDataOperations = new InputDataOperations();
//        String searchItem = inputDataOperations.getProperties("searchData");

        cookiesKill();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_INPUT_XPATH))).sendKeys(item);
//        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SEARCH_INPUT_XPATH))).submit();
        logger.info("searching item");

        Thread.sleep(5000);

        this.takeScreenshot("zipcode_before");

        selectLocationByZipCode(zipcode);
        this.takeScreenshot("zipcode_after");

        logger.info("selecting store location by zipcode provided");

        cookiesKill();
        showMoreResults();
        logger.info("collecting data");
        List<WebElement> divElement = webDriver.findElements(By.xpath("//*[@id='search-results']/div"));

        List<String> links = new ArrayList<>();

        for (int i = 0; i < divElement.size(); i++) {
            if (divElement.get(i).getAttribute("class").contains("content-fragment serp-grid__item serp-grid__item--content-fragment")) {
                divElement.remove(i);
            }
        }

        List<WebElement> priceDecimal = webDriver.findElements(By.xpath("//span[@class = 'range-revamp-price__integer']"));

        List<WebElement> priceResid = webDriver.findElements(By.xpath("//span/span[@class = 'range-revamp-price__decimals']"));

        List<String> price = new ArrayList<>();
        for (int i = 0; i < priceDecimal.size(); i++) {
            String a = priceDecimal.get(i).getText() + priceResid.get(i).getText();
            price.add(a);
        }


        List<WebElement> availability = webDriver.findElements(By.xpath("//*[@id='search-results']/div/div[3]/div/span[2]"));
        List<String> itemID = new ArrayList<>();
        for (WebElement el : divElement) {
            itemID.add(el.getAttribute("data-ref-id"));
        }


        for (WebElement element : divElement) {
            links.add(element.findElement(By.tagName("a")).getAttribute("href"));
        }

        for (int i = 0; i < divElement.size(); i++) {
//            System.out.println(priceDecimal.get(i).getText() + priceResid.get(i).getText() + " " + availability.get(i).getText() + "  " + links.get(i));
        }


        FileSaver fileSaver = new FileSaver();
        fileSaver.fileWriter(item.toUpperCase(Locale.ROOT), zipcode.toUpperCase(Locale.ROOT), price, availability, links, itemID);


        logger.info("========= Adding items to cart ========");


        for (int i = 0; i < divElement.size(); i++) {
            if (availability.get(i).getText().contains("In stock") || availability.get(i).getText().contains("Low") ||availability.get(i).getText().contains("Running")) {

                try {
                    logger.info("Item added to cart");
                    JavascriptExecutor js = (JavascriptExecutor) webDriver;
                    js.executeScript("arguments[0].click();", webDriver.findElement(By.xpath("//*[@id='" + itemID.get(i) + "add_to_cart']")));
                } catch (Exception ignored) {

                }
            }
        }

        logger.info("------FINISH------");


    }

    public void selectLocationByZipCode(String zipcode) {

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='hnf-header-storepicker']/button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='hnf-txt-storepicker-postcode']"))).sendKeys(zipcode);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='hnf-btn-storepicker-method-postcode']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='hnf-storepicker-ok']"))).click();


    }

    public void showMoreResults() {
        try {
            do {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(SHOW_MORE_RESULTS))).click();
                System.out.println("Next search page opened");
            }
            while (elementExistsByXpath(SHOW_MORE_RESULTS));
        } catch (Exception ignored) {

        }
    }

    public void cookiesKill() {
        // Closes pop-up with cookies
        String parentWindowHandler = webDriver.getWindowHandle();
        String subWindowHandler = null;

        Set<String> handles = webDriver.getWindowHandles();
        for (String handle : handles) {
            subWindowHandler = handle;
        }
        webDriver.switchTo().window(subWindowHandler);

        if (isElementVisible("//button[text()='Ok']")) {
            findElementByXpath("//button[@id='onetrust-accept-btn-handler']").click();
        }
        webDriver.switchTo().window(parentWindowHandler);
    }
}
