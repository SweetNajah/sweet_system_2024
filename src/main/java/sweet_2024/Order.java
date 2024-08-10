package sweet_2024;

import java.security.Principal;

public class Order {
    private final Products selectedProduct;

    private int orderId;
    private String storeOwnerName;
    private String productName;
    private int quantity;
    private String status;
    private static int idCounter = 0; // Used to generate unique IDs for each order

    // Constructor
    public Order(Products selectedProduct, String storeOwnerName, String productName, int quantity) {
        this.selectedProduct = selectedProduct;
        this.orderId = ++idCounter; // Increment and assign unique ID
        this.storeOwnerName = storeOwnerName;
        this.productName = productName;
        this.quantity = quantity;
        this.status = "Pending"; // Default status is "Pending"
    }

    public Order(Products selectedProduct, int quantity) {
        this.selectedProduct=selectedProduct;
        this.quantity=quantity;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public String getStoreOwnerName() {
        return storeOwnerName;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for displaying order details
    @Override
    public String toString() {
        return "Order ID: " + orderId +
                "\nStore Owner: " + storeOwnerName +
                "\nProduct: " + productName +
                "\nQuantity: " + quantity +
                "\nStatus: " + status + "\n";
    }


    public Principal getProduct() {
        return getProduct();
    }
}
