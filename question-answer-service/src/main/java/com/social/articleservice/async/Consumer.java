package com.social.articleservice.async;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class Consumer implements InitializingBean {
    BlockingQueue<Event> queue = new LinkedBlockingQueue<>();

    public BlockingQueue<Event> getQueue() {
        return queue;
    }
    //取出任务并执行
    public void afterPropertiesSet() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Event e = queue.take();
                        e.handle();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
