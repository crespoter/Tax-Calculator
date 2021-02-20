package Controllers;
import Models.Category;
import Models.Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CatalogManager {
    private final ArrayList<Item> items;
    public CatalogManager(String fileLocation) {
        this.items = new ArrayList<Item>();
        // read from json file to populate the Items Catalog
        try {
            File itemsCatalogFile = new File(fileLocation);
            Scanner fileScanner = new Scanner(itemsCatalogFile);
            // Skip header
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] values = data.split(", ");
                String name = values[0];
                boolean imported = false;
                if (values[1].equals("yes")) {
                    imported = true;
                }
                float cost = Float.parseFloat(values[2]);
                Category category = Category.OTHER;
                try {
                    category = Category.valueOf(values[3]);
                } catch (Exception e) {
                    System.out.println("Invalid category for item " + name + " , assigning other category");
                }
                Item item = new Item(name, imported, cost, category);
                items.add(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred reading file");
            e.printStackTrace();
        }
    }
    public ArrayList<Item> getCatalog() {
        return this.items;
    }
}
