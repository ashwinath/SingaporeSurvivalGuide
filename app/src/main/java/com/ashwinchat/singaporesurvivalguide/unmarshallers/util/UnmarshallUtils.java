package com.ashwinchat.singaporesurvivalguide.unmarshallers.util;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;

public class UnmarshallUtils<T> {

    public static<T> T convertJsonToObject(String jsonString, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, (Type) clazz);
    }

    public static String callOpenWeatherMapApi(String apiKey) throws IOException {
        String weatherUri = buildWeatherUri(apiKey);
        InputStream inStream = new URL(weatherUri).openStream();
        String jsonString = null;
        try {
            jsonString = IOUtils.toString(inStream);
        } finally {
            IOUtils.closeQuietly(inStream);
        }
        return jsonString;
    }

    // Chose apache utils over android URI builder since there's some problem with unit testing and I love my unit tests.
    public static String buildWeatherUri(String apiKey) {
        URIBuilder builder = new URIBuilder()
                .setScheme("http")
                .setHost("api.openweathermap.org")
                .setPath("/data/2.5/forecast/daily")
                .setParameter("q", "singapore")
                .setParameter("apikey", apiKey);
        return builder.toString();
    }

}
