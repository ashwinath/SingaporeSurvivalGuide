package com.ashwinchat.singaporesurvivalguide.activities;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ashwinchat.singaporesurvivalguide.R;
import com.ashwinchat.singaporesurvivalguide.listeners.DrawerItemClickListener;
import com.ashwinchat.singaporesurvivalguide.listeners.DrawerToggleListener;

public class WelcomePageActivity extends AppCompatActivity {
    private String[] planetTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence title;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planetTitles = this.getResources().getStringArray(R.array.planets_array);
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawerList = (ListView) this.findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, planetTitles));
        // set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener(this, drawerList, planetTitles, drawerLayout));

        title = this.getTitle();
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawerToggle = new DrawerToggleListener(this, drawerLayout, R.string.drawer_open, R.string.drawer_close, title);

        // set drawer toggle as the drawer listener
        drawerLayout.addDrawerListener(drawerToggle);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

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
}
