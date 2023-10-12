package cs.seu.cs.eshop.account.config;

import cn.seu.cs.eshop.common.conf.ShopConf;
import cn.seu.cs.eshop.common.constants.ApplicationConstants;
import cn.seu.cs.eshop.common.constants.ConfigConstants;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
@Service
@Slf4j
public class AccountConfService implements ShopConf {

    @Value("${nacos.server-addr}")
    private String serverAddr;

    @Value("${nacos.username}")
    private String username;

    @Value("${nacos.password}")
    private String password;

    private static ConfigService configService;

    @PostConstruct
    public void init() {
        log.info("Nacos Server({}?{}&&{}) connect...",serverAddr, username,password);
        try {
            configService = NacosFactory.createConfigService(getNacosProperties(serverAddr, username, password));
        } catch (NacosException e) {
            log.error("Nacos Server({}?{}&&{}) connect error, e:",serverAddr, username,password , e);
        }
    }

    @Override
    public String getContext(String dataId) {
        String res = StringUtils.EMPTY;
        try {
            res = configService.getConfig(dataId, ConfigConstants.CONFIG_GROUP, ConfigConstants.TIMEOUT_CONFIG);
        } catch (NacosException e) {
            log.error("Config: {} request error, e:", dataId, e);
        }
        return res;
    }

    @Override
    public String getApplicationName() {
        return ApplicationConstants.ACCOUNT_APPLICATION;
    }
}
