package cn.seu.cs.eshop.common.component;

import cn.seu.cs.eshop.common.nacos.ShopConf;
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
 * Created on 2023/10/16
 */
@Service
@Slf4j
public class EshopConfService implements ShopConf {
    @Resource
    private NacosConfigManager nacosConfigManager;

    public static ConfigService configService;

    @PostConstruct
    public void init() {
        try {
            configService = nacosConfigManager.getConfigService();
        } catch (Exception e) {
            log.error("Nacos Server connect error, e:", e);
        }
    }

    @Override
    public String getContext(String dataId, String application) {
        String res = StringUtils.EMPTY;
        try {
            String key = ConfigConstants.getDataId(ConfigConstants.COMMON_CONFIG, application, dataId);
            res = configService.getConfig(key, ConfigConstants.CONFIG_GROUP, ConfigConstants.TIMEOUT_CONFIG);
            log.info("Get nacos config -> (key: {}, value: {})", key, res);
        } catch (NacosException e) {
            log.error("Config: {} request error, e:", dataId, e);
        }

        return res;
    }

    public static String getDataId(String dataId, String application) {
        return ConfigConstants.getDataId(ConfigConstants.COMMON_CONFIG, application, dataId);
    }

    public static String getGroup() {
        return ConfigConstants.CONFIG_GROUP;
    }
}
