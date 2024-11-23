package com.example.projetjee.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtil {
    public static boolean areDatesValid(String startDateString, String endDateString) {
        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateString);
            LocalDateTime endDate = LocalDateTime.parse(endDateString);

            return startDate.isBefore(endDate);
        } catch (DateTimeParseException e) {
            System.out.println("Erreur de format de date : " + e.getMessage());
            return false;
        }
    }

    public static Timestamp dateStringToTimestamp(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);

        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        return timestamp;
    }

    public static String dateFormat(String dateString) {
        if (dateString != null) {
            return dateString.replace("T", " ");
        }
        return null;
    }
}
