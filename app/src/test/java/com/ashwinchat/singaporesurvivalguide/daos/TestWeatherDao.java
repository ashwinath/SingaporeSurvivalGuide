package com.ashwinchat.singaporesurvivalguide.daos;

import com.ashwinchat.singaporesurvivalguide.database.daos.WeatherDao;
import com.ashwinchat.singaporesurvivalguide.mockapp.ClientApp;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import GeneralUtils.DateUtils;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "app/src/main/AndroidManifest.xml", packageName = "com.ashwinchat.singaporesurvivalguide", application = ClientApp.class)
public class TestWeatherDao {
    private static final LocalDateTime TIME_NOW = new LocalDateTime();

    @Test
    public void testInsertDeleteDao() {
        // Delete existing records first
        this.deleteRecord(TIME_NOW);

        // Create new record
        WeatherDao viewObject = this.createRecordAndPersist();

        // query back and assert
        List<WeatherDao> queryResult = this.queryRecord(TIME_NOW);
        Assert.assertTrue(CollectionUtils.isNotEmpty(queryResult));

        // Delete test records
        this.deleteRecord(TIME_NOW);
    }

    private List<WeatherDao> queryRecord(LocalDateTime date) {
        return Select.from(WeatherDao.class)
                .where(Condition.prop("date").eq(DateUtils.convertFromDateToUnixTimeStamp(date)))
                .list();
    }

    private WeatherDao createRecordAndPersist() {
        WeatherDao weatherDao = new WeatherDao();
        weatherDao.setDate(TIME_NOW);
        weatherDao.setHumidity(123);
        weatherDao.setMaxTemp(123);
        weatherDao.setMinTemp(123);
        weatherDao.setPressure(123);
        weatherDao.setWeatherDesc("WeatherDesc");
        weatherDao.setWeatherId(123);
        weatherDao.setWindSpeed(123);
        weatherDao.setWindBearings(123);
        weatherDao.setUpdOn(TIME_NOW);
        weatherDao.save();
        return weatherDao;
    }

    private void deleteRecord(LocalDateTime date) {
        List<WeatherDao> queryResult = this.queryRecord(date);
        WeatherDao.deleteInTx(queryResult);
    }
}
