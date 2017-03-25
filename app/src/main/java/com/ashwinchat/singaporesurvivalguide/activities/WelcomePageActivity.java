package com.ashwinchat.singaporesurvivalguide.activities;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ashwinchat.singaporesurvivalguide.R;
import com.ashwinchat.singaporesurvivalguide.async.WeatherAsyncTask;
import com.ashwinchat.singaporesurvivalguide.fragments.MainFragment;
import com.ashwinchat.singaporesurvivalguide.listeners.AppLocationListener;
import com.ashwinchat.singaporesurvivalguide.listeners.DrawerItemClickListener;
import com.ashwinchat.singaporesurvivalguide.listeners.DrawerToggleListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WelcomePageActivity extends AppCompatActivity {
    private String[] navbarTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence title;
    private ActionBarDrawerToggle drawerToggle;
    private Location location;
    private Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // Load initial fragment
        this.initialiseMainFragment();

        navbarTitles = this.getResources().getStringArray(R.array.navBarTitles);
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawerList = (ListView) this.findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, navbarTitles));
        // set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener(this, drawerList, navbarTitles, drawerLayout));

        title = this.getTitle();
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawerToggle = new DrawerToggleListener(this, drawerLayout, R.string.drawer_open, R.string.drawer_close, title);

        // set drawer toggle as the drawer listener
        drawerLayout.addDrawerListener(drawerToggle);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void initialiseMainFragment() {
        Fragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = this.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, mainFragment)
                .commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(this.title);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        this.startWeatherAsyncTask();
    }

    private void startLocationListener() {
        try {
            int minDuration = 30 * 60 * 1000 ; // 30 minutes
            int minDistance = 100; // 100m
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            AppLocationListener locationListener = new AppLocationListener(this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minDuration, minDistance, locationListener);
        } catch (SecurityException e) {
            showToastMessageWhenNotAllowedPermission();
        }
    }

    private void showToastMessageWhenNotAllowedPermission() {
        LOGGER.log(Level.INFO, "User did not grant permission.");
        CharSequence toastMessage = "Please enable location, or the default location will be \"Singapore\".";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, toastMessage, duration);
        toast.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            // if request is cancelled, grantResultArray will be empty
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                // permission denied
                LOGGER.log(Level.INFO, "Permission to access GPS was denied.");
                showToastMessageWhenNotAllowedPermission();
            } else {
                // granted
                this.startLocationListener();
            }
        }
    }

    private void getUserPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }

    }

    private void startWeatherAsyncTask() {
        String apiKey = this.getString(R.string.open_weather_map_api_key);
        WeatherAsyncTask task = new WeatherAsyncTask(apiKey);
        task.execute();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
