package cn.seu.cs.eshop.common.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Configuration
public class RedisTemplateConfiguration {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setDefaultSerializer(StringRedisSerializer.UTF_8);
        return template;
    }

    @Bean(name = "sessionTemplate")
    public RedisTemplate<String, Long> sessionTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setDefaultSerializer(StringRedisSerializer.UTF_8);
        return template;
    }
}
