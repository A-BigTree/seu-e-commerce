package cs.seu.cs.eshop.account.nacos;

import cn.seu.cs.eshop.common.conf.ShopConf;
import cn.seu.cs.eshop.common.constants.ConfigConstants;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
@Service
@Slf4j
public class AccountConfService implements ShopConf {
    @Resource
    private NacosConfigManager nacosConfigManager;

    private static ConfigService configService;

    @PostConstruct
    public void init() {
        try {
            configService = nacosConfigManager.getConfigService();
        } catch (Exception e) {
            log.error("Nacos Server connect error, e:", e);
        }
    }

    @Override
    public String getContext(String dataId) {
        String res = StringUtils.EMPTY;
        try {
            res = configService.getConfig(ConfigConstants.getDataId(ConfigConstants.COMMON_CONFIG,
                            this.getApplicationName(), dataId),
                    ConfigConstants.CONFIG_GROUP,
                    ConfigConstants.TIMEOUT_CONFIG);
        } catch (NacosException e) {
            log.error("Config: {} request error, e:", dataId, e);
        }
        return res;
    }

    @Override
    public String getApplicationName() {
        return ConfigConstants.ACCOUNT_CONFIG;
    }
}
