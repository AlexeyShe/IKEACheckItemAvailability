package Utiliies;

import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class FileSaver {

    public void fileWriter (String itemName, String zipcode, List <String> price, List <WebElement> availability, List <String> link, List <String> itemID) throws IOException {
        Date date = new Date();
        File file = new File("searchResults"+date+".txt");
        FileWriter writeToFile = new FileWriter(file);
        writeToFile.append(String.format("%20s \r\n", "-------Date  " + date));
        writeToFile.append(String.format("%20s \r\n", "-------Item searched  " + itemName + "  ZIPcode = " + zipcode));
        writeToFile.write(String.format("%7s  %-30s %-15s %-30s \r\n", "Price", "Availability", "ItemID", "Link"));
        for (int i=0; i<itemID.size();i++) {
            writeToFile.append(String.format("%7s  %-30s %-15s %-100s \r\n", price.get(i), availability.get(i).getText(), itemID.get(i).toString(), link.get(i)));
        }
        if (itemID.size()==0) {
            writeToFile.write("****** NO ITEMS WERE FOUND ******");
        }

        writeToFile.close();

    }




}
