package cn.seu.cs.eshop.im.controller;

import cn.seu.cs.eshop.common.util.JsonUtils;
import cn.seu.cs.eshop.im.dto.EshopImMessageListItemDTO;
import cn.seu.cs.eshop.im.store.ImMessageStoreService;
import cs.seu.cs.eshop.common.sdk.entity.dto.EshopImMessageDTO;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;
import static cn.seu.cs.eshop.im.enums.EshopImMessageTypeEnum.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Component
@ServerEndpoint("/server/{userId}")
@Slf4j
public class EshopWebSocketServer {
    private Session session;
    private Long userId;

    private static final ConcurrentHashMap<Long, EshopWebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private static ImMessageStoreService imMessageStoreService;

    public static void setImMessageStoreService(ImMessageStoreService imMessageStoreService) {
        EshopWebSocketServer.imMessageStoreService = imMessageStoreService;
    }

    private void sendMessage(EshopImMessageDTO message) {
        sendMessage(JsonUtils.objectToJson(message));
    }

    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("send message error: {}", message);
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        log.info("User: {} websocket open", userId);
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
        } else {
            webSocketMap.put(userId, this);
        }
        handleLoginMessage(userId);
    }

    @OnClose
    public void onClose() {
        webSocketMap.remove(this.userId);
    }

    @OnMessage
    public void onMessage(String message) {
        handleOnMessage(JsonUtils.jsonToObject(message, EshopImMessageDTO.class));
    }

    @OnError
    public void onError(@PathParam("userId") Long userId, Session session, Throwable error) {
        log.error("User: {} websocket error: {}", userId, error.getMessage());
    }

    private void handleOnMessage(EshopImMessageDTO message) {
        if (message == null) {
            return;
        }
        int msgType = message.getMsgType();
        if (msgType == HEARTBEAT.getType()) {
            handleHeartbeatMessage(message.getFromUserId());
            return;
        }
        imMessageStoreService.sendKafkaMessage(message);
    }

    /**
     * 处理心跳消息
     */
    private static void handleHeartbeatMessage(Long toUserId) {
        if (!webSocketMap.containsKey(toUserId)) {
            return;
        }
        webSocketMap.get(toUserId).sendMessage("pong");
    }

    /**
     * 处理登录消息
     */
    public static void handleLoginMessage(Long toUserId) {
        if (!webSocketMap.containsKey(toUserId)) {
            return;
        }
        List<EshopImMessageListItemDTO> messageList = imMessageStoreService.getMessageList(toUserId);
        EshopImMessageDTO message = EshopImMessageDTO.builder()
                .msgType(LOGIN.getType())
                .fromUserId(OFFICIAL_ID)
                .toUserId(toUserId)
                .content(JsonUtils.objectToJson(messageList))
                .build();
        webSocketMap.get(toUserId).sendMessage(message);
    }

    /**
     * 处理已读未读消息
     */
    public static void handleSessionMessage(Long toUserId, Long fromUserId, String message) {
        if (!webSocketMap.containsKey(toUserId)) {
            return;
        }
        EshopImMessageDTO session = EshopImMessageDTO.builder()
                .msgType(SESSION.getType())
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .content(message)
                .build();
        webSocketMap.get(toUserId).sendMessage(session);
    }

    /**
     * 处理业务消息
     */
    public static void handleBusinessMessage(Long toUserId, EshopImMessageDTO message) {
        if (!webSocketMap.containsKey(toUserId)) {
            return;
        }
        webSocketMap.get(toUserId).sendMessage(message);
    }
}
