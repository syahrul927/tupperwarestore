package com.example.tupperwarestore.retrofit;

public class UnauthorizedEvent {

    private static final UnauthorizedEvent INSTANCE = new UnauthorizedEvent();

    public static UnauthorizedEvent instance() {
        return INSTANCE;
    }

    private UnauthorizedEvent() {
    }
}