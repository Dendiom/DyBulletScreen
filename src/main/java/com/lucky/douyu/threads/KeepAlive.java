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
                }
            }

            try {
                logger.info("keepAlive thread sleep");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
