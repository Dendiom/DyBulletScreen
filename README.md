# DyBulletScreen
在官方demo的基础上进行修改，实现自动获取/发送斗鱼弹幕信息，并将获取的信息存入mongo数据库

## 使用方式
* 获取弹幕       
修改Constants常量类下的相关信息:
``` 
    interface Mongo {    
        Boolean USE_MONGO = true;        // 是否使用mongo
        String CONNECT_STRING = "mongodb://localhost:27017";
        String DATABASE = "bullet_screen";  // mongo数据库名称
        String COLLECTION = "collection_name";     // 集合名称
      }   
    
    interface BulletScreenReceive {
        String ROOM_ID ="9999";      // 接收某房间的弹幕
        String GROUP_ID = "-9999";   // 弹幕分组，海量弹幕模式
        int LEVEL_LIMIT = 5;         // 忽略一定等级下的弹幕
    }
```
&#8194;&#8194;&#8194;&#8194;运行 *bin/DyBulletScreenReceiver*

* 发送弹幕   
修改Constants常量类下的相关信息：
```
    interface BulletScreenSend {
        String ROOM_ID = "9999";
        String USERNAME = "from_cookie";
        String LTKID = "from_cookie";
        String STK = "from_cookie";
    }
```    
&#8194;&#8194;&#8194;&#8194;运行 *bin/DyBulletScreenPublisher*

## 存在问题
* 通过wireshark抓包获取发送弹幕数据包进行分析，其中的dmvv字段来源还不清楚
* 发送弹幕时只能发送英文，中文会出现编码错误
* 如果发现弹幕无法发送，先在网页上进入房间尝试发送弹幕并进行身份验证
* 现在发送弹幕一段时间后，斗鱼需要进行身份验证，这个问题尚未解决，因此这个弹幕发送无法达到弹幕机器人的效果


  

