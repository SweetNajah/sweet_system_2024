package sweet_2024;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public enum OrderState {
        PROCESSING, SHIPPED, DELIVERED
    }

    private int id;
    private OrderState orderState; // Field to store the current state of the order
    private static List<Order> orders = new ArrayList<>(); // Shared list of orders

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", state=" + orderState +
                '}';
    }

    // Static block to initialize some orders
    static {
        Order order1 = new Order();
        order1.setId(12345);
        order1.setOrderState(OrderState.PROCESSING);
        orders.add(order1);

        Order order2 = new Order();
        order2.setId(12346);
        order2.setOrderState(OrderState.SHIPPED);
        orders.add(order2);

        Order order3 = new Order();
        order3.setId(12347);
        order3.setOrderState(OrderState.DELIVERED);
        orders.add(order3);
    }

    public String view_list_of_orders() {
        // StringBuilder to accumulate the order details
        StringBuilder orderListString = new StringBuilder("List of Orders:\n");
        for (Order order : orders) {
            orderListString.append(order.toString()).append("\n");
        }
        return orderListString.toString();
    }

    public Order select_order_number(int orderId) {
        // Loop through the list to find the order with the matching ID
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order; // Return the found order
            }
        }
        return null; // Return null if no order with the given ID is found
    }

    public String see_current_state_order(int orderId) {
        // Find the order by its ID
        Order order = select_order_number(orderId);
        if (order != null) {
            return "Order ID " + orderId + " is currently in state: " + order.getOrderState();
        } else {
            return "Order not found.";
        }
    }

    public String update_order_status(int orderId, OrderState newStatus) {
        // Find the order by its ID
        Order order = select_order_number(orderId);
        if (order != null) {
            // Update the order's status
            order.setOrderState(newStatus);

            // Notify the customer
            notifyCustomer(orderId, newStatus);

            // Return a message reflecting the updated status
            return "Order ID " + orderId + " status updated to " + newStatus + ". The current status is now: " + order.getOrderState() + ".";
        } else {
            return "Order not found.";
        }
    }

    // Method to notify the customer about the status change
    private void notifyCustomer(int orderId, OrderState newStatus) {
        // For demonstration purposes, we'll use a simple print statement
        // In a real application, this could involve sending an email, SMS, or app notification
        System.out.println("Notification: Your order ID " + orderId + " has been updated to " + newStatus + ".");
    }




}
