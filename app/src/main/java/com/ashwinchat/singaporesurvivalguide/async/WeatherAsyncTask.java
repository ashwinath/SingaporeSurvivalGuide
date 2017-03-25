package com.ashwinchat.singaporesurvivalguide.async;

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

public class WeatherAsyncTask extends AsyncTask<String, Void, Void> {

    private String apiKey;
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public WeatherAsyncTask(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            // 1. Download json string
            LOGGER.log(Level.INFO, "Downloading from OpenWeatherMap...");
            String weatherJsonString = UnmarshallUtils.callOpenWeatherMapApi(apiKey);
            // 2. Unmarshal
            LOGGER.log(Level.INFO, "Unmarshalling object...");
            OpenWeatherMap weatherObject = UnmarshallUtils.convertJsonToObject(weatherJsonString, OpenWeatherMap.class);
            // 3. Persist into db
            LOGGER.log(Level.INFO, "Persisting into database...");
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
                weatherDao.setHumidity(eachDayWeather.getHumidity());
                if (eachDayWeather.getTemp() != null) {
                    weatherDao.setMaxTemp(eachDayWeather.getTemp().getMax());
                    weatherDao.setMinTemp(eachDayWeather.getTemp().getMin());
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
