package com.lucky.douyu.models;

import com.lucky.douyu.utils.MsgHandler;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;

public class DyBulletScreenSendClient {

    // 弹幕服务器地址
    private static final String HOSTNAME = "119.90.49.89";

    // 服务器端口号
    private static final int PORT = 8092;

    //设置字节获取buffer的最大值
    private static final int MAX_BUFFER_LENGTH = 4096;

    // 单例
    private static DyBulletScreenSendClient sInstance;
    private Socket socket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private Logger logger = Logger.getLogger(DyBulletScreenSendClient.class);

    public void init(String roomId) {
        connect();
        login(roomId);
    }

    public void sendMsg(String msg) {
        byte[] data = MsgHandler.getMsgData(msg);

        try {
            bos.write(data, 0, data.length);
            bos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            bis.close();
            bos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(String roomId) {
        byte[] data = MsgHandler.getLoginData(roomId);

        try {
            bos.write(data, 0, data.length);
            bos.flush();

            byte[] rec = new byte[MAX_BUFFER_LENGTH];

            bis.read(rec);
            logger.info(new String(rec));

            //todo judge if login successfully
            logger.info("login successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void connect() {
        try {
            socket = new Socket(HOSTNAME, PORT);
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("connect to bullet screen server");
    }
}
