package common.help;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyTimestamp {
    public static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

    public static LocalDateTime parse(String timestamp) {
        try {
            return LocalDateTime.parse(timestamp, TIMESTAMP_FORMAT);
        } catch (Exception e) {
            return null;
        }
    }
}
