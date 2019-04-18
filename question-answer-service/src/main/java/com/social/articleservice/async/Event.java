package com.social.articleservice.async;

public class Event implements Runnable {
    //0为问题发布
    public void handle() {
    }

    @Override
    public void run() {
        this.handle();
    }
}
