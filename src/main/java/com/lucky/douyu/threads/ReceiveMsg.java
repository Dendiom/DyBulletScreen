package com.lucky.douyu.threads;

import com.lucky.douyu.models.DyBulletScreenClient;

public class ReceiveMsg implements Runnable {

    public void run() {
        DyBulletScreenClient client = DyBulletScreenClient.getInstance();
        while (true) {
            while (client.isReady()) {
                client.receiveMsg();
            }

            try {
                System.out.println("receive thread sleep");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
