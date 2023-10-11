package cn.seu.cs.eshop.common.conf;

import cn.seu.cs.eshop.common.util.JsonUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
public interface ShopConf {

    String getContext();

    String getApplicationName();
    
    default <T> T getConfigObject(Class<T> clazz) {
        String context = getContext();
        return JsonUtils.jsonToObject(context, clazz);
    }

    default Properties getConfigProperties() throws IOException {
        String context = getContext();
        Properties properties = new Properties();
        properties.load(new StringReader(context));
        return properties;
    }
}
