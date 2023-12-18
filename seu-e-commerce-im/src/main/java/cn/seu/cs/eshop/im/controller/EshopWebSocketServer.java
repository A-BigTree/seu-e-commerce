package cn.seu.cs.eshop.im.controller;

import cn.seu.cs.eshop.im.store.ImMessageStoreService;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Component
@ServerEndpoint("/server/{userId}")
public class EshopWebSocketServer {
    private static final ConcurrentHashMap<Long, EshopWebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private Long userId;
    private Long sessionId;
    private static ImMessageStoreService imMessageStoreService;

    public static void setImMessageStoreService(ImMessageStoreService imMessageStoreService) {
        EshopWebSocketServer.imMessageStoreService = imMessageStoreService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        this.session = session;
        this.userId = userId;
        webSocketMap.put(userId, this);
    }
}
