package cs.seu.cs.eshop.account.redis;

import cs.seu.cs.eshop.account.application.AccountApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static cs.seu.cs.eshop.account.redis.AccountRedisConfEnum.accountRedisTest;


@SpringBootTest(classes = {AccountApplication.class, AccountRedisService.class})
@Slf4j
class AccountRedisServiceTest {
    @Resource
    AccountRedisService accountRedisService;

    @Test
    public void testRedis() {
        accountRedisService.setValue("test", "账户缓存测试", accountRedisTest);
        log.info(accountRedisService.getValue("test", accountRedisTest));
    }

}