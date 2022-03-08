package Utiliies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class InputDataOperations {

    public void saveProperties(String type, String value) throws IOException {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream("search_data.properties");
        properties.load(inputStream);
        FileOutputStream outputStream = new FileOutputStream("search_data.properties");
        properties.setProperty(type, value);
        properties.store(outputStream, null);

    }

    public String getProperties (String type) throws IOException {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream("search_data.properties");
        properties.load(inputStream);
        return properties.getProperty(type);

    }

    public void checkMult () throws IOException {

        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream("search_data.properties");
        properties.load(inputStream);
        List<String> items = Arrays.asList(properties.getProperty("zipcode").split("\\s*,\\s*"));
        for (int i =0; i<items.size();i++) {
            System.out.println(items.get(i));
        }



    }

}
