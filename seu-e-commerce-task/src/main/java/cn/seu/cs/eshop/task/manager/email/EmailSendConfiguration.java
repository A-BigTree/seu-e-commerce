package cn.seu.cs.eshop.task.manager.email;

import cn.seu.cs.eshop.common.component.EshopConfService;
import cn.seu.cs.eshop.task.bo.EmailSendClientBO;
import com.alibaba.nacos.api.config.listener.Listener;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static cn.seu.cs.eshop.task.nacos.TaskNacosConfEnum.emailSendClientConf;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Configuration
@Slf4j
public class EmailSendConfiguration {
    @Resource
    private EshopConfService eshopConfService;
    private JavaMailSenderImpl javaMailSender;
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);


    @PostConstruct
    public void init() {
        try {
            EshopConfService.configService.addListener(
                    EshopConfService.getDataId(emailSendClientConf.getDataId(), emailSendClientConf.getApplication()),
                    EshopConfService.getGroup(),
                    new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return null;
                        }
                        @Override
                        public void receiveConfigInfo(String s) {
                            log.info("Email Client config changed");
                            updateSendClient();
                        }
                    });
        } catch (Exception e) {
            log.error("Add listener error. e:", e);
        }
        this.updateSendClient();
    }

    private void updateSendClient() {
        readWriteLock.writeLock().lock();
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        EmailSendClientBO emailSendClient =
                eshopConfService.getConfigObject(emailSendClientConf, EmailSendClientBO.class);
        jms.setHost(emailSendClient.getHost());
        jms.setUsername(emailSendClient.getUsername());
        jms.setPassword(emailSendClient.getPassword());
        jms.setDefaultEncoding(emailSendClient.getDefaultEncoding());
        javaMailSender = jms;
        readWriteLock.writeLock().unlock();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JavaMailSenderImpl javaMailSender() {
        readWriteLock.readLock().lock();
        try {
            return javaMailSender;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
