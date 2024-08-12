package sweet_2024;

public class Dessert {
    private final String name;
    private final String dietaryInfo;
    private final double price;

    public Dessert(String name, String dietaryInfo, double price) {
        this.name = name;
        this.dietaryInfo = dietaryInfo;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDietaryInfo() {
        return dietaryInfo;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Dessert{" +
                "name='" + name + '\'' +
                ", dietaryInfo='" + dietaryInfo + '\'' +
                ", price=" + price +
                '}';
    }
}
