package com.ashwinchat.singaporesurvivalguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashwinchat.singaporesurvivalguide.R;
import com.ashwinchat.singaporesurvivalguide.database.daos.WeatherDao;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;

import GeneralUtils.GeneralUtils;

public class WeatherListAdapter extends ArrayAdapter<WeatherDao> {
    private static final int POSITION_TODAY = 0;
    private static final int POSITION_TOMORROW = 1;

    private TextView  dateTextView;
    private TextView  highTextView;
    private TextView  lowTextView;
    private ImageView iconImageView;
    private TextView  forecastTextView;

    public WeatherListAdapter(Context context, ArrayList<WeatherDao> weatherList) {
        super(context, 0, weatherList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherDao weatherObject = this.getItem(position);

        if (convertView == null) {
            if (position == POSITION_TODAY) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.weather_list_item_today, parent, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.weather_list_item, parent, false);
            }
        }
        this.getViews(convertView);
        this.setViews(weatherObject, position);

        return convertView;
    }

    private void getViews(View convertView) {
        this.dateTextView     = (TextView)  convertView.findViewById(R.id.list_item_date_textview);
        this.highTextView     = (TextView)  convertView.findViewById(R.id.list_item_high_textview);
        this.lowTextView      = (TextView)  convertView.findViewById(R.id.list_item_low_textview);
        this.iconImageView    = (ImageView) convertView.findViewById(R.id.list_item_icon);
        this.forecastTextView = (TextView)  convertView.findViewById(R.id.list_item_forecast_textview);
    }

    private void setViews(WeatherDao weatherObject, int position) {
        LocalDateTime dateTime = GeneralUtils.convertFromUnixTimeStampToDate(weatherObject.getDate());
        this.dateTextView.setText(formatDateTextView(dateTime, position));
        this.highTextView.setText(weatherObject.getMaxTemp() + "°");
        this.lowTextView.setText(weatherObject.getMinTemp() + "°");
        // TODO: fix these two hardcoded values
        this.iconImageView.setImageResource(R.drawable.art_clear);
        this.forecastTextView.setText("Clear");
    }

    private String formatDateTextView(LocalDateTime dateTime, int position) {
        String day = null;
        if (position == POSITION_TODAY) {
            day = "Today";
        } else if (position == POSITION_TOMORROW) {
            day = "Tomorrow";
        } else {
            day = GeneralUtils.getDayNameFromLocalDateTime(dateTime);
        }
        String date = GeneralUtils.getMonthAndDayString(dateTime);
        if (0 < position && position <= 6) {
            return day;
        } else {
            return GeneralUtils.formatDateString(day, date);
        }
    }


    @Override
    public int getViewTypeCount() {
        // to prevent reindexing while scrolling
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        // to prevent reindexing while scrolling
        return position;
    }
}
