package cs.seu.cs.eshop.account.config;

import cn.seu.cs.eshop.common.conf.ShopConf;
import cs.seu.cs.eshop.account.application.AccountApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = AccountApplication.class)
@Slf4j
class AccountConfServiceTest {

    @Autowired
    AccountConfService accountConfService;

    @Test
    void getContext() {
        log.info(accountConfService.getContext("eshop.test.config.test"));
    }
}