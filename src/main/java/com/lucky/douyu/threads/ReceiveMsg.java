package com.lucky.douyu.threads;

import com.lucky.douyu.models.DyBulletScreenClient;

public class ReceiveMsg implements Runnable {

    public void run() {
        DyBulletScreenClient client = DyBulletScreenClient.getInstance();
        while (client.isReady()) {
            client.receiveMsg();
        }
    }
}
