package com.example.projetjee.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

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

    public static String dateFormat(String dateString) {
        if (dateString != null) {
            return dateString.replace("T", " ");
        }
        return null;
    }
}
