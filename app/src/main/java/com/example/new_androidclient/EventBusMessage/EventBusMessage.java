package com.example.new_androidclient.EventBusMessage;

public class EventBusMessage {

    public String message;

    public static EventBusMessage getInstance(String message) {
        return new EventBusMessage(message);
    }

    public static EventBusMessage getInstance() {
        return new EventBusMessage();
    }

    private EventBusMessage(String message) {
        this.message = message;
    }

    public EventBusMessage() {
    }
}

