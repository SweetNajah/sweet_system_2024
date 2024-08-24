package sweet_2024;

public class Supply {
    private String name;
    private int quantity;
    private double price;
    private String status;

    public Supply(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public Supply(String supplyName, int quantityRequested, String status) {
        this.name = supplyName;
        this.quantity = quantityRequested;
        this.status = status;
    }
    public Supply(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.price = 0.0;
        this.status = "Pending";
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SupplyRequest{" +
                "supplyName='" + name + '\'' +
                ", quantityRequested=" + quantity +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }

}
