package GeneralUtils;

import com.ashwinchat.singaporesurvivalguide.R;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;

public class GeneralUtils {

    public static final double KELVIN_DIFFERENCE = 273.15;
    public static final double ROUNDING_VALUE = 0.5;

    public static LocalDateTime convertFromUnixTimeStampToDate(long unixTimeStamp) {
        return new LocalDateTime(unixTimeStamp * 1000l);
    }

    public static long convertFromDateToUnixTimeStamp(LocalDateTime date) {
        return date.toDate().getTime() / 1000l;
    }

    public static String getDayNameFromLocalDateTime(LocalDateTime dateTime) {
        int dayOfWeek = dateTime.getDayOfWeek();
        String day = null;
        switch (dayOfWeek) {
            case DateTimeConstants.MONDAY:
                day = "Monday";
                break;
            case DateTimeConstants.TUESDAY:
                day = "Tuesday";
                break;
            case DateTimeConstants.WEDNESDAY:
                day = "Wednesday";
                break;
            case DateTimeConstants.THURSDAY:
                day = "Thursday";
                break;
            case DateTimeConstants.FRIDAY:
                day = "Friday";
                break;
            case DateTimeConstants.SATURDAY:
                day = "Saturday";
                break;
            case DateTimeConstants.SUNDAY:
                day = "Sunday";
                break;
        }
        return day;
    }

    public static String getMonthAndDayString(LocalDateTime dateTime) {
        int monthInt = dateTime.getMonthOfYear();
        String month = null;
        switch (monthInt) {
            case DateTimeConstants.JANUARY:
                month = "January";
                break;
            case DateTimeConstants.FEBRUARY:
                month = "February";
                break;
            case DateTimeConstants.MARCH:
                month = "March";
                break;
            case DateTimeConstants.APRIL:
                month = "April";
                break;
            case DateTimeConstants.MAY:
                month = "May";
                break;
            case DateTimeConstants.JUNE:
                month = "June";
                break;
            case DateTimeConstants.JULY:
                month = "July";
                break;
            case DateTimeConstants.AUGUST:
                month = "August";
                break;
            case DateTimeConstants.SEPTEMBER:
                month = "September";
                break;
            case DateTimeConstants.OCTOBER:
                month = "October";
                break;
            case DateTimeConstants.NOVEMBER:
                month = "November";
                break;
            case DateTimeConstants.DECEMBER:
                month = "December";
                break;
        }
        int dayInt = dateTime.getDayOfMonth();
        return month + " " + dayInt;
    }

    public static String formatDateString(String day, String date) {
        return day + ", " + date;
    }

    public static int formatTemperature(double kelvin) {
        return (int) (kelvin - KELVIN_DIFFERENCE + ROUNDING_VALUE);
    }

    public static int getArtResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
            return R.drawable.art_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.art_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        }
        return -1;
    }
}
