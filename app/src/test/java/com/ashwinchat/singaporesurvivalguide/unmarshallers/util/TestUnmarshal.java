package com.ashwinchat.singaporesurvivalguide.unmarshallers.util;

import com.ashwinchat.singaporesurvivalguide.unmarshallers.pojos.weather.OpenWeatherMap;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "app/src/main/AndroidManifest.xml")
public class TestUnmarshal {

    private static final String ENV_KEY_API_KEY = "OPEN_WEATHER_MAP_API";
    private static final double lat = 1.3521;
    private static final double lon = 103.8198;

    @Test
    public void testBuildUri() {
        String apiKey = "DummyKey";
        String uri = UnmarshallUtils.buildWeatherUri(apiKey, this.lat, this.lon);
        final String expectedUri = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=1.3521&lon=103.8198&cnt=14&apikey=" + apiKey;
        Assert.assertEquals(expectedUri, uri);
    }

    @Test
    public void testBuildUriOverLoaded() {
        String apiKey = "DummyKey";
        String uri = UnmarshallUtils.buildWeatherUri(apiKey);
        final String expectedUri = "http://api.openweathermap.org/data/2.5/forecast/daily?q=singapore&cnt=14&apikey=" + apiKey;
        Assert.assertEquals(expectedUri, uri);
    }

    @Test
    public void testDownloadFromOpenWeatherMap() {
        boolean testCompleted = false;
        try {
            final String apiKey = System.getenv(this.ENV_KEY_API_KEY);
            String jsonString = UnmarshallUtils.callOpenWeatherMapApi(apiKey, this.lat, this.lon);
            Assert.assertTrue(StringUtils.isNotBlank(jsonString));
            testCompleted = true;
        } catch (IOException e) {
            // do nothing there since I don't want the stack trace coming out.
            // instead we just fail the test here
            Assert.assertTrue(testCompleted);
        }
    }

    @Test
    public void testUnmarshallWeatherObject() {
        boolean testCompleted = false;
        try {
            final String apiKey = System.getenv(ENV_KEY_API_KEY);
            String jsonString = UnmarshallUtils.callOpenWeatherMapApi(apiKey, this.lat, this.lon);
            OpenWeatherMap weatherBean = UnmarshallUtils.convertJsonToObject(jsonString, OpenWeatherMap.class);
            Assert.assertNotNull(weatherBean);
            Assert.assertNotNull(weatherBean.getMessage());
            Assert.assertNotNull(weatherBean.getCity());
            Assert.assertNotNull(weatherBean.getCnt());
            Assert.assertNotNull(weatherBean.getCod());
            Assert.assertNotNull(weatherBean.getList());
            testCompleted = true;
        } catch (IOException e) {
            // do nothing there since I don't want the stack trace coming out.
            // instead we just fail the test here
            Assert.assertTrue(testCompleted);
        }
    }
}
