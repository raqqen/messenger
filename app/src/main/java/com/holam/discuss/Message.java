package com.holam.discuss;

import java.util.Date;

public class Message {


    public String userName;
    public String userMessage;
    private long messageTime;


    public Message(){}
    public Message(String userName, String textMessage)
    {
        this.userName = userName;
        this.userMessage = textMessage;
        this.messageTime = new Date().getTime();
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
