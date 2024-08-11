package sweet_2024;

public class Sale {
    private final String productName;
    private final int quantitySold;
    private final double totalSaleAmount;

    public Sale(String productName, int quantitySold, double totalSaleAmount) {
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalSaleAmount = totalSaleAmount;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getTotalSaleAmount() {
        return totalSaleAmount;
    }

    @Override
    public String toString() {
        return String.format("Product: %s, Quantity Sold: %d, Total Sale: $%.2f",
                productName, quantitySold, totalSaleAmount);
    }
}
