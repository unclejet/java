package com.uj.study.datetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 * @author ：UncleJet
 * @date ：Created in 2021/1/5 上午11:49
 * @description：
 */
public class DateTimeExamples {
    private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MMM-yyyy");
        }
    };

    public static void main(String[] args) {
        useOldDate();
        useLocalDate();
        useTemporalAdjuster();
        useDateFormatter();
    }

    private static void useOldDate() {
        Date date = new Date(114, 2, 18);
        System.out.println(date);

        System.out.println(formatters.get().format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    private static void useLocalDate() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.getYear(); // 2014
        Month month = date.getMonth(); // MARCH
        int day = date.getDayOfMonth(); // 18
        DayOfWeek dow = date.getDayOfWeek(); // TUESDAY
        int len = date.lengthOfMonth(); // 31 (days in March)
        boolean leap = date.isLeapYear(); // false (not a leap year)
        System.out.println(date);

        int y = date.get(ChronoField.YEAR);
        int m = date.get(ChronoField.MONTH_OF_YEAR);
        int d = date.get(ChronoField.DAY_OF_MONTH);

        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
        int hour = time.getHour(); // 13
        int minute = time.getMinute(); // 45
        int second = time.getSecond(); // 20
        System.out.println(time);

        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println(dt1);

        LocalDate date1 = dt1.toLocalDate();
        System.out.println(date1);
        LocalTime time1 = dt1.toLocalTime();
        System.out.println(time1);

        //机器时间
        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        Instant now = Instant.now();

        //duration
        Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time);
        Duration d2 = Duration.between(instant, now);
        System.out.println(d1.getSeconds());
        System.out.println(d2.getSeconds());

        Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(threeMinutes);

        //period
        Period tenDays = Period.between(LocalDate.of(2014, 3, 8),
                LocalDate.of(2014, 3, 18));
        Period tenDays1 = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);



        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);


        //处理不同时区
        ZoneId romeZone = ZoneId.of("Europe/Rome");
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        LocalDate date11 = LocalDate.of(2014, Month.MARCH, 18);
        ZonedDateTime zdt1 = date11.atStartOfDay(romeZone);
        LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        ZonedDateTime zdt2 = dateTime.atZone(romeZone);
        Instant instant1 = Instant.now();
        ZonedDateTime zdt3 = instant1.atZone(romeZone);

        //通过ZoneId,你还可以将LocalDateTime转换为Instant:
        LocalDateTime dateTime11 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
//        Instant instantFromDateTime = dateTime11.toInstant(romeZone);

        //你也可以通过反向的方式得到LocalDateTime对象:
        Instant instant11 = Instant.now();
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant11, romeZone);

        //利用和 UTC/格林尼治时间的固定偏差计算时区
        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
        LocalDateTime dateTime1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(dateTime1, newYorkOffset);
    }

    private static void useTemporalAdjuster() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        date = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date);
        date = date.with(lastDayOfMonth());
        System.out.println(date);

        date = date.with(new NextWorkingDay());
        System.out.println(date);
        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(new NextWorkingDay());
        System.out.println(date);

        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
        System.out.println(date);
    }

    private static class NextWorkingDay implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }

    private static void useDateFormatter() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(date.format(formatter));
        System.out.println(date.format(italianFormatter));

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);

        System.out.println(date.format(complexFormatter));
    }
}
