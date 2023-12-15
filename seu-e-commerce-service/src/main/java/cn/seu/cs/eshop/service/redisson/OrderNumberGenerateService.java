package cn.seu.cs.eshop.service.redisson;

import cn.seu.cs.eshop.common.component.EshopRedisSessionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.orderNumberGenerate;
import static cs.seu.cs.eshop.common.sdk.util.TimeUtils.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Service
public class OrderNumberGenerateService {
    @Resource
    EshopRedisSessionService eshopRedisSessionService;

    public String generateOrderNumber() {
        return convertString(getCurrentTime(), DATE_TIME_CODE_FORMAT) +
                eshopRedisSessionService.generateUniqueId(orderNumberGenerate);
    }
}
