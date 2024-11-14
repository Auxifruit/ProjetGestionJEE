package com.example.projetjee.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date parsedDate = dateFormat.parse(dateString);
            return new Timestamp(parsedDate.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateFormat(String dateString) {
        if (dateString != null) {
            return dateString.replace("T", " ");
        }
        return null;
    }
}
