package com.lucky.douyu.threads;

import com.lucky.douyu.Constants;
import com.lucky.douyu.models.DyBulletScreenClient;

public class KeepAlive implements Runnable {

    public void run() {
        DyBulletScreenClient client = DyBulletScreenClient.getInstance();
        while (true) {
            while (client.isReady()) {
                try {
                    Thread.sleep(30000);
                    client.keepAlive();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    client.setReady(false);
                    client.reconnect(Constants.BulletScreenReceive.ROOM_ID, Constants.BulletScreenReceive.GROUP_ID);
                }
            }

            try {
                Thread.sleep(5000);
                if (!client.isReady()) {
                    client.reconnect(Constants.BulletScreenReceive.ROOM_ID, Constants.BulletScreenReceive.GROUP_ID);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
