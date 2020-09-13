package com.example.chatappfirebase;

public class SetDates {
    private String DAY_OF_MONTH,MONTH,YEAR;

    public SetDates(String DAY_OF_MONTH, String MONTH, String YEAR) {
        this.DAY_OF_MONTH = DAY_OF_MONTH;
        this.MONTH = MONTH;
        this.YEAR = YEAR;
    }

    public String getDAY_OF_MONTH() {
        return DAY_OF_MONTH;
    }

    public String getMONTH() {
        return MONTH;
    }

    public String getYEAR() {
        return YEAR;
    }
}
