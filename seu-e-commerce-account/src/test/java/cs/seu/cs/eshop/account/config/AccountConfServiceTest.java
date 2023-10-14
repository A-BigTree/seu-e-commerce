package cs.seu.cs.eshop.account.config;

import cs.seu.cs.eshop.account.application.AccountApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static cs.seu.cs.eshop.account.config.ObjectConfig.accountTest;


@SpringBootTest(classes = {AccountApplication.class, AccountConfService.class})
@Slf4j
class AccountConfServiceTest {

    @Autowired
    AccountConfService accountConfService;

    @Test
    void getContext() {
        log.info(accountConfService.getContext("test"));
        log.info(accountConfService.getConfig(accountTest));
    }
}