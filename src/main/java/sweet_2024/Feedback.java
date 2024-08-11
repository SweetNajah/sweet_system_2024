package sweet_2024;

public class Feedback {
    private User user;
    private Product product;
    private String feedbackMessage;
    private int rating;

    public Feedback(User user, Product product, String feedbackMessage, int rating) {
        this.user = user;
        this.product = product;
        this.feedbackMessage = feedbackMessage;
        this.rating = rating;
        addFeedbackToProduct();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        product.reviews.add(feedbackMessage);
        product.rates.add(rating);
        updateProductRating();
    }

    private void updateProductRating() {
        int sum = 0;
        for (int rate : product.rates) {
            sum += rate;
        }
        product.rateAvg = sum / (float) product.rates.size();
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "user=" + user.getEmail() +
                ", product=" + product.getName() +
                ", feedbackMessage='" + feedbackMessage + '\'' +
                ", rating=" + rating +
                '}';
    }
}
