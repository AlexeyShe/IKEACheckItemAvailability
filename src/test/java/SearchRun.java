import MainClasses.*;
import Utiliies.InputDataOperations;
import Utiliies.UseCaseBase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SearchRun extends UseCaseBase {

    private static Launch mainClass;
    private static LoginPage loginPage;
    private static SearchProcessing searchProcessing;


    @BeforeAll
    public static void classSetUp() {

        mainClass = new Launch();
        loginPage = new LoginPage();
        searchProcessing = new SearchProcessing();
    }

    @BeforeEach
    public void beforeTests() {
        setUPMain();
        searchProcessing.navigateToLoginPage();
    }

    @AfterAll
    public static void closeWebdriver() {
        tearDownMain();
    }




    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")

    // username & password please put into search_data.properties
    // username $ password should be valid

    //zip codes and items to search - please put into data.csv


    //output file with search results - searchResultsDate.txt

    public void launch(String zipcode, String item) throws IOException, InterruptedException {

        mainClass.start(zipcode, item);

    }
}
