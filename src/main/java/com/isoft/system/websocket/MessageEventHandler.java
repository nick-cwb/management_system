package com.isoft.system.websocket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.isoft.system.entity.MessageInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MessageEventHandler {
    public static SocketIOServer socketIOServer;
    // 所有ws client列表
    static ArrayList<UUID> listClient = new ArrayList<>();

    @Autowired
    public MessageEventHandler(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @OnConnect
    public void onConnect(SocketIOClient socketIOClient) {
        listClient.add(socketIOClient.getSessionId());
        System.out.println("客户端:" + socketIOClient.getSessionId() + "已连接");

        Timer timer = new Timer();
        Random random = new Random();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendBuyLogEvent("backMsg", String.valueOf(random.nextInt(100)));
                System.out.println("消息已推送" + DateTime.now().toString());
            }
        }, 0, 5000);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient socketIOClient) {
        System.out.println("客户端:" + socketIOClient.getSessionId() + "断开连接");
        listClient.remove(listClient.indexOf(socketIOClient.getSessionId()));
    }

    @OnEvent(value = "infoEvent")
    public void onEvent(SocketIOClient socketIOClient, AckRequest request, MessageInfo data) {
        System.out.println("发来消息：" + data.getMegContent());
        socketIOServer.getClient(socketIOClient.getSessionId()).sendEvent("backMsg", "back data");
    }

    public static void sendBuyLogEvent(String event, String msg) {   //这里就是向客户端推消息了
        for (UUID clientId : listClient) {
            if (socketIOServer.getClient(clientId) == null) continue;
            socketIOServer.getClient(clientId).sendEvent(event, DateTime.now().toDateTime(), msg);
        }
    }
}
