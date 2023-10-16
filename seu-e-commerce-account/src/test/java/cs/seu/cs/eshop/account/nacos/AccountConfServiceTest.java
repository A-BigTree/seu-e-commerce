package cs.seu.cs.eshop.account.nacos;

import cs.seu.cs.eshop.account.application.AccountApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static cs.seu.cs.eshop.account.nacos.ObjectConfig.accountTest;


@SpringBootTest(classes = {AccountApplication.class, AccountConfService.class})
@Slf4j
class AccountConfServiceTest {

    @Resource
    AccountConfService accountConfService;

    @Test
    void getContext() {
        log.info(accountConfService.getContext("test"));
        log.info(accountConfService.getConfig(accountTest));
    }
}