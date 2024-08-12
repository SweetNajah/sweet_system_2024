package sweet_2024;

public class SupplyRequest {
    private final String supplyName;
    private final int quantityRequested;
    private final String status;  // Example statuses could be "Pending", "Approved", "Denied"

    public SupplyRequest(String supplyName, int quantityRequested, String status) {
        this.supplyName = supplyName;
        this.quantityRequested = quantityRequested;
        this.status = status;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public int getQuantityRequested() {
        return quantityRequested;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SupplyRequest{" +
                "supplyName='" + supplyName + '\'' +
                ", quantityRequested=" + quantityRequested +
                ", status='" + status + '\'' +
                '}';
    }
}
