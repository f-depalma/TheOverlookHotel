package com.toh.database.entity;

public class Date {
    private int day;
    private int month;
    private int year;

    public void set(String date) {
        String[] splitted = date.split("/");
        day = Integer.parseInt(splitted[0]);
        month = Integer.parseInt(splitted[1]);
        year = Integer.parseInt(splitted[2]);
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
}
