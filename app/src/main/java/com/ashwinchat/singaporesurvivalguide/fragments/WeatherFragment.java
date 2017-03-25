package com.ashwinchat.singaporesurvivalguide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ashwinchat.singaporesurvivalguide.R;
import com.ashwinchat.singaporesurvivalguide.adapters.WeatherListAdapter;
import com.ashwinchat.singaporesurvivalguide.database.daos.WeatherDao;
import com.orm.query.Select;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import GeneralUtils.GeneralUtils;

public class WeatherFragment extends Fragment {

    private ListView weatherListView;
    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    public WeatherFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        this.getActivity().setTitle(R.string.weather_title);
        this.weatherListView = (ListView) rootView.findViewById(R.id.listview_forecast);
        this.populateView(this.queryWeatherRecords(), weatherListView);

        return rootView;
    }


    private ArrayList<WeatherDao> queryWeatherRecords() {
        LocalDateTime now = new LocalDateTime();
        LocalDateTime todayTruncated = new LocalDateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0);
        long todayUnix = GeneralUtils.convertFromDateToUnixTimeStamp(todayTruncated);
        List<WeatherDao> weatherObjectList = Select.from(WeatherDao.class)
                .orderBy("id")
                .list();
        LOGGER.log(Level.INFO, "" + weatherObjectList.size());
        return new ArrayList<>(weatherObjectList);
    }

    private void populateView(ArrayList<WeatherDao> weatherObjectList, ListView weatherListView) {
        WeatherListAdapter adapter = new WeatherListAdapter(this.getActivity(), weatherObjectList);
        weatherListView.setAdapter(adapter);
        LOGGER.log(Level.INFO, "Adapter set!");
    }

}
