package cn.seu.cs.eshop.service.configuration;

import jakarta.annotation.Resource;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/13
 */
@Configuration
public class RedissonConfiguration {
    @Resource
    private RedisProperties properties;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s", properties.getHost(),properties.getPort()+"");
        config.useSingleServer().setAddress(redisUrl);
        config.useSingleServer().setDatabase(2);
        config.useSingleServer().setConnectionMinimumIdleSize(10);
        return Redisson.create(config);
    }
}
