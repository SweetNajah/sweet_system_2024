package sweet_2024;

public class Inquiry {
    private User user;
    private String inquiryMessage;
    private Products products; // Optional product related to the inquiry
    private String inquiryId;
    private String question;
    private String answer;

    public Inquiry(User user, String inquiryMessage) {
        this.user = user;
        this.inquiryMessage = inquiryMessage;
    }

    public Inquiry(User user, String inquiryMessage, Products products) {
        this.user = user;
        this.inquiryMessage = inquiryMessage;
        this.products = products;
    }

    public Inquiry() {
        
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

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    @Override
    public String toString() {
        return "Inquiry{" +
                "user=" + user.getEmail() +
                ", inquiryMessage='" + inquiryMessage + '\'' +
                (products != null ? ", product=" + products.getName() : "") +
                ", inquiryId='" + inquiryId + '\'' +
                ", question='" + question + '\'' +
                '}';
    }


}
