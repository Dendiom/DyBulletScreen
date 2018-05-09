package com.lucky.douyu.utils;


import com.lucky.douyu.Constants;
import com.lucky.douyu.models.MongoHelper;
import org.bson.Document;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MsgHandler {

    private static final int MESSAGE_TYPE_CLIENT = 689;
    private static final int MESSAGE_TYPE_SERVER = 690;
    private static final int BULK = 55;
    private static final String TYPE_BULLET_SCREEN = "chatmsg";

    private static final String BULLET_SCREEN_NICKNAME = "nn";
    private static final String BULLET_SCREEN_TEXT = "txt";
    private static final String BULLET_SCREEN_LEVEL = "level";

    private static List<Document> docs = new ArrayList<Document>();
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static byte[] getKeepAliveData() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", "mrkl");

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getNormalLoginData(String roomId) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "loginreq");
        params.put("roomid", roomId);

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getMsgData(String msg) {
        msg = msg.replaceAll("@", "@A").replaceAll("/", "@S");
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "chatmessage");
        params.put("receiver", "0");
        params.put("content", msg);
        params.put("scope", "");
        params.put("col", "0");
        params.put("pid","");
        params.put("p2p", "0");
        params.put("nc", "0");
        params.put("rev", "0");
        params.put("ifs", "0");

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getLoginData(String roomId) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String vk = MD5Util.MD5(timestamp + "7oE9nPEG9xXV69phU31FYCLUagKeYtsF" + uuid);

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "loginreq");
        params.put("username", Constants.BulletScreenSend.USERNAME);
        params.put("ct", "0");
        params.put("password", "");
        params.put("roomid", roomId);
        params.put("devid", uuid);
        params.put("rt", timestamp);
        params.put("vk", vk);
        params.put("ver", "20150929");
        params.put("aver", "2017073111");
        params.put("ltkid", Constants.BulletScreenSend.LTKID);
        params.put("biz", "1");
        params.put("stk", Constants.BulletScreenSend.STK);

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    public static byte[] getJoinGroupData(String roomId, String groupId) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("type", "joingroup");
        params.put("rid", roomId);
        params.put("gid", groupId);

        return generateDyMsg(new MsgEncoder().encode(params));
    }

    /**
     * 按消息内心分发消息.
     */
    public static void recvMsgHandler(String data) {
        //System.out.println(data);
        String[] params = data.split("/");
        if (params[0] != null && params[0].length() > 7) {
            String type = params[0].substring(6);
            if (TYPE_BULLET_SCREEN.equals(type)) {
                bulletScreenHandler(params);

            }
        }
    }

    /**
     * 处理弹幕消息.
     */
    private static void bulletScreenHandler(String[] params) {
        Document doc = new Document();
        doc.put("timestamp", System.currentTimeMillis() / 1000);
        for (String param : params) {
            // System.out.println(param);
            String[] pairs = param.split("@=");
            if (BULLET_SCREEN_NICKNAME.equals(pairs[0])) {
                doc.append("nickname", pairs[1].replaceAll("@S", "/")
                        .replaceAll("@A", "@"));
                continue;
            }

            if (BULLET_SCREEN_LEVEL.equals(pairs[0])) {
                int level = Integer.valueOf(pairs[1]);
                if (level < Constants.BulletScreenReceive.LEVEL_LIMIT) {
                    return;
                }

                doc.append("level", level);
                continue;
            }

            if (BULLET_SCREEN_TEXT.equals(pairs[0])) {
                doc.append("text", pairs[1].replaceAll("@S", "/")
                        .replaceAll("@A", "@"));
            }
        }

        docs.add(doc);
        if (docs.size() >= BULK) {   // 写入mongo

            if (Constants.Mongo.USE_MONGO) {
                final List<Document> copy = new ArrayList<Document>(docs);
                docs.clear();
                fixedThreadPool.execute(new Runnable() {
                    public void run() {
                        MongoHelper.getInstance().insertDocs(copy);
                    }
                });
            } else {
                docs.clear();
            }
        }

        System.out.println(doc);
    }

    private static byte[] generateDyMsg(String data) {
        //System.out.println(data);
        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        DataOutputStream doutput = new DataOutputStream(boutput);

        try {
            boutput.reset();
            doutput.write(FormatTransfer.toLH(data.length() + 8), 0, 4);        // 4 bytes packet length
            doutput.write(FormatTransfer.toLH(data.length() + 8), 0, 4);        // 4 bytes packet length
            doutput.write(FormatTransfer.toLH(MESSAGE_TYPE_CLIENT), 0, 2);   // 2 bytes message type
            doutput.writeByte(0);                                               // 1 bytes encrypt
            doutput.writeByte(0);                                               // 1 bytes reserve
            doutput.writeBytes(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return boutput.toByteArray();
    }

}
