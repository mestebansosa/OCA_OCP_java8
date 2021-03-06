package java8.OCA.C4_Java_API_Arrays;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class Time_OCA_OCP_C5 {
    public static void main(String[] args) {
    	System.out.println(-1.0/0); // -Infinity
    	
    	// The old - new way:
    	/* 
    	 Date      -  LocalDate
    	 Calendar  -  LocalDate
    	 */
    	// The date and time classes are Immutable and hence safe to be used in a multithreaded env.
    	// Constructor is private, so you must use one of the factory methods.
    	// Only ZoneDateTime contains time zone.
    	
    	// LocalDate d = new LocalDate(); // DOES NOT COMPILE
    	// LocalDate.of(2015, Month.JANUARY, 32); // throws DateTimeException
    	
    	// LocalDate. YYYY-MM-DD. 
    	System.out.println("-- LocalDate. YYYY-MM-DD");
    	LocalDate date1 = LocalDate.of(2018, 12, 10);
    	LocalDate date2 = LocalDate.of(2018, Month.DECEMBER, 10);
    	LocalDate date3 = LocalDate.now();
    	LocalDate date4 = LocalDate.parse("2018-12-10");
    	// LocalDate date5 = LocalDate.parse("2018-182-10"); // DateTimeParseException format 9999-99-99
    	
    	System.out.println(date1.getDayOfMonth());
    	System.out.println(date2.getMonthValue());	
    	System.out.println(date3.getYear());
    	System.out.println(date3.minusYears(1).getYear());
    	System.out.println(date3.plusYears(10).minusYears(1).getYear()); // chaining
    	System.out.println(date4);
    	System.out.println(date1.equals(date2));
    	assert date1.equals(date2);
    	assert date3.isAfter(date1);
    	
    	// converting to another type
    	System.out.println(date1.atTime(16, 30)); // LocalDateTime
    	System.out.println(date1.toEpochDay()); // long
    	
    	// LocalTime. HH:MM:SS:SSSS
    	System.out.println("-- LocalTime. HH:MM:SS:SSSS");
    	LocalTime time1 = LocalTime.of(12, 12);
    	LocalTime time2 = LocalTime.of(0, 12, 6);
    	LocalTime time3 = LocalTime.of(14, 7, 10, 998564632); // nanoseconds
    	LocalTime time4 = LocalTime.now();
    	LocalTime time5 = LocalTime.parse("15:08:23");
    	System.out.println(time1);
    	System.out.println(time2);
    	System.out.println(time3);
    	System.out.println(time4);
    	System.out.println(time5);
    	
    	System.out.format("max: %s %s %s\n",LocalTime.MAX, LocalTime.MIN, LocalTime.NOON);
    	System.out.format("%s %s\n", time5.getHour(), time5.getNano());

    	// converting to another type
    	System.out.println(time5.atDate(date3));
    	// System.out.println(time5.toEpochDay()); // LocalTime has not epoch method

    	// LocalDateTime. YYYY-MM-DDTHH:MM:SS:SSSS.
    	// Java uses T to separate the date and time when converting LocalDateTime to a String.
    	// Both functionalities (without the time zone)
    	System.out.println("-- LocalDateTime. YYYY-MM-DDTHH:MM:SS:SSSS");
    	LocalDateTime dateTime = LocalDateTime.parse("2050-06-05T15:08:23");
    	LocalDateTime dateTime2 = LocalDateTime.now();
    	System.out.println(dateTime);
    	System.out.println(dateTime2);
    	// converting to another type
    	System.out.println(dateTime2.toEpochSecond(ZoneOffset.UTC));
   	
    	// Period. 
    	// PnYnMnD or PnW. It is a day or more of time. P is mandatory 
    	System.out.println("-- Period PnYnMnD or PnW");
    	Period period1 = Period.of(1, 2, 7);
    	Period period2 = Period.ofYears(2);
    	System.out.println(dateTime + "---" + dateTime.plus(period2)); // Easier use for other types
    	System.out.println("period1:" + period1);
    	System.out.println("period2:" + period2);
    	System.out.println(Period.ofDays(-15));
    	System.out.println(Period.ofDays(35));
    	System.out.println(Period.parse("-P-5Y"));
    	System.out.println(Period.parse("-P-5Y-5D").equals(Period.parse("P5Y5D")));
    	System.out.println(Period.parse("P5Y5D"));
    	
    	System.out.println(date4.plus(Period.ofDays(1)));
    	
    	Period days5 = Period.of(0, 13, 5);
    	System.out.println(days5);
    	System.out.println(days5.isZero());
    	days5 = Period.of(0, 0, 0);
    	System.out.println(days5.isZero());
    	System.out.println(days5.isNegative());

    	System.out.println(Period.of(10, 5, 40).toTotalMonths());
    	
    	// Duration, which is intended for smaller units of time. 
    	// For Duration, you can specify the number of days, hours, minutes, seconds, or nanoseconds.
    	// PTnHnMnS. PT (period of time) is mandatory.
    	// Period and Duration are not equivalent.
    	// LocalDate use Period but Not duration. LocalTime use Duration but not Period.
    	System.out.println("-- Duration PTnHnMnS");
    	Duration.ofSeconds(3600); // to mean one hour.
    	// Use TemporalUnit interface. ChronoUnit is one implementation.
    	Duration daily = Duration.of(1, ChronoUnit.DAYS);
    	Duration halfDay = Duration.of(1, ChronoUnit.HALF_DAYS);
    	Duration hourly = Duration.of(1, ChronoUnit.HOURS);
    	Duration everyMinute = Duration.of(1, ChronoUnit.MINUTES);
    	Duration everyTenSeconds = Duration.of(10, ChronoUnit.SECONDS);
    	Duration everyMilli = Duration.of(1, ChronoUnit.MILLIS);
    	Duration everyNano = Duration.of(1, ChronoUnit.NANOS);
		System.out.println(daily + " " + halfDay + " " + hourly + " " + everyMinute + " " + everyTenSeconds + " "
				+ everyMilli + " " + everyNano);
    	
    	LocalTime one = LocalTime.of(5, 15);
    	LocalTime two = LocalTime.of(6, 30);
    	LocalDate date = LocalDate.of(2016, 1, 20);
    	System.out.println(ChronoUnit.HOURS.between(one, two)); // 1
    	System.out.println(ChronoUnit.MINUTES.between(one, two)); // 75
    	// System.out.println(ChronoUnit.MINUTES.between(one, date)); // DateTimeException
    	
    	Duration duration = Duration.ofHours(6);
    	System.out.println(dateTime.plus(duration));
    	
    	// Instant. 
    	// Represents a specific moment in time in the GMT time zone.
    	System.out.println("-- Instant");
    	Instant now = Instant.now();
    	System.out.println(now); 
    	Instant later = Instant.now();
    	System.out.println(later); 
    	System.out.println(Duration.between(now, later).toNanos());

    	// With ZoneDateTime you can turn into an Instant
    	System.out.println("-- ZoneDateTime");
    	LocalDate date0 = LocalDate.of(2015, 5, 25);
    	LocalTime time0 = LocalTime.of(11, 55, 00);
    	ZoneId zone0 = ZoneId.of("US/Eastern");
    	ZonedDateTime zonedDateTime = ZonedDateTime.of(date0, time0, zone0);
    	Instant instant = zonedDateTime.toInstant(); // 2015–05–25T15:55:00Z
    	System.out.println(zonedDateTime); // 2015–05–25T11:55–04:00[US/Eastern]
    	System.out.println(instant); // 2015–05–25T15:55:00Z
    	// other way to create an instance
    	instant = Instant.ofEpochSecond(instant.getEpochSecond());
    	
    	// DateTimeFormatter
    	// DateTimeFormatter can be used to format any type of date and/or time object.
    	// By calling a static ofLocalizedXXX method
    	System.out.println("-- DateTimeFormatter");
    	DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    	DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
    	DateTimeFormatter formatter3 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
    	// By access public static fields of DateTimeFormatter
    	DateTimeFormatter formatter4 = DateTimeFormatter.ISO_DATE;
    	// By using the static method of Pattern
    	DateTimeFormatter formatter5 = DateTimeFormatter.ofPattern("2018 12 12");
    	
    	System.out.println(FormatStyle.SHORT + " " + formatter1.format(dateTime));
    	System.out.println(FormatStyle.MEDIUM + " " + formatter2.format(dateTime));
    	System.out.println(formatter3.format(dateTime));
    	System.out.println(formatter4.format(dateTime));
    	System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));   
    	System.out.println(dateTime.format(formatter3));   
    	// create your own. Note: M:1, MM:01, MMM:Jan, MMMM:January
    	DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm");
    	System.out.println(dateTime.format(f)); // January 20, 2020, 11:12
    	
    	// ZoneDateTime. Contains a date, time and time zone.
    	// Oracle recommends avoiding time zones unless you really need them.
    	System.out.println("-- ZoneDateTime");
    	System.out.println(ZonedDateTime.now());
    	System.out.println(ZoneId.systemDefault());
		ZoneId.getAvailableZoneIds().stream().filter(z -> z.contains("US") && z.contains("Pacific")).sorted()
				.forEach(System.out::println);
		ZoneId.getAvailableZoneIds().stream().filter(z -> z.contains("Madrid")).forEach(System.out::println);
    	// The time zone offset can be listed in: +02:00, GMT+2, and UTC+2 all mean the same thing.
    	ZoneId zone = ZoneId.of("US/Eastern");
    	ZonedDateTime zoned1 = ZonedDateTime.of(2015, 1, 20, 6, 15, 30, 200, zone);
    	ZonedDateTime zoned2 = ZonedDateTime.of(date1, time1, zone);
    	ZonedDateTime zoned3 = ZonedDateTime.of(dateTime2, zone);
    	
    	// Accounting for Daylight Savings Time
		// The exam will let you know if a date/time mentioned falls on a weekend when
		//   the clocks are scheduled to be changed.
    	// March changeover:    1:00am-1:59am - 3:00am-4:00am
    	// November changeover: 1:00am-1:59am - 1:00am-1:59am - 2:00am-3:00am
    	LocalDate dateMarch = LocalDate.of(2016, Month.MARCH, 13);
    	LocalTime timeMarch = LocalTime.of(1, 30);
    	ZoneId zoneMarch = ZoneId.of("US/Eastern");
    	ZonedDateTime dateTimeMarch = ZonedDateTime.of(dateMarch, timeMarch, zoneMarch);
    	System.out.println(dateTimeMarch); // 2016–03–13T01:30–05:00[US/Eastern]
    	dateTimeMarch = dateTimeMarch.plusHours(1);
    	System.out.println(dateTimeMarch); // 2016–03–13T03:30–04:00[US/Eastern]
    	
    	LocalDate dateNov = LocalDate.of(2016, Month.NOVEMBER, 6);
    	LocalTime timeNov = LocalTime.of(1, 30);
    	ZoneId zoneNov = ZoneId.of("US/Eastern");
    	ZonedDateTime dateTimeNov = ZonedDateTime.of(dateNov, timeNov, zoneNov);
    	System.out.println(dateTimeNov); // 2016–11–06T01:30–04:00[US/Eastern]
    	dateTimeNov = dateTimeNov.plusHours(1);
    	System.out.println(dateTimeNov); // 2016–11–06T01:30–05:00[US/Eastern]
    	dateTimeNov = dateTimeNov.plusHours(1);
    	System.out.println(dateTimeNov); // 2016–11–06T02:30–05:00[US/Eastern]
    	
    	String response = 4 <= 5 ? "yes": "no";
    	
    }			
}
