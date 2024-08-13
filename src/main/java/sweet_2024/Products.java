package sweet_2024;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Products {

    private double revenue;
    private double productRating;
    private String category;
    float rateAvg;
    protected List<Integer> rates= new ArrayList<>();
    protected List<String> reviews= new ArrayList<>();
    private Products products;



    private String productName;
    private String productDescription;
    private String sku;
    private String discountDuration;

    private int quantity;
    private int unitsSold;

    private double totalRevenue;
    private double discountPercentage;
    private double productPrice;
    public boolean is_logged_in = true;
    private boolean isDiscountActive;
    static public ArrayList<String> Sweets = new ArrayList<>();

    public Products(String chocolateCake) {
        this.productName=chocolateCake;
    }


    public double getRevenue() {
        return revenue;
    }


    public Products() {
    }

    public Products(String productName, double productPrice, String productDescription, String sku, int quantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.sku = sku;
        this.quantity = quantity;
        this.unitsSold = 0;
        this.totalRevenue = 0.0;
        this.isDiscountActive = false;
    }

    public double getPrice() {
        if (isDiscountActive) {
            return Double.parseDouble(String.valueOf(productPrice)) * (1 - discountPercentage / 100);
        }
        return productPrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public String getDiscountDuration() {
        return discountDuration;
    }

    public boolean isDiscountActive() {
        return isDiscountActive;
    }

    public void saveSweet(String sweet) {
        if (is_logged_in) {
            Sweets.add(sweet);
            System.out.println(sweet + " has been added to the list.");
        } else {
            System.out.println("You need to be logged in to add a sweet.");
        }
    }

    public void deleteSweet(Products sweet) {
        if (is_logged_in) {
            if (Sweets.contains(sweet)) {
                Sweets.remove(sweet);
                System.out.println(sweet + " has been removed from the list.");
            } else {
                System.out.println(sweet + " is not in the list.");
            }
        } else {
            System.out.println("You need to be logged in to delete a sweet.");
        }
    }

    public void updateSweet(String oldSweet, String newSweet) {
        if (is_logged_in) {
            int index = Sweets.indexOf(oldSweet);
            if (index != -1) {
                Sweets.set(index, newSweet);
                System.out.println(oldSweet + " has been updated to " + newSweet + ".");
            } else {
                System.out.println(oldSweet + " is not in the list.");
            }
        } else {
            System.out.println("You need to be logged in to update a sweet.");
        }
    }

    public void listSweets() {
        if (is_logged_in) {
            System.out.println("Current sweets in the list:");
            for (String sweet : Sweets) {
                System.out.println("- " + sweet);
            }
        } else {
            System.out.println("You need to be logged in to view the sweets.");
        }
    }

    public void applyDiscount(double discountPercentage, String discountDuration) {
        this.discountPercentage = discountPercentage;
        this.discountDuration = discountDuration;
        this.isDiscountActive = true;
        System.out.printf("Discount of %.2f%% applied to %s for %s.\n", discountPercentage, productName, discountDuration);
    }

    public void removeDiscount() {
        this.isDiscountActive = false;
        System.out.printf("Discount removed from %s.\n", productName);
    }

    public void registerSale(int quantity) {
        if (quantity <= this.quantity) {
            this.quantity -= quantity;
            unitsSold += quantity;
            totalRevenue += quantity * getPrice();
        } else {
            System.out.println("Not enough stock to complete the sale.");
        }
    }

    public double calculateProfit(double costPrice) {
        return totalRevenue - (unitsSold * costPrice);
    }

    public void displaySalesDashboard(List<Products> productsList) {
        System.out.println("Sales Dashboard:");
        System.out.println("----------------------------------------------------");

        double totalProfits = 0.0;

        for (Products product : productsList) {
            System.out.printf("Product: %s | Units Sold: %d | Revenue: $%.2f\n",
                    product.getName(), product.getUnitsSold(), product.getTotalRevenue());
            totalProfits += product.calculateProfit(5.0);
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("Total Profits: $%.2f\n", totalProfits);
        System.out.println("----------------------------------------------------");
        System.out.println("Sales Trends:");
        for (Products product : productsList) {
            System.out.printf("Product: %s | Sales: %s\n", product.getName(), "*".repeat(product.getUnitsSold()));
        }
    }

    public void displayBestSellingProducts(List<Products> productsList) {
        System.out.println("Best-Selling Products:");
        System.out.println("----------------------------------------------------");
        productsList.sort(Comparator.comparingInt(Products::getUnitsSold).reversed());
        for (Products product : productsList) {
            System.out.printf("Product: %s | Units Sold: %d | Revenue: $%.2f\n",
                    product.getName(), product.getUnitsSold(), product.getTotalRevenue());
            if (product == productsList.getFirst()) {
                System.out.println("-> Best-Selling Product!");
            }
            if (product.getQuantity() < 10) {
                System.out.printf("-> Recommend Restocking: Only %d units left in stock!\n", product.getQuantity());
            }
        }
        System.out.println("----------------------------------------------------");
    }

    @Override
    public String toString() {
        return "Products{" +
                "productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", sku='" + sku + '\'' +
                ", quantityInStock=" + quantity +
                '}';
    }

    public String fillProductDetails() {
        return String.format(
                "Product Name: %s\nDescription: %s\nPrice: %s\nSKU: %s\nQuantity in Stock: %d",
                productName, productDescription, productPrice, sku, quantity
        );
    }


    public Products(String name, int quantity, double price) {
        this.productName = name;
        this.quantity = quantity;
        this.productPrice = price;
    }

    public String getName() {
        if (this.products == null) {
            return "No product";
        }
        return productName;
    }

    public void setPrice(double price) {
        this.productPrice = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public char[] getProductRating() {
        String ratingStr = String.valueOf(productRating);
        return ratingStr.toCharArray();
    }

    public Object getCategory() {
        return category;
    }


}

