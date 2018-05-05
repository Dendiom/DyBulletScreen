package com.lucky.douyu.bin;

import com.lucky.douyu.Constants;
import com.lucky.douyu.models.DyBulletScreenClient;
import com.lucky.douyu.models.MongoHelper;
import com.lucky.douyu.threads.KeepAlive;
import com.lucky.douyu.threads.ReceiveMsg;

/**
 * 用于获取某房间的弹幕信息.
 */
public class DyBulletScreenReceiver {


    public static void main(String[] args) {

        // 初始化client
        DyBulletScreenClient client = DyBulletScreenClient.getInstance();
        client.init(Constants.BulletScreenReceive.ROOM_ID, Constants.BulletScreenReceive.GROUP_ID);

        // 初始化Mongo客户端
        if (Constants.Mongo.USE_MONGO) {
            MongoHelper mongoHelper = MongoHelper.getInstance();
            mongoHelper.init(Constants.BulletScreenReceive.ROOM_ID);
        }

        // 开启心跳和消息接收线程
        Thread keepAlive = new Thread(new KeepAlive());
        keepAlive.start();
        Thread receiver = new Thread(new ReceiveMsg());
        receiver.start();
    }
}
