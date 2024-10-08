package sweet_2024;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Products {

    float rateAvg;
    int quantity;
    private int quantityInStock;
    private int unitsSold;
    private double productPrice;
    private double totalRevenue;
    private double productRating;
    private double discountPercentage;
    private double revenue;
    private String category;
    private String discountDuration;
    private String productName;
    private String productDescription;
    private String sku;
    boolean isLoggedIn=true;
    private boolean isDiscountActive;
    private Products products;
    protected List<Integer> rates = new ArrayList<>();
    protected List<String> reviews = new ArrayList<>();
    static public ArrayList<String> Sweets = new ArrayList<>();
    static public ArrayList<String> Sweetes = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(Products.class.getName());


    public Products() {
    }

    public Products(String name, int quantity, double price) {
        this.productName = name;
        this.quantity = quantity;
        this.productPrice = price;
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

    public Products(String cake, double v) {
        this.productName = cake;
        this.productPrice = v;
        this.quantity = 0;
        this.unitsSold = 0;
        this.totalRevenue = 0.0;
        this.isDiscountActive = false;
    }


    public String getProductName() {
        return productName;
    }


    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void saveSweet(String sweet, String pageName) {
        if (navigateToPage(pageName)) {
            if (isLoggedIn) {
                Sweetes.add(sweet);
                LOGGER.log(Level.INFO, "{0} has been added to the list.\n", sweet);
            } else {
                LOGGER.info("You need to be logged in to add a sweet."+ "\n");
            }
        } else {
            LOGGER.log(Level.INFO, "Failed to navigate to the {0} page. Sweet not saved.\n", pageName);
        }
    }

    public boolean navigateToPage(String pageName) {
        if (pageName != null && !pageName.isEmpty()) {
            LOGGER.log(Level.INFO, "Navigated to {0} page.", pageName);
            return true;
        } else {
            LOGGER.warning("Invalid page name provided.");
            return false;
        }
    }


    public void setDiscountParameters(double discountPercentage, String discountDuration) {
        this.discountPercentage = discountPercentage;
        this.discountDuration = discountDuration;
        System.out.printf("Discount parameters set: %.2f%% discount for %s.\n", discountPercentage, discountDuration);
    }

    public String getDiscountDetails() {
        if (isDiscountActive) {
            return String.format("Discount: %.2f%% off for %s!", discountPercentage, discountDuration);
        } else {
            return "No discount available. Regular price: $" + productPrice;
        }
    }

    public Products(String chocolateCake) {
        this.productName=chocolateCake;
    }


    public double getRevenue() {
        return revenue;
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
    public void setUnitsSold(int unitsSold) {
        this.unitsSold=unitsSold;
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

    public void deleteSweet(String sweet) {
        if (isLoggedIn) {
            if (Sweets.contains(sweet)) {
                Sweets.remove(sweet);
                LOGGER.info(sweet + " has been removed from the list."+ "\n");
            } else {
                LOGGER.info(sweet + " is not in the list."+ "\n");
            }
        } else {
            LOGGER.info("You need to be logged in to delete a sweet."+ "\n");
        }
    }

    public void updateSweet(String oldSweet, String newSweet) {
        if (isLoggedIn) {
            int index = Sweets.indexOf(oldSweet);
            if (index != -1) {
                Sweets.set(index, newSweet);
                LOGGER.info(oldSweet + " has been updated to " + newSweet + "."+ "\n");
            } else {
                LOGGER.info(oldSweet + " is not in the list."+ "\n");
            }
        } else {
            LOGGER.info("You need to be logged in to update a sweet."+ "\n");
        }
    }

    public void listSweets() {
        if (isLoggedIn) {
            LOGGER.info("Current sweets in the list:"+ "\n");
            for (String sweet : Sweets) {
                LOGGER.info("- " + sweet);
            }
        } else {
            LOGGER.info("You need to be logged in to view the sweets."+ "\n");
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
            LOGGER.info("Not enough stock to complete the sale."+ "\n");
        }
    }

    public double calculateProfit(double costPrice) {
        return totalRevenue - (unitsSold * costPrice);
    }

    public void displaySalesDashboard(List<Products> productsList) {
        LOGGER.info("Sales Dashboard:\n");
        LOGGER.info("----------------------------------------------------\n");
        double totalProfits = 0.0;
        for (Products product : productsList) {
            LOGGER.log(Level.INFO, "Product: {0} | Units Sold: {1} | Revenue: ${2,number,#.##}",
                    new Object[]{product.getName(), product.getUnitsSold(), product.getTotalRevenue()});

            totalProfits += product.calculateProfit(5.0);
        }
        LOGGER.info("----------------------------------------------------\n");
        LOGGER.log(Level.INFO, "Total Profits: ${0,number,#.##}\n", totalProfits);
        LOGGER.info("----------------------------------------------------\n");
        LOGGER.info("Sales Trends:");
        for (Products product : productsList) {
            LOGGER.log(Level.INFO, "Product: {0} | Sales: {1}");
        }
    }


    public void displayBestSellingProducts(List<Products> productsList) {
        if (navigateToPage("Product Analytics")) {
            logBestSellingProducts(productsList);
        } else {
            LOGGER.warning("Failed to navigate to the Product Analytics page.\n");
        }
    }
    private void logBestSellingProducts(List<Products> productsList) {
        LOGGER.info("Navigated to Product Analytics page.\n");
        LOGGER.info("Best-Selling Products:\n");
        LOGGER.info("----------------------------------------------------\n");
        productsList.sort(Comparator.comparingInt(Products::getUnitsSold).reversed());
        for (Products product : productsList) {
            logProductDetails(product, productsList.get(0));
        }
        LOGGER.info("----------------------------------------------------\n");
    }
    private void logProductDetails(Products product, Products bestSellingProduct) {
        LOGGER.info(String.format("Product: %s | Units Sold: %d | Revenue: $%.2f",
                product.getName(), product.getUnitsSold(), product.getTotalRevenue()));
        if (product == bestSellingProduct) {
            LOGGER.info("-> Best-Selling Product!\n");
        }
        if (product.getQuantity() < 10) {
            LOGGER.info(String.format("-> Recommend Restocking: Only %d units left in stock!", product.getQuantity()));
        }
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

    public void setName(String productName) {
        this.productName = productName;
    }

}