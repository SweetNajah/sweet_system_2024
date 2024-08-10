package sweet_2024;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private final String name;
    private int unitsSold;
    private double revenue;
    int quantity;
    int price;
    float rateAvg;
    LocalDate manufactureDate;
    LocalDate expirationDate;
    protected List<Integer> rates= new ArrayList<>();
    protected List<String> reviews= new ArrayList<>();
    public String getName() {
        return name;
    }

    public Product(String name) {
        this.name = name;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public double getRevenue() {
        return revenue;
    }

}

