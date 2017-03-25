package GeneralUtils;

import org.joda.time.LocalDateTime;

public class DateUtils {
    public static LocalDateTime convertFromUnixTimeStampToDate(long unixTimeStamp) {
        return new LocalDateTime(unixTimeStamp * 1000);
    }

    public static long convertFromDateToUnixTimeStamp(LocalDateTime date) {
        return date.toDate().getTime() / 1000;
    }
}
