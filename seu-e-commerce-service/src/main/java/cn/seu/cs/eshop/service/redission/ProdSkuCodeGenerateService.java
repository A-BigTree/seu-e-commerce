package cn.seu.cs.eshop.service.redission;

import cn.seu.cs.eshop.common.component.EshopRedisSessionService;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodSkuCodeGenerate;
import static cs.seu.cs.eshop.common.sdk.util.TimeUtils.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/29
 */
@Service
public class ProdSkuCodeGenerateService {
    @Resource
    EshopRedisSessionService eshopRedisSessionService;

    public String generateSkuCode() {
        return convertString(getCurrentTime(), DATE_TIME_CODE_FORMAT) +
                eshopRedisSessionService.generateUniqueId(prodSkuCodeGenerate);
    }
}
