package com.example.todoapp.models;

public enum Status {
    ACTIVE("active"),
    COMPLETED("completed"),
    CANCELED("canceled");

    public final String val;

    Status(String val) {
        this.val = val;
    }
}
