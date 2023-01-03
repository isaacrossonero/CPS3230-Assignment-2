package main;

public class Alert {
	// Attributes
	protected int alertType;
    protected String heading;
    protected String description;
    protected String url;
    protected String imageUrl;
    protected String postedBy = "c55bc56a-232c-46a4-9778-7f0d41690aa2";
    protected int priceInCents;

    public Alert(int alertType, String heading, String description, String url, String imageUrl, int priceInCents) {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.priceInCents = priceInCents;
    }
    
    public Alert(){}
}
