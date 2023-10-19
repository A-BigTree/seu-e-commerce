package cs.seu.cs.eshop.account.manager;

import cn.seu.cs.eshop.common.util.MysqlUtils;
import cs.seu.cs.eshop.account.cache.EmailVerifyCache;
import cs.seu.cs.eshop.account.dao.EmailVerifyDao;
import cs.seu.cs.eshop.account.pojo.db.EmailVerifyDO;
import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Component
public class EmailSendManager {
    @Resource
    EmailVerifyDao emailVerifyDao;
    @Resource
    EmailVerifyCache emailVerifyCache;
    @Resource
    JavaMailSender javaMailSender;

    @Transactional
    public void addVerifyCode(String account, String verifyCode, SimpleMailMessage message) {
        EmailVerifyDO entity = MysqlUtils.buildEffectEntity(new EmailVerifyDO());
        entity.setAccount(account);
        entity.setVerifyCode(verifyCode);
        emailVerifyDao.insert(entity);
        emailVerifyCache.setEmailVerifyDO(entity);
        javaMailSender.send(message);
    }
}
