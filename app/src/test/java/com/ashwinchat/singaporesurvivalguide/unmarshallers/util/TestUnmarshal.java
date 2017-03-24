package com.ashwinchat.singaporesurvivalguide.unmarshallers.util;

import com.ashwinchat.singaporesurvivalguide.unmarshallers.pojos.weather.OpenWeatherMap;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;

public class TestUnmarshal {

    private static final String ENV_KEY_API_KEY = "OPEN_WEATHER_MAP_API";

    @Test
    public void testBuildUri() {
        String apiKey = "DummyKey";
        String uri = UnmarshallUtils.buildWeatherUri(apiKey);
        final String expectedUri = "http://api.openweathermap.org/data/2.5/forecast/daily?q=singapore&apikey=" + apiKey;
        Assert.assertEquals(expectedUri, uri);
    }

    @Test
    public void testDownloadFromOpenWeatherMap() throws IOException {
        final String apiKey = System.getenv(ENV_KEY_API_KEY);
        String jsonString = UnmarshallUtils.callOpenWeatherMapApi(apiKey);
        Assert.assertTrue(StringUtils.isNotBlank(jsonString));
    }

    @Test
    public void testUnmarshallWeatherObject() throws IOException {
        final String apiKey = System.getenv(ENV_KEY_API_KEY);
        String jsonString = UnmarshallUtils.callOpenWeatherMapApi(apiKey);
        OpenWeatherMap weatherBean = UnmarshallUtils.convertJsonToObject(jsonString, OpenWeatherMap.class);
        Assert.assertNotNull(weatherBean);
        Assert.assertNotNull(weatherBean.getMessage());
        Assert.assertNotNull(weatherBean.getCity());
        Assert.assertNotNull(weatherBean.getCnt());
        Assert.assertNotNull(weatherBean.getCod());
        Assert.assertNotNull(weatherBean.getList());
    }
}
