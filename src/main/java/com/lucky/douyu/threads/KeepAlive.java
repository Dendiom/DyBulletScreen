package com.lucky.douyu.threads;

import com.lucky.douyu.Constants;
import com.lucky.douyu.models.DyBulletScreenClient;
import com.lucky.douyu.models.MongoHelper;
import org.apache.log4j.Logger;

public class KeepAlive implements Runnable {

    private Logger logger = Logger.getLogger(KeepAlive.class);

    public void run() {
        DyBulletScreenClient client = DyBulletScreenClient.getInstance();
        while (true) {
            while (client.isReady()) {
                try {
                    Thread.sleep(30000);
                    client.keepAlive();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error("socket closed!!!!");
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
