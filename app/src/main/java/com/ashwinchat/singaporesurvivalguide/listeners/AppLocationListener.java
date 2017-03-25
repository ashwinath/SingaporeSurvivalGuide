package com.ashwinchat.singaporesurvivalguide.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.ashwinchat.singaporesurvivalguide.activities.WelcomePageActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLocationListener implements LocationListener {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());
    private WelcomePageActivity activity;

    public AppLocationListener(WelcomePageActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
        activity.setLocation(location);
        LOGGER.log(Level.INFO, location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
