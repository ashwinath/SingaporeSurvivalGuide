package com.ashwinchat.singaporesurvivalguide.listeners;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ashwinchat.singaporesurvivalguide.R;
import com.ashwinchat.singaporesurvivalguide.fragments.PlanetFragment;

public class DrawerItemClickListener implements AdapterView.OnItemClickListener {

    private Activity activity;
    private ListView listView;
    private String[] planetTitles;
    private DrawerLayout drawerLayout;

    public DrawerItemClickListener(Activity activity, ListView listView, String[] planetTitles, DrawerLayout drawerLayout) {
        this.activity = activity;
        this.listView = listView;
        this.planetTitles = planetTitles;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selectItem(position);
    }

    private void selectItem(int position) {
        // Create new fragment and specify which planet to show based on position
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        // Insert fragment by replacing any existing fragment
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();


        // Highlight the selected item, update the title and close the drawer
        listView.setItemChecked(position, true);
        activity.setTitle(planetTitles[position]);
        drawerLayout.closeDrawer(listView);
    }

}
