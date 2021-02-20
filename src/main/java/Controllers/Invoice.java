package Controllers;

import Models.Category;
import Models.InvoiceItem;
import Models.Item;

import java.util.ArrayList;

public class Invoice {
    private  static final Category[] EXEMPTED_ITEMS = {Category.BOOK, Category.FOOD, Category.MEDICINE};
    private static final float BASE_SALES_TAX = 10;
    private static final float IMPORT_TAX = 5;
    public ArrayList<InvoiceItem> invoiceItems;
    public float totalTax = 0;
    public float total = 0;
    private float roundTax(float tax) {
        /*
            Rounding ->
            First dividing by 5 to make this as a rounding to nearest 0.01 instead of nearest 0.05
            Multiplying by 1000 and removing floating part
            if last digit is >=5, increment, second last digit
            Divide by 1000 again
            Multiply by 5 to get rounded number
         */
        tax = tax / 5.0f;
        tax *= 1000;
        int integerTax = (int) tax;
        int lastDigit = integerTax%10;
        if (lastDigit >= 5) {
            integerTax += 10;
        }
        integerTax = integerTax/10; // Remove last digit
        tax = integerTax/100.0f;
        tax *= 5;
        return  tax/100.0f;
    }
    private float calculateTax(Item item) {
        int taxRate = 0;
        boolean isExempted = false;
        for (int i=0; i < Invoice.EXEMPTED_ITEMS.length; i++ ) {
            if (Invoice.EXEMPTED_ITEMS[i] == item.category) {
                isExempted = true;
                break;
            }
        }
        if (!isExempted) {
            taxRate += Invoice.BASE_SALES_TAX;
        }
        if (item.imported) {
            taxRate += Invoice.IMPORT_TAX;
        }
        float totalTax = item.cost * taxRate;
        // Rounding tax to the nearest 0.05
        totalTax = this.roundTax(totalTax);
        return totalTax;
    }
    public Invoice() {
        invoiceItems = new ArrayList<InvoiceItem>();
    }
    public void addInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.add(invoiceItem);
    }
    public void calculate() {
        ArrayList<InvoiceItem> calculatedInvoiceItems = new ArrayList<InvoiceItem>();
        for (InvoiceItem invoiceItem : invoiceItems) {
            float tax = this.calculateTax(invoiceItem.item);
            invoiceItem.tax = tax * invoiceItem.quantity;
            invoiceItem.finalValue = invoiceItem.item.cost * invoiceItem.quantity + invoiceItem.tax;
            total += invoiceItem.item.cost * invoiceItem.quantity;
            totalTax += invoiceItem.tax;
        }
    }
}
