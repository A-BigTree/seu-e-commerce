package cn.seu.cs.eshop.service.redisson;

import jakarta.annotation.Resource;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Service
public class EshopRedissonLockService {
    @Resource
    private Redisson redisson;

    public void init() {
        RLock lock = redisson.getLock("test");
    }
}
