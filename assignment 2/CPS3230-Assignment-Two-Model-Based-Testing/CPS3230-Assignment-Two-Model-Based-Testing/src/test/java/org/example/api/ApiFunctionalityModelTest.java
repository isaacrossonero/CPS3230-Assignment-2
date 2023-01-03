package org.example.api;

import junit.framework.Assert;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.example.GetResponse;
import org.example.api.enums.ApiFunctionalityStates;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class ApiFunctionalityModelTest implements FsmModel {
    private ApiFunctionalityStates modelState;
    private int numberOfAlerts;
    private boolean uploaded;
    private boolean purged;
    private boolean alertType;
    private boolean heading;
    private boolean description;
    private boolean url;
    private boolean imageUrl;
    private boolean postedBy;
    private boolean priceInCents;

    private ApiFunctionality sut;

    public ApiFunctionalityStates getState() { return modelState; }

    public void reset(final boolean b){
        modelState = ApiFunctionalityStates.API_STARTED;
        numberOfAlerts = 0;
        uploaded = false;
        purged = false;
        alertType = false;
        heading = false;
        description = false;
        url = false;
        imageUrl = false;
        postedBy = false;
        priceInCents = false;
        if(b) {
            ApiFunctionality a = new ApiFunctionality();
            try {
                a.startWithPurgeAlerts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                GetResponse g = a.getRequestFromMarketAlertUm();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            sut = new ApiFunctionality();
        }
    }

    public boolean uploadCorrectAlertGuard() {
        return getState().equals(ApiFunctionalityStates.API_STARTED) || getState().equals(ApiFunctionalityStates.UPLOADED) || getState().equals(ApiFunctionalityStates.PURGED);
    }
    public @Action void uploadCorrectAlert() throws IOException {
        sut.uploadCorrectAlert();

        // pause
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (numberOfAlerts < 5){
            numberOfAlerts ++;
            uploaded = true;
            purged = false;
            alertType = true;
            heading = true;
            description = true;
            url = true;
            imageUrl = true;
            postedBy = true;
            priceInCents = true;
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

        modelState = ApiFunctionalityStates.UPLOADED;

        Assert.assertEquals("The model's uploaded state doesn't match the SUT's state.", uploaded, sut.isUploaded());
        Assert.assertEquals("The model's number of alerts doesn't match the SUT's number of alerts.", numberOfAlerts, sut.getNumberOfAlerts());
        Assert.assertEquals("The model's purged state doesn't match the SUT's state.", purged, sut.isPurged());
        Assert.assertEquals("The model's alert type state doesn't match the SUT's state.", alertType, sut.containsAlertType());
        Assert.assertEquals("The model's heading state doesn't match the SUT's state.", heading, sut.containsHeading());
        Assert.assertEquals("The model's heading state doesn't match the SUT's state.", description, sut.containsDescription());
        Assert.assertEquals("The model's description state doesn't match the SUT's state.", alertType, sut.containsDescription());
        Assert.assertEquals("The model's url state doesn't match the SUT's state.", url, sut.containsUrl());
        Assert.assertEquals("The model's image url state doesn't match the SUT's state.", imageUrl, sut.containsImageUrl());
        Assert.assertEquals("The model's posted by state doesn't match the SUT's state.", postedBy, sut.containsPostedBy());
        Assert.assertEquals("The model's price in cents state doesn't match the SUT's state.", priceInCents, sut.containsPriceInCents());
    }

    public boolean purgeAlertsGuard() {
        return getState().equals(ApiFunctionalityStates.API_STARTED) || getState().equals(ApiFunctionalityStates.UPLOADED) || getState().equals(ApiFunctionalityStates.PURGED);
    }
    public @Action void purgeAlerts() throws IOException {
        //update the SUT
        sut.purgeAlerts();

        //update the model
        uploaded = false;
        purged = true;
        numberOfAlerts = 0;

        modelState = ApiFunctionalityStates.PURGED;

        //check correspondance
        Assert.assertEquals("The model's uploaded state doesn't match the SUT's state.", uploaded, sut.isUploaded());
        Assert.assertEquals("The model's number of alerts doesn't match the SUT's number of alerts.", numberOfAlerts, sut.getNumberOfAlerts());
        Assert.assertEquals("The model's purged state doesn't match the SUT's state.", purged, sut.isPurged());
    }

    @Test
    public void ApiFunctionalityModelTestRunner() throws IOException {
        final Tester tester = new RandomTester(new ApiFunctionalityModelTest());
        tester.setRandom(new Random());
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(250);
        tester.printCoverage();
    }
}
