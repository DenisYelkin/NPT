/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Yelkin
 */
public class DateFormatter {

    private static final String[] monthNames = {"января", "февраля", "марта", "апреля", "мая",
        "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};

    public static String getMonthName(int i) {
        if (i < 0 || i > 11) {
            throw new RuntimeException("Wrong Month");
        }
        return monthNames[i];
    }

    public static String getRichDate(Calendar date) {
        return date.get(Calendar.DAY_OF_MONTH) + " " + DateFormatter.getMonthName(date.get(Calendar.MONTH))
                + " " + date.get(Calendar.YEAR);
    }

    public static String getFormattedDate(Calendar date) {
        return date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1)
                + "-" + date.get(Calendar.YEAR);
    }

    public static Date setFormattedDate(String formattedDate) {
        if (formattedDate == null || formattedDate.isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(formattedDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
}
