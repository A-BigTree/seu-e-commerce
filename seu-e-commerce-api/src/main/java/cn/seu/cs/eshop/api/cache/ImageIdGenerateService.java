package cn.seu.cs.eshop.api.cache;

import cn.seu.cs.eshop.common.component.EshopRedisSessionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.api.redis.ApiRedisConfigEnum.imageUniqueIdGenerateKey;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
public class ImageIdGenerateService {
    @Resource
    EshopRedisSessionService eshopRedisSessionService;

    public Long generateUniqueId() {
        return eshopRedisSessionService.generateUniqueId(imageUniqueIdGenerateKey);
    }
}
