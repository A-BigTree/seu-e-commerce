package cs.seu.cs.eshop.account.nacos;

import cn.seu.cs.eshop.common.component.EshopConfService;
import cs.seu.cs.eshop.account.EshopAccountApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static cs.seu.cs.eshop.account.nacos.AccountNacosConfEnum.accountTest;


@SpringBootTest(classes = {EshopAccountApplication.class, EshopConfService.class})
@Slf4j
class AccountConfServiceTest {

    @Resource
    EshopConfService accountConfService;

    @Test
    void getContext() {
        log.info(accountConfService.getContext("emailVerifyLength", accountTest.getApplication()));
        log.info(accountConfService.getConfig(accountTest));
    }
}