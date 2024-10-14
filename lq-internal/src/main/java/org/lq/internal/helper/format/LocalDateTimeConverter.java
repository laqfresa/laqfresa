package org.lq.internal.helper.format;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class LocalDateTimeConverter {
    public static final String INPUT_DATE_FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String INPUT_DATE_FORMAT_dd_MM_yyyy = "dd/MM/yyyy";
    public static final String INPUT_DATE_FORMAT_dd_MM_yy = "dd/MM/yy";
    public static final String INPUT_DATE_FORMAT_yyyy_MM_dd_HH_mm_ssZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String INPUT_DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss";

    public static LocalDateTime parseUTCToDate(String dateTime, String format) {
        dateTime = dateTime.replace("[UTC]", "");
        LocalDateTime localDateTime = parse(dateTime, format);
        localDateTime = localDateTime.minusHours(5);
        return localDateTime;
    }

    public static LocalDateTime parse(String dateTime, String format) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(format)
                .toFormatter(Locale.forLanguageTag("es-CO"));

        TemporalAccessor temporalAccessor = formatter.parse(dateTime);

        LocalDateTime localDateTime;

        if (temporalAccessor.isSupported(ChronoField.HOUR_OF_DAY)) {
            localDateTime = LocalDateTime.from(temporalAccessor);
        } else {
            LocalDate localDate = LocalDate.from(temporalAccessor);
            localDateTime = localDate.atStartOfDay();
        }

        return localDateTime;
    }

    public static LocalDate parseToDate(String date, String format) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(format)
                .toFormatter(Locale.forLanguageTag("es-CO"));

        TemporalAccessor temporalAccessor = formatter.parse(date);

        return LocalDate.from(temporalAccessor);
    }

    public static String parseToString(LocalDate date, String format) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(format)
                .toFormatter(Locale.forLanguageTag("es-CO"));

        return date.format(formatter);
    }
}
