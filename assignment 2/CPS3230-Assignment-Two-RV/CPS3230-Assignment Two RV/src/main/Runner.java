package main;

import java.io.IOException;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.gson.Gson;

public class Runner {
	
	final Random random = new Random();
	final OkHttpClient httpClient = new OkHttpClient();
	
	public void uploadCorrectAlert() {
		System.out.println("Alert uploaded!");
	}
	
	public void purgeAlerts() {
		System.out.println("Alerts purged!");
	}
	
	public void attemptUploadCorrectAlert() throws IOException {
		
		Alert alert = new Alert(
	            1,
	            "Lot 14 Metal DieCast model car",
	            "Lot 14 Metal DieCast model car",
	            "https://www.maltapark.com/item/details/9514895",
	            "https://www.maltapark.com/asset/itemphotos/9514895/9514895_1.jpg?_ts=1",
	            400
		    );
		System.out.println("Creating an alert");
		
		
		String json = new Gson().toJson(alert);
        
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
		
	}
	
	public void attemptPurgeAlerts() throws IOException {
		Request request = new Request.Builder().url("https://api.marketalertum.com/Alert?userId=c55bc56a-232c-46a4-9778-7f0d41690aa2").delete().build();

        try (Response response = httpClient.newCall(request).execute()) {}
	}
	
	
	public void run(final APISystem api) throws IOException {
		final Random rand = new Random();
		
		this.attemptPurgeAlerts();
		api.setApiAttributesFromGetRequest();
		int numberOfAlerts = api.numberOfAlerts;
		
		while(true){
			final int randomNumber = rand.nextInt(6);
					
			switch(randomNumber){
			case 1:
				this.attemptPurgeAlerts();
				api.setApiAttributesFromGetRequest();
				if (api.eventLogType == 1 && api.numberOfAlerts == 0){
					numberOfAlerts = api.numberOfAlerts;
					this.purgeAlerts();
				}
				break;
			default:
				this.attemptUploadCorrectAlert();
				api.setApiAttributesFromGetRequest();
				if (api.eventLogType == 0 && api.numberOfAlerts == (numberOfAlerts + 1)){
					numberOfAlerts = api.numberOfAlerts;
					this.uploadCorrectAlert();
				}
				break;
			}
			
			// program pause
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) throws IOException {
		final Runner r = new Runner();
		final APISystem api = r.new APISystem();
		r.run(api);
	}
	
	public class APISystem {
		private int numberOfAlerts;
		private int eventLogType;
		
		public APISystem() {}
		

		public void setApiAttributesFromGetRequest() throws IOException {
			Request request = new Request.Builder()
            .url("https://api.marketalertum.com/EventsLog/c55bc56a-232c-46a4-9778-7f0d41690aa2")
            .addHeader("Content-Type", "application/json")
            .build();

		    try (Response response = httpClient.newCall(request).execute()) {
		
		        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

		        String jsonString = response.body().string();
		
		        GetResponse[] r = new Gson().fromJson(jsonString, GetResponse[].class);
		        
		        this.numberOfAlerts = r[0].systemState.alerts.size();
		        this.eventLogType = r[0].eventLogType;
		    }
			
		}
	}
	
}