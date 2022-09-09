package com.example.homework01.impl;

public interface MessageSender {
    void setCallback(MessageCallback callback);
    void sendMessage(String message);
}
