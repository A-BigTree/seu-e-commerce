package cn.seu.cs.eshop.common.nacos;

import cn.seu.cs.eshop.common.constants.ConfigConstants;
import cn.seu.cs.eshop.common.util.JsonUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
public interface ShopConf {

    String getContext(String dataId, String application);

    default <T> T getConfigObject(String dataId, String application, Class<T> clazz) {
        String context = getContext(dataId, application);
        return JsonUtils.jsonToObject(context, clazz);
    }

    default Properties getConfigProperties(String dataId, String application) throws IOException {
        String context = getContext(dataId, application);
        Properties properties = new Properties();
        properties.load(new StringReader(context));
        return properties;
    }

    default <T> List<T> getConfigList(String dataId, String application, Class<T> clazz) {
        String context = getContext(dataId, application);
        return JsonUtils.jsonToList(context, clazz);
    }

    default <K, V> Map<K, V> getConfigMap(String dataId, String application, Class<K> key, Class<V> value) {
        String context = getContext(dataId, application);
        return JsonUtils.jsonToMap(context, key, value);
    }

    default Properties getNacosProperties(String serverAddr, String username, String password) {
        Properties properties = new Properties();
        properties.put(ConfigConstants.SERVER_ADDR, serverAddr);
        properties.put(ConfigConstants.USERNAME, username);
        properties.put(ConfigConstants.PASSWORD, password);
        return properties;
    }

    default String getConfig(ConfDataId<Object> dataId) {
        String res = getContext(dataId.getDataId(), dataId.getApplication());
        if (res == null) {
            return dataId.getDefaultData();
        }
        return res;
    }

    default <D, T extends D> T getConfigObject(ConfDataId<D> dataId, Class<T> clazz) {
        T res = getConfigObject(dataId.getDataId(), dataId.getApplication(), clazz);
        if (res == null) {
            return dataId.getDefaultData();
        }
        return res;
    }

    default <D, T extends D> List<T> getConfigList(ConfDataId<D> dataId, Class<T> clazz) {
        return getConfigList(dataId.getDataId(), dataId.getApplication(), clazz);
    }

    default <K, V, T> Map<K, V> getConfigMap(ConfDataId<T> dataId, Class<K> key, Class<V> value) {
        return getConfigMap(dataId.getDataId(), dataId.getApplication(), key, value);
    }
}
