package com.ashwinchat.singaporesurvivalguide.database.daos;

import com.orm.SugarRecord;

import org.joda.time.LocalDateTime;

import GeneralUtils.GeneralUtils;

public class WeatherDao extends SugarRecord {

    public WeatherDao() {}

    // this is stored in unix time stamp since sqlite has no functionality for date
    private long date;
    private int minTemp;
    private int maxTemp;
    private double pressure;
    private int humidity;
    private String weatherDesc;
    private int weatherId;
    private double windSpeed;
    private int windBearings;
    // this is stored in unix time stamp since sqlite has no functionality for date
    private long updOn;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setDate(LocalDateTime date) {
        this.date = GeneralUtils.convertFromDateToUnixTimeStamp(date);
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindBearings() {
        return windBearings;
    }

    public void setWindBearings(int windBearings) {
        this.windBearings = windBearings;
    }

    public long getUpdOn() {
        return updOn;
    }

    public void setUpdOn(long updOn) {
        this.updOn = updOn;
    }

    public void setUpdOn(LocalDateTime updOn) {
        this.updOn = GeneralUtils.convertFromDateToUnixTimeStamp(updOn);
    }
}
