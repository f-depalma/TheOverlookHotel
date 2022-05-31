package com.toh.database.core.field;

import com.toh.database.core.Exceptions.DateFormatException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Date {
    private int day;
    private int month;
    private int year;


    public Date() {
    }

    public Date(int day, int month, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(String date) throws DateFormatException {
        set(date);
    }

    public void set(String date) throws DateFormatException {
        try {
            String[] splitted = date.split("/");
            day = Integer.parseInt(splitted[0]);
            month = Integer.parseInt(splitted[1]);

            if (splitted[2].length() < 4) {
                throw new DateFormatException();
            }
            year = Integer.parseInt(splitted[2]);
        } catch (NumberFormatException | DateFormatException e) {
            throw new DateFormatException();
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        if (isValid()) {
            return day + "/" + month + "/" + year;
        }
        return null;
    }

    public boolean isValid() {
        return day != 0 && month != 0 && year != 0 && year / 1000 > 1;
    }

    public boolean isAfterThen(Date date) {
        if (year == date.year) {
            if (month == date.month) {
                if (day == date.day) {
                    return false;
                } else {
                    return day > date.day;
                }
            } else {
                return month > date.month;
            }
        } else {
            return year > date.year;
        }
    }

    public boolean equals(Date date) {
        return date.day == day && date.month == month && date.year == year;
    }

    public boolean isBetween(Date date1, Date date2) {
        return equals(date1) || equals(date2) || isAfterThen(date1) != isAfterThen(date2);
    }

    public boolean isToday() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date today = new java.util.Date();
        boolean ret = false;
        try {
            ret = equals(new Date(dateFormat.format(today)));
        } catch (DateFormatException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
