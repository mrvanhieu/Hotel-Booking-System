package com.edu.mum.hbs.common;

public enum Status {
    AVAILABLE("Available"),
    CHECKED("Checked"),
    BOOKING("Booking");

    private String value;

    private Status(String status) {
        this.value = status;
    }

    public String getStatus() {
        return this.value;
    }
}
