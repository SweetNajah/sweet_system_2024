package sweet_2024;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;


public class Order {
    private static final Logger LOGGER = Logger.getLogger(Order.class.getName());
    private final Products selectedProduct;
    private List<Products> orderedProducts;
    private double totalPrice;

    private int orderId;
    private String storeOwnerName;
    private String productName;
    private  int quantity;
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
    public Order(Products selectedProduct, List<Products> orderedProducts, int quantity) {
        this.selectedProduct = selectedProduct;
        this.orderedProducts = orderedProducts;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
    }

    public Order(Products selectedProduct, int quantity) {
        this.selectedProduct = selectedProduct;
        this.quantity=quantity;

    }

    private double calculateTotalPrice() {
        double total = 0.0;
        for (Products product : orderedProducts) {
            total += product.getPrice();
        }
        return total;
    }


    public List<Products> getOrderedProducts() {
        return orderedProducts;
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


    public double getTotalPrice() {
        return totalPrice;
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

    public void processOrder(Order order) {
        if (order == null) {
            LOGGER.warning("Order is null, cannot process.");
            return;
        }
        order.setStatus("Processed");
        LOGGER.info("Order processed successfully.");
    }



}
