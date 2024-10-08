package sweet_2024;

public class Feedback {


    private final int id;
    private String userId;
    private String status;
    private User user;
    private Products products;
    private String feedbackMessage;
    private String response;
    private int rating;
    private static int idCounter = 1;
    private static final String STATUS_PENDING = "Pending";

    public Feedback(User user, Products products, String feedbackMessage, int rating) {
        this.id = idCounter++;
        this.user = user;
        this.feedbackMessage = feedbackMessage;
        this.rating = rating;
        this.response = "";
        this.products = products;
        this.status = STATUS_PENDING;
        this.userId = (user != null) ? user.getUserName() : "Anonymous";
    }

    public Feedback(String userId, String feedbackMessage, int id) {
        this.userId = userId;
        this.id = id;
        this.feedbackMessage = feedbackMessage;
        this.rating = 0;
        this.status = STATUS_PENDING;
    }

    public Feedback(int i, String feedbackMessage, String open) {
        this.id=i;
        this.feedbackMessage=feedbackMessage;
        this.userId=open;
    }

    public Feedback() {
        this.id = idCounter++;
        this.status = STATUS_PENDING;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
     this.status=status;
    }
    public int getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        updateUserName();
    }

    private void updateUserName() {
        this.userId = (user != null) ? user.getUserName() : "Anonymous";
    }

    public Products getProduct() {
        return products;
    }

    public void setProduct(Products products) {
        this.products = products;
        updateProductRating(products);
    }

    public String getFeedbackMessage() {

        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public int getRating() {
        return rating;
    }
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setRating(int rating) {
        if (rating >= 0 && rating <= 5) {
            this.rating = rating;
        } else if (rating > 5) {
            this.rating = 5;
        } else {
            this.rating = 0;
        }
    }





    private void addFeedbackToProduct() {
        if (products == null) {
            return;
        }
        products.reviews.add(feedbackMessage);
        products.rates.add(rating);
        updateProductRating(products);
    }

    private void updateProductRating(Products products) {
        if (products.rates.isEmpty()) {
            products.rateAvg = 0;
            return;
        }
        int sum = products.rates.stream().mapToInt(Integer::intValue).sum();
        products.rateAvg = sum / (float) products.rates.size();
    }

    @Override
    public String toString() {
        String productName = (this.products != null) ? this.products.getName() : "No product";
        String userName = (this.user != null) ? this.user.getUserName() : "No user";
        return "Feedback{" +
                "productName='" + productName + '\'' +
                ", userName='" + userName + '\'' +
                ", rating=" + rating +
                ", feedbackMessage='" + feedbackMessage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public void setComment(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public String getComment() {
        return feedbackMessage;
    }
}
