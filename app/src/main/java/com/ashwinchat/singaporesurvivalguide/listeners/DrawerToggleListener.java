package com.ashwinchat.singaporesurvivalguide.listeners;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by chat on 23/3/2017.
 */

public class DrawerToggleListener extends ActionBarDrawerToggle {
    private AppCompatActivity activity;

    public DrawerToggleListener(AppCompatActivity activity,
                                DrawerLayout drawerLayout,
                                @StringRes int openDrawerContentDescRes,
                                @StringRes int closeDrawerContentDescRes,
                                CharSequence title) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity = activity;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        activity.invalidateOptionsMenu();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        activity.invalidateOptionsMenu();
    }
}
