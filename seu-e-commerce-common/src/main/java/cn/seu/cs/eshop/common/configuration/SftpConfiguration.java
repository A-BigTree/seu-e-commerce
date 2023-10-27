package cn.seu.cs.eshop.common.configuration;

import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/26
 */
@Configuration
public class SftpConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public SftpFactory sftpFactory() {
        return new SftpFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public GenericObjectPoolConfig<ChannelSftp> sftpGenericObjectPoolConfig() {
        return new GenericObjectPoolConfig<>();
    }

    @Bean
    @ConditionalOnMissingBean
    public SftpGenericObjectPool sftpGenericObjectPool(SftpFactory sftpFactory,
                                                       GenericObjectPoolConfig<ChannelSftp> sftpPoolConfig) {
        return new SftpGenericObjectPool(sftpFactory, sftpPoolConfig);
    }
}
