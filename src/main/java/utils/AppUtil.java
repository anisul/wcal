package utils;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class AppUtil {
    public static final double HOURLY_WAGE_RATE = 3.75;
    public static final double EVENING_WAGE_RATE = 1.15;

    public static LocalDateTime convertStringToLocalDateTime(String input) {
        ZoneId zoneId = ZoneId.of("America/Montreal");
        Date output = new Date();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            output = formatter.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Instant.ofEpochMilli(output.getTime()).atZone(zoneId).toLocalDateTime();
    }

    public static LocalDateTime convertStringToLocalDateTime(LocalDateTime date, String time, boolean isNextDay) {
        String[] splits = time.split(":");

        if (isNextDay) {
            date.plusDays(1);
        }

        LocalDateTime t1 = date.plusHours(Long.parseLong(splits[0]));
        LocalDateTime t2 = t1.plusMinutes(Long.parseLong(splits[1]));

        return t2;
    }

    public static BigDecimal calculateWage(LocalDateTime startTime, LocalDateTime endTime) {
        BigDecimal regularDailyWage;
        BigDecimal eveningWorkCompensation;
        BigDecimal overtimeCompensations;
        BigDecimal totalWage;

        double totalWorkHour = Duration.between(startTime, endTime).toMinutes() / 60d;

        regularDailyWage = BigDecimal.valueOf(totalWorkHour * HOURLY_WAGE_RATE);
        eveningWorkCompensation = eveningWageCalculation(startTime, endTime);
        overtimeCompensations = overtimeCompensationCalculation(totalWorkHour);

        totalWage = regularDailyWage.add(eveningWorkCompensation).add(overtimeCompensations).setScale(2, RoundingMode.CEILING) ;
        return totalWage;
    }

    public static BigDecimal eveningWageCalculation(LocalDateTime startTime, LocalDateTime endTime) {
        BigDecimal eveningWage;
        ZoneId zoneId = ZoneId.of("America/Montreal");
        Duration duration = eveningWorkIntervalCalculation(ZonedDateTime.of(startTime, zoneId), ZonedDateTime.of(endTime, zoneId));
        double totalHours = duration.toMinutes() / 60d;

        eveningWage = BigDecimal.valueOf(totalHours * EVENING_WAGE_RATE);
        return eveningWage;
    }

    public static BigDecimal overtimeCompensationCalculation(double totalWorkHour) {
        double totalOvertimeWage = 0;

        if (totalWorkHour - 8 > 0 &&  totalWorkHour - 8 <= 2) {
            double overtimeWorkHour = totalWorkHour - 8;
            totalOvertimeWage = overtimeWorkHour * HOURLY_WAGE_RATE * 0.25;

            double remainingOvertimeWorkHour = overtimeWorkHour - 2;

            if (remainingOvertimeWorkHour > 0 && remainingOvertimeWorkHour <= 2) {
                totalOvertimeWage += remainingOvertimeWorkHour * HOURLY_WAGE_RATE * 0.5;

                double finalOvertimeWorkHour = remainingOvertimeWorkHour - 2;

                if (finalOvertimeWorkHour > 0) {
                    totalOvertimeWage += remainingOvertimeWorkHour * HOURLY_WAGE_RATE * 1.0;
                }
            }
        }
        return BigDecimal.valueOf(totalOvertimeWage);
    }

    public static Duration eveningWorkIntervalCalculation(ZonedDateTime startTime, ZonedDateTime endTime) {
        LocalTime START = LocalTime.of(18, 0);
        LocalTime END = LocalTime.of(6, 0);

        ZonedDateTime singleIntervalStart = startTime.with(START);
        ZonedDateTime singleIntervalEnd = startTime.plusDays(1).with(END);
        if (startTime.isBefore(singleIntervalStart)) {
            // no overlap
            return Duration.ZERO;
        }
        ZonedDateTime overlapStart = startTime.isBefore(singleIntervalStart)
                ? singleIntervalStart : startTime;
        ZonedDateTime overlapEnd = startTime.isBefore(singleIntervalEnd)
                ? endTime : singleIntervalEnd;
        return Duration.between(overlapStart, overlapEnd);
    }

}
