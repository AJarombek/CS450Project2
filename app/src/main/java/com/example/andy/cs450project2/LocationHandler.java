package com.example.andy.cs450project2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Observable;

/**
 * Class which checks for updates in the location values
 * @author Andrew Jarombek
 * @since 9/14/2016
 */
public class LocationHandler extends Observable implements LocationListener {

    private LocationManager locationManager = null;

    public LocationHandler(Activity act) {
        locationManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);

        // Check to make sure we have the proper permissions
        if (ContextCompat.checkSelfPermission(act, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(act, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        } else {
            // Otherwise request the proper permissions
            ActivityCompat.requestPermissions(act, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION }, 0);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        setChanged();

        // Notify observing classes with the latitude and longitude values
        double[] loc = {location.getLatitude(), location.getLongitude()};
        notifyObservers(loc);
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
