package com.example.weplan;

public class Messages {

    private String from,to,message,type,time,date,name,messageId;


    public String getFrom() {
        return from;
    }

    public Messages(String message) {
        this.message = message;
    }

    public Messages() {
    }

    public Messages(String from, String to, String message, String type, String time, String date, String name, String messageId) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
        this.time = time;
        this.date = date;
        this.name = name;
        this.messageId = messageId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
