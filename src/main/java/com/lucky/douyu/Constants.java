package com.lucky.douyu;

public interface Constants {

    interface Mongo {
        Boolean USE_MONGO = false;        // 是否使用mongo
        //String CONNECT_STRING = "mongodb://localhost:27017";
        String CONNECT_STRING = "mongodb://39.107.252.8:27017";
        String DATABASE = "bullet_screen";  // mongo数据库名称
        String COLLECTION = "nd_1209";     // 集合名称
    }

    interface BulletScreenReceive {
        String ROOM_ID ="1559610";      // 接收某房间的弹幕
        String GROUP_ID = "-9999";   // 弹幕分组，海量弹幕模式
    }

    // 以下均来自浏览器cookie
    interface BulletScreenSend {
        String ROOM_ID = "1209";
        String USERNAME = "201889880";
        String LTKID = "91799336";
        String STK = "775061bb9a0288a0";
    }
}
