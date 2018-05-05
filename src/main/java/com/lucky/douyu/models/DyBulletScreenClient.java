package com.lucky.douyu.models;

import com.lucky.douyu.utils.MsgHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;

public class DyBulletScreenClient {

    // 弹幕服务器地址
    private static final String HOSTNAME = "openbarrage.douyutv.com";

    // 服务器端口号
    private static final int PORT = 8601;

    //设置字节获取buffer的最大值
    private static final int MAX_BUFFER_LENGTH = 4096;

    // 单例
    private static DyBulletScreenClient sInstance;
    private Socket socket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private volatile boolean ready;

    private DyBulletScreenClient(){}

    /**
     * 获取单例
     * @return DyBulletScreenClient.
     */
    public static DyBulletScreenClient getInstance() {
        if (sInstance == null) {
            sInstance = new DyBulletScreenClient();
        }

        return sInstance;
    }

    public void init(String roomId, String groupId) {
        Runtime.getRuntime().addShutdownHook(new ExitHandler());
        connect();
        login(roomId);
        joinGroup(roomId, groupId);
        ready = true;
    }

    /**
     * 发送心跳包.
     */
    public void keepAlive() {
        byte[] data = MsgHandler.getKeepAliveData();

        try {
            bos.write(data, 0, data.length);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收弹幕等消息.
     */
    public void receiveMsg() {
        byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
        String dataStr;
        try {
            int length = bis.read(recvByte, 0, recvByte.length);
            if (length < 12) {
                return;
            }

            dataStr = new String(recvByte, 12, length - 12);
            MsgHandler.recvMsgHandler(dataStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        return ready;
    }

    /**
     * 建立socket连接.
     */
    private void connect() {
        try {
            String host = Inet4Address.getByName(HOSTNAME).getHostAddress();
            socket = new Socket(host, PORT);
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("connect to bullet screen server");
    }

    /**
     * 登录到房间服务器.
     */
    private void login(String roomId) {
        byte[] data = MsgHandler.getNormalLoginData(roomId);

        try {
            bos.write(data, 0, data.length);
            bos.flush();

//            byte[] recv = new byte[MAX_BUFFER_LENGTH];
//            bis.read(recv, 0 , recv.length);
            //todo judge if login successfully
            System.out.println("login successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 加入到弹幕组.
     */
    private void joinGroup(String roomId, String groupId) {
        byte[] data = MsgHandler.getJoinGroupData(roomId, groupId);

        try {
            bos.write(data, 0, data.length);
            bos.flush();

            System.out.println("login room successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class ExitHandler extends Thread {

        @Override
        public void run() {
            try {
                ready = false;
                bis.close();
                bos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("exit");
        }
    }
}
