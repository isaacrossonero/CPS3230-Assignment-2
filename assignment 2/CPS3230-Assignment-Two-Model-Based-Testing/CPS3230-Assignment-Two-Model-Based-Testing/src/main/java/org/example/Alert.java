package org.example;

public class Alert {
    // Attributes
    public int alertType;
    public String heading;
    public String description;
    public String url;
    public String imageURL;
    public String postedBy = "3abba092-71ce-4d58-b552-c511d70b09b9";
    public int priceInCents;

    public Alert(int alertType, String heading, String description, String url, String imageUrl, int priceInCents) {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.imageURL = imageUrl;
        this.priceInCents = priceInCents;
    }

    public Alert(){}
}