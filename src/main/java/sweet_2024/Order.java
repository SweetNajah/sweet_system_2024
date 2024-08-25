package sweet_2024;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Order {
    private static final Logger LOGGER = Logger.getLogger(Order.class.getName());
    private Products selectedProduct;
    private List<Products> orderedProducts;
    private double totalPrice;
    private int orderId;
    private String storeOwnerName;
    private String productName;
    private int quantity;
    private String status;
    private static int idCounter = 0;
    private LocalDate requestDate;
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

    public Order() {
        this.orderId = ++idCounter;
        this.status = "Pending";
        this.requestDate = LocalDate.now();
        this.isInstalled = false;
    }


    public static List<Order> filterOrdersByStatus(List<Order> orders, String status) {
        if (orders == null || status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return orders.stream()
                .filter(order -> order.getStatus().equalsIgnoreCase(status))
                .toList();
    }


    double calculateTotalPrice() {
        return orderedProducts != null ? orderedProducts.stream().mapToDouble(Products::getPrice).sum() : 0.0;
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

    public void setInstalled(boolean isInstalled) {
        this.isInstalled = isInstalled;
    }

    public void setOrderId(String orderId) {
        try {
            this.orderId = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid order ID format: " + orderId);
        }
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        LOGGER.log(Level.INFO, "Order status updated to: {0}", newStatus);
    }

    public void cancelOrder() {
        if ("Pending".equals(this.status)) {
            this.status = "Cancelled";
            LOGGER.log(Level.INFO, "Order ID {0} has been cancelled.", orderId);
        } else {
            LOGGER.log(Level.WARNING, "Order ID {0} cannot be cancelled. Current status: {1}", new Object[]{orderId, status});
        }
    }


    public String generateDetailedReceipt() {
        StringBuilder receipt = new StringBuilder();
        appendBasicReceiptInfo(receipt);
        appendProductDetails(receipt);
        appendTotalPriceAndInstallation(receipt);

        return receipt.toString();
    }

    private void appendBasicReceiptInfo(StringBuilder receipt) {
        receipt.append("Order Receipt\n")
                .append("Order ID: ").append(orderId).append("\n")
                .append("Store Owner: ").append(storeOwnerName != null ? storeOwnerName : "N/A").append("\n")
                .append("Request Date: ").append(requestDate != null ? requestDate.toString() : "N/A").append("\n")
                .append("Status: ").append(status != null ? status : "N/A").append("\n\n");
    }

    private void appendProductDetails(StringBuilder receipt) {
        if (orderedProducts != null && !orderedProducts.isEmpty()) {
            receipt.append("Ordered Products:\n");
            for (Products product : orderedProducts) {
                receipt.append("Product Name: ").append(product.getName())
                        .append(", Price: $").append(product.getPrice())
                        .append("\n");
            }
        } else {
            receipt.append("Product: ").append(productName != null ? productName : "N/A")
                    .append("\nQuantity: ").append(quantity)
                    .append("\n");
        }
    }

    private void appendTotalPriceAndInstallation(StringBuilder receipt) {
        receipt.append("\nTotal Price: $").append(String.format("%.2f", totalPrice))
                .append("\nInstalled: ").append(isInstalled ? "Yes" : "No").append("\n");
    }


    public boolean isValidOrder() {
        if (selectedProduct == null) {
            logInvalidOrder("selected product is null.");
            return false;
        }
        if (isNullOrEmpty(storeOwnerName)) {
            logInvalidOrder("store owner name is missing.");
            return false;
        }
        if (isNullOrEmpty(productName)) {
            logInvalidOrder("product name is missing.");
            return false;
        }
        if (quantity <= 0) {
            logInvalidOrder("quantity must be greater than zero.");
            return false;
        }
        return true;
    }

    private void logInvalidOrder(String message) {
        LOGGER.warning("Invalid order: " + message);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }


}
