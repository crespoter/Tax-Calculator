package Models;

public class Item {
    String name;
    boolean imported;
    float cost;
    Category category;
    public Item(String name, boolean imported, float cost, Category category) {
        this.name = name;
        this.imported = imported;
        this.cost = cost;
        this.category = category;
    }
}
