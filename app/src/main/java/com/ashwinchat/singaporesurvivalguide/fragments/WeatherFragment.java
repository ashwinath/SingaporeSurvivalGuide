package com.ashwinchat.singaporesurvivalguide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashwinchat.singaporesurvivalguide.R;

public class WeatherFragment extends Fragment {
    public WeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        this.getActivity().setTitle(R.string.weather_title);
        return rootView;
    }

}
