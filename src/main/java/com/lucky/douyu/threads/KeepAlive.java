package com.lucky.douyu.threads;

import com.lucky.douyu.models.DyBulletScreenClient;

public class KeepAlive implements Runnable {

    public void run() {
        DyBulletScreenClient client = DyBulletScreenClient.getInstance();
        while (client.isReady()) {
            try {
                client.keepAlive();
                Thread.sleep(45000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
