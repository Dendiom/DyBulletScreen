package com.lucky.douyu.bin;

import com.lucky.douyu.Constants;
import com.lucky.douyu.models.DyBulletScreenClient;
import com.lucky.douyu.models.DyBulletScreenSendClient;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 用于在某房间发送弹幕.
 */
public class DyBulletScreenPublisher {

    private static Timer timer = new Timer();
    private static int num = 0;


    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                timer.cancel();
                System.out.println("exit");
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DyBulletScreenSendClient client = new DyBulletScreenSendClient();
                client.init(Constants.BulletScreenSend.ROOM_ID);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.sendMsg("test " + (num++));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.close();
            }
        }, 1000, 6000);
    }
}
