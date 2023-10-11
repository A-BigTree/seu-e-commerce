package cs.seu.cs.eshop.account.config;

import cn.seu.cs.eshop.common.conf.ShopConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
@Configuration
public class DataSourceConfiguration {

    @Autowired
    ShopConf accountConfService;


}
