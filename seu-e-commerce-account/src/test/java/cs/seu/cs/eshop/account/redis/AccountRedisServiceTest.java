package cs.seu.cs.eshop.account.redis;

import cn.seu.cs.eshop.common.component.EshopRedisService;
import cs.seu.cs.eshop.account.application.AccountApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static cs.seu.cs.eshop.account.redis.AccountRedisConfEnum.accountRedisTest;


@SpringBootTest(classes = {AccountApplication.class, EshopRedisService.class})
@Slf4j
class AccountRedisServiceTest {
    @Resource
    EshopRedisService accountRedisService;

    @Test
    public void testRedis() {
        accountRedisService.setValue("test1", "账户缓存测试", accountRedisTest);
        log.info(accountRedisTest.buildKey("test1"));
        log.info("test1: {}", accountRedisService.getValue("test1", accountRedisTest));
        accountRedisService.setObjectValue("test2", 1234L, accountRedisTest);
        log.info("test2: {}", accountRedisService.getObjectValue("test2", accountRedisTest, Long.class));
    }

}