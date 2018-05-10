package com.lucky.douyu.threads;

import com.lucky.douyu.models.DyBulletScreenClient;
import com.lucky.douyu.models.MongoHelper;
import org.apache.log4j.Logger;

public class ReceiveMsg implements Runnable {

    private Logger logger = Logger.getLogger(ReceiveMsg.class);

    public void run() {
        DyBulletScreenClient client = DyBulletScreenClient.getInstance();
        while (true) {
            while (client.isReady()) {
                client.receiveMsg();
            }

            try {
                logger.info("receive thread sleep");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
