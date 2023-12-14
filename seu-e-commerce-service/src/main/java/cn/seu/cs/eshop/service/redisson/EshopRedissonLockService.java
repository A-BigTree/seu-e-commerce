package cn.seu.cs.eshop.service.redisson;

import cn.seu.cs.eshop.common.redis.RedisConf;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Service
public class EshopRedissonLockService {
    @Resource
    private RedissonClient redisson;

    public RLock getLock(RedisConf conf, Object... keys) {
        return redisson.getLock(conf.prefixKey().formatted(keys));
    }
}
