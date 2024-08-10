package sweet_2024;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Products {
    private double revenue;

    float rateAvg;
    protected List<Integer> rates= new ArrayList<>();
    protected List<String> reviews= new ArrayList<>();
    public double getRevenue() {
        return revenue;
    }

    static public ArrayList<String> Sweetes = new ArrayList<>();

    private String productName;
    private String productPrice;
    private String productDescription;
    private String sku;
    private int quantityInStock;
    private int unitsSold;
    private double totalRevenue;
    public boolean is_logged_in = true;

    // Discount-related attributes
    private double discountPercentage;
    private String discountDuration;
    private boolean isDiscountActive;

    public Products() {
        // Default constructor
    }

    public Products(String productName, String productPrice, String productDescription, String sku, int quantityInStock) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.sku = sku;
        this.quantityInStock = quantityInStock;
        this.unitsSold = 0;
        this.totalRevenue = 0.0;
        this.isDiscountActive = false; // Default: No discount
    }

    // Getter and Setter methods
    public String getProductPrice() {
        if (isDiscountActive) {
            double discountedPrice = Double.parseDouble(productPrice) * (1 - discountPercentage / 100);
            return String.format("%.2f", discountedPrice);
        }
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
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

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
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
    // Save a new sweet to the Sweetes list
    public void saveSweet(String sweet) {
        if (is_logged_in) {
            Sweetes.add(sweet);
            System.out.println(sweet + " has been added to the list.");
        } else {
            System.out.println("You need to be logged in to add a sweet.");
        }
    }

    // Delete a sweet from the Sweetes list
    public void deleteSweet(Products sweet) {
        if (is_logged_in) {
            if (Sweetes.contains(sweet)) {
                Sweetes.remove(sweet);
                System.out.println(sweet + " has been removed from the list.");
            } else {
                System.out.println(sweet + " is not in the list.");
            }
        } else {
            System.out.println("You need to be logged in to delete a sweet.");
        }
    }

    // Update a sweet in the Sweetes list
    public void updateSweet(String oldSweet, String newSweet) {
        if (is_logged_in) {
            int index = Sweetes.indexOf(oldSweet);
            if (index != -1) {
                Sweetes.set(index, newSweet);
                System.out.println(oldSweet + " has been updated to " + newSweet + ".");
            } else {
                System.out.println(oldSweet + " is not in the list.");
            }
        } else {
            System.out.println("You need to be logged in to update a sweet.");
        }
    }
    // For demonstration purposes: List all sweets
    public void listSweets() {
        if (is_logged_in) {
            System.out.println("Current sweets in the list:");
            for (String sweet : Sweetes) {
                System.out.println("- " + sweet);
            }
        } else {
            System.out.println("You need to be logged in to view the sweets.");
        }
    }

    // Method to apply a discount
    public void applyDiscount(double discountPercentage, String discountDuration) {
        this.discountPercentage = discountPercentage;
        this.discountDuration = discountDuration;
        this.isDiscountActive = true;
        System.out.printf("Discount of %.2f%% applied to %s for %s.\n", discountPercentage, productName, discountDuration);
    }

    // Method to remove discount
    public void removeDiscount() {
        this.isDiscountActive = false;
        System.out.printf("Discount removed from %s.\n", productName);
    }

    // Method to register a sale
    public void registerSale(int quantity) {
        if (quantity <= quantityInStock) {
            quantityInStock -= quantity;
            unitsSold += quantity;
            totalRevenue += quantity * Double.parseDouble(getProductPrice());
        } else {
            System.out.println("Not enough stock to complete the sale.");
        }
    }

    // Method to calculate total profit (assuming cost price is available or a fixed profit margin)
    public double calculateProfit(double costPrice) {
        return totalRevenue - (unitsSold * costPrice);
    }

    // Method to display the sales dashboard
    public void displaySalesDashboard(List<Products> productsList) {
        System.out.println("Sales Dashboard:");
        System.out.println("----------------------------------------------------");

        double totalProfits = 0.0;

        for (Products product : productsList) {
            System.out.printf("Product: %s | Units Sold: %d | Revenue: $%.2f\n",
                    product.getProductName(), product.getUnitsSold(), product.getTotalRevenue());
            // Assuming a fixed cost price for all products or you can add costPrice as an attribute
            totalProfits += product.calculateProfit(5.0);  // Replace 5.0 with the actual cost price
        }

        System.out.println("----------------------------------------------------");
        System.out.printf("Total Profits: $%.2f\n", totalProfits);
        System.out.println("----------------------------------------------------");

        // Displaying text-based sales trends (in a real application, this would be graphical)
        System.out.println("Sales Trends:");
        for (Products product : productsList) {
            System.out.printf("Product: %s | Sales: %s\n", product.getProductName(), "*".repeat(product.getUnitsSold()));
        }
    }

    // Method to display best-selling products and recommend restocking
    public void displayBestSellingProducts(List<Products> productsList) {
        System.out.println("Best-Selling Products:");
        System.out.println("----------------------------------------------------");

        // Sort products by units sold in descending order
        productsList.sort(Comparator.comparingInt(Products::getUnitsSold).reversed());

        for (Products product : productsList) {
            System.out.printf("Product: %s | Units Sold: %d | Revenue: $%.2f\n",
                    product.getProductName(), product.getUnitsSold(), product.getTotalRevenue());

            // Highlight the best-selling product
            if (product == productsList.getFirst()) {
                System.out.println("-> Best-Selling Product!");
            }

            // Recommend restocking if quantity in stock is low
            if (product.getQuantityInStock() < 10) {  // Threshold for restocking
                System.out.printf("-> Recommend Restocking: Only %d units left in stock!\n", product.getQuantityInStock());
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
                ", quantityInStock=" + quantityInStock +
                '}';
    }

    public String fillProductDetails() {
        return String.format(
                "Product Name: %s\nDescription: %s\nPrice: %s\nSKU: %s\nQuantity in Stock: %d",
                productName, productDescription, productPrice, sku, quantityInStock
        );
    }













}

