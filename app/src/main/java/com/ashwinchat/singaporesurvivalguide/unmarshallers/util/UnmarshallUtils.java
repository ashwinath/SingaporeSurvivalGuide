package com.ashwinchat.singaporesurvivalguide.unmarshallers.util;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;

import static android.net.Uri.*;

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
        Builder builder = new Builder();
        builder.scheme("http")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("forecast")
                .appendPath("daily")
                .appendQueryParameter("q", "singapore")
                .appendQueryParameter("cnt", "14")
                .appendQueryParameter("apikey", apiKey);
        return builder.build().toString();

    }

}
