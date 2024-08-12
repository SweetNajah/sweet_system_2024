package sweet_2024;

import java.time.LocalDate;

public class InstallationRequest {
    private static int idCounter = 1;
    private final int requestId;
    private final Order order;
    private final LocalDate requestDate;
    private boolean isInstalled;

    public InstallationRequest(Order order) {
        this.requestId = idCounter++;
        this.order = order;
        this.requestDate = LocalDate.now();
        this.isInstalled = false;
    }

    public int getRequestId() {
        return requestId;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void markAsInstalled() {
        this.isInstalled = true;
    }

    @Override
    public String toString() {
        return "InstallationRequest{" +
                "requestId=" + requestId +
                ", order=" + order +
                ", requestDate=" + requestDate +
                ", isInstalled=" + isInstalled +
                '}';
    }

    public void setInstalled(boolean b) {

    }

    public int getOrderId() {
        return order.getOrderId();
    }
}

