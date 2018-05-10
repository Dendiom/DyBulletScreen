package com.lucky.douyu;

public interface Constants {

    interface Mongo {
        Boolean USE_MONGO = false;        // 是否使用mongo
        String CONNECT_STRING = "mongodb://localhost:27017";
        String DATABASE = "bullet_screen";  // mongo数据库名称
        String COLLECTION = "nd_1209";     // 集合名称
    }

    interface BulletScreenReceive {
        String ROOM_ID = "1209";      // 接收某房间的弹幕
        String GROUP_ID = "-9999";   // 弹幕分组，海量弹幕模式
        int LEVEL_LIMIT = 8;         // 忽略几级以下的弹幕
    }

    // 以下均来自浏览器cookie
    interface BulletScreenSend {
        String ROOM_ID = "1209";
        String USERNAME = "201889880";
        String LTKID = "91799336";
        String STK = "775061bb9a0288a0";
    }
}
