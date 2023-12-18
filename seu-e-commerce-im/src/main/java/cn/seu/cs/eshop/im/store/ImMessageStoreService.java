package cn.seu.cs.eshop.im.store;

import cn.seu.cs.eshop.common.component.EshopRedisService;
import cn.seu.cs.eshop.common.component.EshopRedisSessionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.seu.cs.eshop.im.redis.ImRedisConfigEnum.imSessionIdGenerateKey;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Service
public class ImMessageStoreService {
    @Resource
    private EshopRedisService eshopRedisService;
    @Resource
    private EshopRedisSessionService eshopRedisSessionService;

    public Long generateSessionId() {
        return eshopRedisSessionService.generateUniqueId(imSessionIdGenerateKey);
    }
}
