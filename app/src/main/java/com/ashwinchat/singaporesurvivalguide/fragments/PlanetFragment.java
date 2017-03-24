package com.ashwinchat.singaporesurvivalguide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashwinchat.singaporesurvivalguide.R;

import java.util.Locale;

public class PlanetFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public PlanetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String planet = this.getResources().getStringArray(R.array.planets_array)[i];

        int imageId = this.getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()), "drawable", this.getActivity().getPackageName());

        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        this.getActivity().setTitle(planet);
        return rootView;

    }
}
