package sweet_2024;

public class Inquiry {
    private User user;
    private String inquiryMessage;
    private Products products; // Optional product related to the inquiry

    public Inquiry(User user, String inquiryMessage) {
        this.user = user;
        this.inquiryMessage = inquiryMessage;
    }

    public Inquiry(User user, String inquiryMessage, Products products) {
        this.user = user;
        this.inquiryMessage = inquiryMessage;
        this.products = products;
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

    public Products getProduct() {
        return products;
    }

    public void setProduct(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "user=" + user.getEmail() +
                ", inquiryMessage='" + inquiryMessage + '\'' +
                (products != null ? ", product=" + products.getName() : "") +
                '}';
    }
}
