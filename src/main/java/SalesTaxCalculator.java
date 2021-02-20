import Controllers.CatalogManager;
import Controllers.Invoice;
import Models.InvoiceItem;
import  Models.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SalesTaxCalculator {
    public static void main(String[] args) throws IOException {
        CatalogManager catalogManager;
        Invoice invoice = new Invoice();
        catalogManager = new CatalogManager(Config.CATALOG_FILE_NAME);
        ArrayList<Item> items = catalogManager.getCatalog();
        for(int i=0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.println((i+1) + ". " + item.name);
        }
        int selectedValue = 0;
        do {
            System.out.println("Select item to add to invoice. Press 0 to finish adding items");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            selectedValue = Integer.parseInt(reader.readLine());
            if ((selectedValue-1) >= items.size() || selectedValue < 0) {
                System.out.println("Invalid input. Please try again");
                continue;
            }
            if (selectedValue != 0) {
                System.out.println("Quantity: ");
                int quantity = Integer.parseInt(reader.readLine());
                InvoiceItem invoiceItem = new InvoiceItem();
                invoiceItem.item = items.get(selectedValue - 1);
                invoiceItem.quantity = quantity;
                invoice.addInvoiceItem(invoiceItem);
            }
        } while (selectedValue != 0);
        invoice.calculate();
        System.out.println("Item \t Quantity \t Cost \t Tax \t Total");
        for(int i=0; i < invoice.invoiceItems.size(); i++) {
            InvoiceItem invoiceItem = invoice.invoiceItems.get(i);
            System.out.println(invoiceItem.item.name + "\t" + invoiceItem.quantity + "\t" + invoiceItem.item.cost + "\t" + invoiceItem.tax + "\t" + invoiceItem.finalValue);
        }
        System.out.println("Tax: " + invoice.totalTax);
        System.out.println("Total: " + (invoice.total + invoice.totalTax));
    }
}