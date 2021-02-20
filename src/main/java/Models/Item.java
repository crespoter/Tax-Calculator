package Models;

public class Item {
    public String name;
    public boolean imported;
    public float cost;
    public Category category;
    public Item(String name, boolean imported, float cost, Category category) {
        this.name = name;
        this.imported = imported;
        this.cost = cost;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", imported=" + imported +
                ", cost=" + cost +
                ", category=" + category +
                '}';
    }
}
