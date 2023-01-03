package org.example.api;

import com.google.gson.Gson;
import okhttp3.*;
import org.example.Alert;
import org.example.GetResponse;

import java.io.IOException;

public class ApiFunctionality {
    final OkHttpClient httpClient = new OkHttpClient();
    private int numberOfAlerts = 0;
    private boolean uploaded = false;
    private boolean purged = false;
    private boolean alertType = false;
    private boolean heading = false;
    private boolean description  = false;
    private boolean url = false;
    private boolean imageUrl = false;
    private boolean postedBy = false;
    private boolean priceInCents = false;

    int getNumberOfAlerts() {
        return numberOfAlerts;
    }

    boolean isUploaded(){
        return uploaded;
    }

    boolean isPurged() {
        return purged;
    }

    boolean containsAlertType(){
        return alertType;
    }

    boolean containsHeading(){
        return heading;
    }

    boolean containsDescription(){
        return description;
    }

    boolean containsUrl() {
        return url;
    }

    boolean containsImageUrl() {
        return imageUrl;
    }

    boolean containsPostedBy() {
        return postedBy;
    }

    boolean containsPriceInCents() {
        return priceInCents;
    }

    public void startWithPurgeAlerts() throws IOException {
        Request request = new Request.Builder().url("https://api.marketalertum.com/Alert?userId=3abba092-71ce-4d58-b552-c511d70b09b9").delete().build();
        try (Response response = httpClient.newCall(request).execute()) {}
    }

    void purgeAlerts() throws IOException {
        Request request = new Request.Builder().url("https://api.marketalertum.com/Alert?userId=3abba092-71ce-4d58-b552-c511d70b09b9").delete().build();
        try (Response response = httpClient.newCall(request).execute()) {}

        GetResponse getResponse = getRequestFromMarketAlertUm();
        if (getResponse != null && getResponse.eventLogType == 1){
            purged = true;
            uploaded = false;
            numberOfAlerts = getResponse.systemState.alerts.size();
        } else {
            purged = false;
        }
    }

    void uploadCorrectAlert() throws IOException {
        Alert correctAlert = new Alert(
                2,
                "Apple iPhone 14 Pro (1 TB) - Space Black",
                "6.1-inch Super Retina XDR display featuring Always-On and ProMotion",
                "https://www.amazon.co.uk/Apple-iPhone-14-Pro-128/dp/B0BDJ61GFY/ref=sr_1_1_sspa?crid=3KQB6RYWTPOE5&keywords=Apple+iPhone+14&qid=1666687238&qu=eyJxc2MiOiI0LjU5IiwicXNhIjoiMy42NCIsInFzcCI6IjMuMjAifQ%3D%3D&sprefix=apple+iphone+14%2Caps%2C194&sr=8-1-spons&psc=1",
                "https://m.media-amazon.com/images/I/61XO4bORHUL.__AC_SY445_SX342_QL70_ML2_.jpg",
                164900
        );

        // Upload alert
        String json = new Gson().toJson(correctAlert);

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("https://api.marketalertum.com/Alert")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }

        GetResponse getResponse = getRequestFromMarketAlertUm();
        if (getResponse != null && getResponse.eventLogType == 0){
            purged = false;
            uploaded = true;
            numberOfAlerts = getResponse.systemState.alerts.size();
            Alert finalAlert = getResponse.systemState.alerts.get(numberOfAlerts - 1);

            alertType = finalAlert.alertType >= 1 && finalAlert.alertType <= 6;

            heading = !finalAlert.heading.equals("");

            description = !finalAlert.description.equals("");

            url = !finalAlert.url.equals("");

            imageUrl = !finalAlert.imageURL.equals("");

            postedBy = !finalAlert.postedBy.equals("");

            priceInCents = finalAlert.priceInCents > 0;
        } else {
            uploaded = false;
            purged = false;
            alertType = false;
            heading = false;
            description = false;
            url = false;
            imageUrl = false;
            postedBy = false;
            priceInCents = false;
        }
    }

    GetResponse getRequestFromMarketAlertUm() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.marketalertum.com/EventsLog/3abba092-71ce-4d58-b552-c511d70b09b9")
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String jsonString = response.body().string();

            GetResponse[] r = new Gson().fromJson(jsonString, GetResponse[].class);

            // Return GetResponse object
            return r[0];
        }
    }
}
