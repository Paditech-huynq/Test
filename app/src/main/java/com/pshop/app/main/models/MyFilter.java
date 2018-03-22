package com.pshop.app.main.models;


public class MyFilter {
    public static final int NO_BUTTON = 0;
    public static final int BUTTON_ALL = 1;
    public static final int BUTTON_THIS_MONTH = 2;
    public static final int BUTTON_THIS_WEEK = 3;
    public static final int BUTTON_LAST_WEEK = 4;
    private int buttonClicked;
    private String from;
    private String to;

    public int getButtonClicked() {
        return buttonClicked;
    }

    public void setButtonClicked(int buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
