package cn.seu.cs.eshop.service.redisson;

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

    public void init() {
        RLock lock = redisson.getLock("test");
    }
}
