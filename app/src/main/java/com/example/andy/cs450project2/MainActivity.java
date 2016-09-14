package com.example.andy.cs450project2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * The MainActivity for the Accelerometer/Location App
 * @author Andrew Jarombek
 * @since 9/14/2016
 */
public class MainActivity
        extends AppCompatActivity
        implements Observer {

    private static final String Y_VALUE = "yvalue";
    private static final String X_VALUE = "xvalue";
    private static final String Z_VALUE = "zvalue";
    private static final String LONGITUDE = "long";
    private static final String LATITUDE = "lat";

    // textviews
    private TextView accel_x_view = null;
    private TextView accel_y_view = null;
    private TextView accel_z_view = null;
    private TextView location_lat = null;
    private TextView location_lon = null;
    private Observable accel;
    private Observable loc;

    @Override
    public void update(Observable observable, Object o) {
        if (observable.equals(accel))
            updateAccelerometer(o);
        else if (observable.equals(loc))
            updateLocation(o);
    }

    /**
     * Helper method to update the accelerometer values
     * @param o the notified changes
     */
    public void updateAccelerometer(Object o) {
        float[] values = (float []) o;
        accel_x_view.setText(String.valueOf(values[0]));
        accel_y_view.setText(String.valueOf(values[1]));
        accel_z_view.setText(String.valueOf(values[2]));
    }

    /**
     * Helper method to update the location values
     * @param o the notified changes
     */
    public void updateLocation(Object o) {
        double[] values = (double[]) o;
        location_lat.setText(String.valueOf(values[0]));
        location_lon.setText(String.valueOf(values[1]));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accel_y_view = (TextView) findViewById(R.id.accel_y);
        accel_x_view = (TextView) findViewById(R.id.accel_x);
        accel_z_view = (TextView) findViewById(R.id.accel_z);

        location_lat = (TextView) findViewById(R.id.lat);
        location_lon = (TextView) findViewById(R.id.lon);

        // If there is a savedInstanceState, apply the saved accelerometer/location values
        if (savedInstanceState != null) {
            accel_x_view.setText(String.valueOf(savedInstanceState.getDouble(X_VALUE, 0.0)));
            accel_y_view.setText(String.valueOf(savedInstanceState.getDouble(Y_VALUE, 0.0)));
            accel_z_view.setText(String.valueOf(savedInstanceState.getDouble(Z_VALUE, 0.0)));
            location_lat.setText(String.valueOf(savedInstanceState.getDouble(LATITUDE, 0.0)));
            location_lon.setText(String.valueOf(savedInstanceState.getDouble(LONGITUDE, 0.0)));
        }

        // Add an accelerometer observer
        this.accel = new AccelerometerHandler(1000, this);
        this.accel.addObserver(this);

        // Add a location observer
        this.loc = new LocationHandler(this);
        this.loc.addObserver(this);
    }

    /**
     * Overridden method for saving the state of the app upon destruction
     * @param outState a Bundle of the saved values
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(X_VALUE, Double.parseDouble(accel_x_view.getText().toString()));
        outState.putDouble(Y_VALUE, Double.parseDouble(accel_y_view.getText().toString()));
        outState.putDouble(Z_VALUE, Double.parseDouble(accel_z_view.getText().toString()));
        outState.putDouble(LATITUDE, Double.parseDouble(location_lat.getText().toString()));
        outState.putDouble(LONGITUDE, Double.parseDouble(location_lon.getText().toString()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
