package MainClasses;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Launch extends BasePage {


    public static void start(String zipcode, String item) throws IOException, InterruptedException {


        SearchProcessing searchProcessing = new SearchProcessing();
        searchProcessing.getSearchResults(zipcode, item);

    }

    public static List<String> paramList(String type) throws IOException {

        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream("search_data.properties");
        properties.load(inputStream);
        List<String> values = Arrays.asList(properties.getProperty(type).split("\\s*,\\s*"));
        for (String value : values) {
            System.out.println(value);
        }
        return values;


    }


}
