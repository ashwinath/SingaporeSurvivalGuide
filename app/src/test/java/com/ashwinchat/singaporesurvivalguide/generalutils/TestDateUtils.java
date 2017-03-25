package com.ashwinchat.singaporesurvivalguide.generalutils;

import junit.framework.Assert;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import GeneralUtils.DateUtils;

public class TestDateUtils {
    private static long TIMESTAMP = 1490412450l;

    @Test
    public void testConvertFromUnixTimeStamp() {
        LocalDateTime date = DateUtils.convertFromUnixTimeStampToDate(TIMESTAMP);
        LocalDateTime sameDate = new LocalDateTime(2017, 3, 25, 11, 27, 30);
        Assert.assertEquals(sameDate, date);
    }

    @Test
    public void testConvertFromLocalDateTime() {
        LocalDateTime date = new LocalDateTime(2017, 3, 25, 11, 27, 30);
        long convertedTimeStamp = DateUtils.convertFromDateToUnixTimeStamp(date);
        Assert.assertEquals(TIMESTAMP, convertedTimeStamp);
    }
}
