package sweet_2024;

public class Order {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }
}
