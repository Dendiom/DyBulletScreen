package com.lucky.douyu;

public interface Constants {

    interface Mongo {
        Boolean USE_MONGO = true;        // 是否使用mongo
        String CONNECT_STRING = "mongodb://localhost:27017";
        String DATABASE = "bullet_screen";  // mongo数据库名称
        String COLLECTION = "collection_name";     // 集合名称
    }

    interface BulletScreenReceive {
        String ROOM_ID ="9999";      // 接收某房间的弹幕
        String GROUP_ID = "-9999";   // 弹幕分组，海量弹幕模式
    }

    // 以下均来自浏览器cookie
    interface BulletScreenSend {
        String ROOM_ID = "from_cookie";
        String USERNAME = "from_cookie";
        String LTKID = "from_cookie";
        String STK = "from_cookie";
    }
}
