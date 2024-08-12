package sweet_2024;

import java.util.List;
import java.util.logging.Logger;
import java.time.LocalDate;

public class Order {
    private static final Logger LOGGER = Logger.getLogger(Order.class.getName());
    private final Products selectedProduct;
    private List<Products> orderedProducts;
    private double totalPrice;
    private final int orderId;
    private String storeOwnerName;
    private String productName;
    private final int quantity;
    private String status;
    private static int idCounter = 0; // Used to generate unique IDs for each order
    private final LocalDate requestDate;
    private boolean isInstalled;
    private Order order;
    public Order(Products selectedProduct, String storeOwnerName, String productName, int quantity) {
        this.selectedProduct = selectedProduct;
        this.orderId = ++idCounter;
        this.storeOwnerName = storeOwnerName;
        this.productName = productName;
        this.quantity = quantity;
        this.status = "Pending";
        this.requestDate = LocalDate.now();
        this.isInstalled = false;
    }

    public Order(Products selectedProduct, List<Products> orderedProducts, int quantity) {
        this.selectedProduct = selectedProduct;
        this.orderedProducts = orderedProducts;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
        this.orderId = ++idCounter;
        this.status = "Pending";
        this.requestDate = LocalDate.now();
        this.isInstalled = false;
    }

    public Order(Products selectedProduct, int quantity) {
        this.selectedProduct = selectedProduct;
        this.quantity = quantity;
        this.orderId = ++idCounter;
        this.status = "Pending";
        this.requestDate = LocalDate.now();
        this.isInstalled = false;
    }


    private double calculateTotalPrice() {
        double total = 0.0;
        if (orderedProducts != null) {
            for (Products product : orderedProducts) {
                total += product.getPrice();
            }
        }
        return total;
    }

    public List<Products> getOrderedProducts() {
        return orderedProducts;
    }

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

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void markAsInstalled() {
        this.isInstalled = true;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
                "\nStore Owner: " + storeOwnerName +
                "\nProduct: " + productName +
                "\nQuantity: " + quantity +
                "\nStatus: " + status +
                "\nRequest Date: " + requestDate +
                "\nInstalled: " + isInstalled + "\n";
    }

    public void processOrder(Order order) {
        if (order == null) {
            LOGGER.warning("Order is null, cannot process.");
            return;
        }
        order.setStatus("Processed");
        LOGGER.info("Order processed successfully.");
    }

    public void setInstalled(boolean b) {

    }
}
