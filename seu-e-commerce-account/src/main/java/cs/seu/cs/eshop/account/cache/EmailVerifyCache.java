package cs.seu.cs.eshop.account.cache;

import cn.seu.cs.eshop.common.nacos.ShopConf;
import cn.seu.cs.eshop.common.redis.RedisService;
import cn.seu.cs.eshop.common.util.TimeUtils;
import cs.seu.cs.eshop.account.dao.EmailVerifyDao;
import cs.seu.cs.eshop.account.pojo.db.EmailVerifyDO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static cs.seu.cs.eshop.account.nacos.AccountNacosConfEnum.emailVerifyTime;
import static cs.seu.cs.eshop.account.redis.AccountRedisConfEnum.accountEmailVerify;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Component
public class EmailVerifyCache {
    @Resource
    RedisService eshopRedisService;
    @Resource
    EmailVerifyDao emailVerifyDao;
    @Resource
    ShopConf eshopConfService;

    public EmailVerifyDO getEmailVerifyDO(String account) {
        EmailVerifyDO entity = eshopRedisService.getObjectValue(account, accountEmailVerify, EmailVerifyDO.class);
        if (entity == null) {
            List<EmailVerifyDO> emailVerifyList = emailVerifyDao.selectedByAccount(account);
            if (CollectionUtils.isEmpty(emailVerifyList)) {
                return null;
            }
            Long verifyTime = eshopConfService.getConfigObject(emailVerifyTime, Long.class);
            EmailVerifyDO res = emailVerifyList.stream()
                    .filter(e -> TimeUtils.getCurrentTime() < TimeUtils.offsetDateTime(e.getCreateTime(), verifyTime, TimeUnit.MINUTES))
                    .findAny().orElse(null);
            if (res != null) {
                eshopRedisService.setObjectValue(account, res, accountEmailVerify);
            }
            return res;
        }
        return entity;
    }

    public void setEmailVerifyDO(EmailVerifyDO entity) {
        if(entity == null) {
            return;
        }
        eshopRedisService.setObjectValue(entity.getAccount(), entity, accountEmailVerify);
    }

    public Boolean removeEmailVerifyDO(EmailVerifyDO entity) {
        if (entity == null) {
            return false;
        }
        return eshopRedisService.removeValue(entity.getAccount(), accountEmailVerify);
    }
}
