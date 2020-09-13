package com.example.chatappfirebase;

import java.util.Date;

class ModelClass {
    private String message,displayName;
    private long time ;
    public ModelClass(String message, String displayName) {
        this.displayName=displayName;
        this.message=message;
        this.time=new Date().getTime();
    }

    public ModelClass() {
    }

    public String getMessage() {
        return message;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getTime() {
        return time;
    }
}
