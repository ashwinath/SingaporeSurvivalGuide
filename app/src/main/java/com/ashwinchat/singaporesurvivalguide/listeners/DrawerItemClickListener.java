package com.ashwinchat.singaporesurvivalguide.listeners;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ashwinchat.singaporesurvivalguide.R;
import com.ashwinchat.singaporesurvivalguide.fragments.MainFragment;
import com.ashwinchat.singaporesurvivalguide.fragments.WeatherFragment;

public class DrawerItemClickListener implements AdapterView.OnItemClickListener {

    private AppCompatActivity activity;
    private ListView listView;
    private String[] navBarTitles;
    private DrawerLayout drawerLayout;

    private static final int NAV_POSITION_MAIN = 0;
    private static final int NAV_POSITION_WEATHER = 1;

    public DrawerItemClickListener(AppCompatActivity activity, ListView listView, String[] navBarTitles, DrawerLayout drawerLayout) {
        this.activity = activity;
        this.listView = listView;
        this.navBarTitles = navBarTitles;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selectItem(position);
    }

    private void selectItem(int position) {
        // Create new fragment and specify which planet to show based on position
        Fragment fragment = null;
        switch (position) {
            case NAV_POSITION_MAIN:
                fragment = new MainFragment();
                break;
            case NAV_POSITION_WEATHER:
                fragment = new WeatherFragment();
                break;
            default:
                fragment = new MainFragment();
        }

        // Insert fragment by replacing any existing fragment
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();


        // Highlight the selected item, update the title and close the drawer
        listView.setItemChecked(position, true);
        activity.setTitle(navBarTitles[position]);
        drawerLayout.closeDrawer(listView);
    }

}
