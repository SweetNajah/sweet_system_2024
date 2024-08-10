package sweet_2024;

public class Inquiry {
    private User user;
    private String inquiryMessage;
    private Product product; // Optional product related to the inquiry

    public Inquiry(User user, String inquiryMessage) {
        this.user = user;
        this.inquiryMessage = inquiryMessage;
    }

    public Inquiry(User user, String inquiryMessage, Product product) {
        this.user = user;
        this.inquiryMessage = inquiryMessage;
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInquiryMessage() {
        return inquiryMessage;
    }

    public void setInquiryMessage(String inquiryMessage) {
        this.inquiryMessage = inquiryMessage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "user=" + user.getEmail() +
                ", inquiryMessage='" + inquiryMessage + '\'' +
                (product != null ? ", product=" + product.getName() : "") +
                '}';
    }
}
