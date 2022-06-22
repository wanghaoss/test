package com.example.new_androidclient.EventBusMessage;


/**
 *
 */
public class InspectionDetailActivityEventBusMessage {

    public String message;
    public String result;

    public static InspectionDetailActivityEventBusMessage getInstance(String message, String result) {
        return new InspectionDetailActivityEventBusMessage(message, result);
    }

    public static InspectionDetailActivityEventBusMessage getInstance() {
        return new InspectionDetailActivityEventBusMessage();
    }

    private InspectionDetailActivityEventBusMessage(String message,String result) {
        this.message = message;
        this.result = result;
    }

    public InspectionDetailActivityEventBusMessage() {
    }
}