package com.ashwinchat.singaporesurvivalguide.async;

import android.location.Location;
import android.os.AsyncTask;

import com.ashwinchat.singaporesurvivalguide.database.daos.WeatherDao;
import com.ashwinchat.singaporesurvivalguide.unmarshallers.pojos.weather.List;
import com.ashwinchat.singaporesurvivalguide.unmarshallers.pojos.weather.OpenWeatherMap;
import com.ashwinchat.singaporesurvivalguide.unmarshallers.pojos.weather.Weather;
import com.ashwinchat.singaporesurvivalguide.unmarshallers.util.UnmarshallUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import GeneralUtils.GeneralUtils;

public class WeatherAsyncTask extends AsyncTask<String, Void, Void> {

    private String apiKey;
    private double lat;
    private double lon;
    private boolean useDefaultLocation;
    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    public WeatherAsyncTask(String apiKey, Double lat, Double lon) {
        this.apiKey = apiKey;
        if (lat != null || lon != null) {
            this.lat = lat;
            this.lon = lon;
            useDefaultLocation = false;
        } else {
            useDefaultLocation = true;
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            // 0. delete old data
            WeatherDao.deleteAll(WeatherDao.class);
            // 1. Download json string
            LOGGER.log(Level.INFO, "Downloading from OpenWeatherMap...");
            String weatherJsonString = null;
            if (useDefaultLocation) {
                weatherJsonString = UnmarshallUtils.callOpenWeatherMapApi(apiKey);
                LOGGER.log(Level.INFO, "Using default location");
            } else {
                weatherJsonString = UnmarshallUtils.callOpenWeatherMapApi(apiKey, this.lat, this.lon);
                LOGGER.log(Level.INFO, "Using user's location");
            }
            // 2. Unmarshal
            OpenWeatherMap weatherObject = UnmarshallUtils.convertJsonToObject(weatherJsonString, OpenWeatherMap.class);
            // 3. Persist into db
            this.persistWeatherData(weatherObject);
            LOGGER.log(Level.INFO, "Database Synchronised!");

        } catch (IOException e) {
            // unable to download
            // TODO: display exception
            LOGGER.log(Level.SEVERE, "WeatherAsyncTask caught an exception!");
        }
        return null;
    }

    private void persistWeatherData(OpenWeatherMap weatherObject) {
        java.util.List<WeatherDao> weatherDaoVector = new Vector<>();
        if (weatherObject != null) {
            // I'm sorry for the naming
            // Note that this List is not java.util.List but rather the list that it was unmarshalled into
            // OpenWeatherMap calls it "list"
            for (List eachDayWeather : weatherObject.getList()) {
                WeatherDao weatherDao = new WeatherDao();
                weatherDao.setDate(eachDayWeather.getDt());
                weatherDao.setHumidity(eachDayWeather.getHumidity());
                if (eachDayWeather.getTemp() != null) {
                    weatherDao.setMaxTemp(GeneralUtils.formatTemperature(eachDayWeather.getTemp().getMax()));
                    weatherDao.setMinTemp(GeneralUtils.formatTemperature(eachDayWeather.getTemp().getMin()));
                }
                weatherDao.setPressure(eachDayWeather.getPressure());
                if (CollectionUtils.isNotEmpty(eachDayWeather.getWeather())) {
                    Weather weather = eachDayWeather.getWeather().get(0);
                    weatherDao.setWeatherDesc(weather.getDescription());
                    weatherDao.setWeatherId(weather.getId());
                }
                weatherDao.setWindSpeed(eachDayWeather.getSpeed());
                weatherDao.setWindBearings(eachDayWeather.getDeg());
                weatherDao.setUpdOn(new LocalDateTime());
                weatherDaoVector.add(weatherDao);
            }
            WeatherDao.saveInTx(weatherDaoVector);
        }
    }
}
