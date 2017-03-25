package com.ashwinchat.singaporesurvivalguide.async;

import com.ashwinchat.singaporesurvivalguide.database.daos.WeatherDao;
import com.ashwinchat.singaporesurvivalguide.mockapp.ClientApp;

import junit.framework.Assert;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "app/src/main/AndroidManifest.xml", packageName = "com.ashwinchat.singaporesurvivalguide", application = ClientApp.class)
public class TestWeatherAsync {
    private static final String ENV_KEY_API_KEY = "OPEN_WEATHER_MAP_API";

    @Test
    public void testAsync() throws InterruptedException {
        // Delete whole db
        WeatherDao.deleteAll(WeatherDao.class);

        final String apiKey = System.getenv(ENV_KEY_API_KEY);
        WeatherAsyncTask task = new WeatherAsyncTask(apiKey);
        task.execute();
        Thread.sleep(4000);
        List<WeatherDao> query = WeatherDao.listAll(WeatherDao.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(query));
        Assert.assertEquals(7, query.size());
    }


}
