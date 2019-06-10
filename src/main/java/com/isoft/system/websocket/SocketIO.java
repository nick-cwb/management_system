package com.isoft.system.websocket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class SocketIO implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public SocketIO(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        // 应用启动后调用server启动
        server.start();
        System.out.println("websocket服务成功启动...");
    }
}
