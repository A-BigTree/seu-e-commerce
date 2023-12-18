package cn.seu.cs.eshop.im.configuration;

import cn.seu.cs.eshop.im.controller.EshopWebSocketServer;
import cn.seu.cs.eshop.im.store.ImMessageStoreService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Configuration
public class WebSocketConfig {
    @Resource
    private ImMessageStoreService imMessageStoreService;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @PostConstruct
    public void init() {
        EshopWebSocketServer.setImMessageStoreService(imMessageStoreService);
    }
}
