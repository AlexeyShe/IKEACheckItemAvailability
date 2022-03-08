package MainClasses;


import Utiliies.InputDataOperations;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;


public class LoginPage extends BasePage {

    private static final String LOGIN_BTN_XPATH = "/html/body/header/div/div/div/ul/li[4]/a";
    private static final String SIGNIN_BTN_XPATH = "//*[@id='loyalty-modal-content']/div/div/div[1]/a";
    private static final String USER_EMAIL_XPATH = "//input[@id='username']";
    private static final String USER_PASSW_XPATH = "//*[@id='password']";
    public static final String CONTINUE_BTN_XPATH = "//*[@id='root']/div/div[3]/div[1]/form/button[1]";


    public void login() throws IOException {

        InputDataOperations inputDataOperations = new InputDataOperations();

        String username = inputDataOperations.getProperties("username");
        String password = inputDataOperations.getProperties("password");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_EMAIL_XPATH))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_PASSW_XPATH))).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONTINUE_BTN_XPATH))).click();


    }
}
