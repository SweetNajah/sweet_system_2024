package sweet_2024;

public class Feedback {



    private final int id; // Unique identifier for feedback
    private User user;
    private Products products;
    private String feedbackMessage;
    private String response; // For storing responses to feedback
    private int rating;
    private static int idCounter = 1; // Static counter to generate unique IDs


    public Feedback(User user, Products products, String feedbackMessage, int rating) {
        this.id = idCounter++;
        this.user = user;
        this.products = products;
        this.feedbackMessage = feedbackMessage;
        this.rating = rating;
        this.response = ""; // Initialize with no response
    }

    public int getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Products getProduct() {
        return products;
    }

    public void setProduct(Products products) {
        this.products = products;
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
        if(rating >= 0 && rating <= 5){
            this.rating = rating;

        }
        else if (rating > 5){
            this.rating = 5;
        }
           else
               this.rating = 0;

    }

    private void addFeedbackToProduct() {
        products.reviews.add(feedbackMessage);
        products.rates.add(rating);
        updateProductRating();
    }

    private void updateProductRating() {
        int sum = 0;
        for (int rate : products.rates) {
            sum += rate;
        }
        products.rateAvg = sum / (float) products.rates.size();
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "user=" + user.getEmail() +
                ", product=" + products.getProductName() +
                ", feedbackMessage='" + feedbackMessage + '\'' +
                ", rating=" + rating +
                '}';
    }
}
