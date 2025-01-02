package com.example.coffee.Models;

public class Message_list {
    private String sender;
    private String content;
    private String timestamp;

    public Message_list(String sender, String content, String timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
