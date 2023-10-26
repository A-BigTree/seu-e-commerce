package cn.seu.cs.eshop.common.configuration;

import cn.seu.cs.eshop.common.configuration.sftp.SftpFactory;
import cn.seu.cs.eshop.common.configuration.sftp.SftpGenericObjectPool;
import cn.seu.cs.eshop.common.configuration.sftp.SftpUtil;
import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/26
 */
@Configuration
public class SftpConfiguration {
    @Bean
    public SftpFactory sftpFactory() {
        return new SftpFactory();
    }

    @Bean
    @ConfigurationProperties(prefix = "sftp.pool")
    public GenericObjectPoolConfig<ChannelSftp> sftpGenericObjectPoolConfig() {
        return new GenericObjectPoolConfig<>();
    }

    @Bean
    public SftpGenericObjectPool sftpGenericObjectPool(SftpFactory sftpFactory,
                                                       GenericObjectPoolConfig<ChannelSftp> sftpPoolConfig) {
        return new SftpGenericObjectPool(sftpFactory, sftpPoolConfig);
    }

    @Bean
    public SftpUtil sftpUtil(SftpGenericObjectPool pool) {
        return new SftpUtil(pool);
    }
}
