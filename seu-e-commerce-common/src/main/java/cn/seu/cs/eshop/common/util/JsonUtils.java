package cn.seu.cs.eshop.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/10
 */
@Slf4j
public class JsonUtils {

    public final static ObjectMapper INSTANCE = new ObjectMapper();

    /**
     * Json字符串转对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        T res = null;
        try {
            res = INSTANCE.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Json String:{} convert error", json);
        }
        return res;
    }

    /**
     * 对象转Json字符
     */
    public static String objectToJson(Object o) {
        String res = StringUtils.EMPTY;
        try {
            res = INSTANCE.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Object:{} convert error", o);
        }
        return res;
    }

    /**
     * Json字符串转列表
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            list = INSTANCE.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Json String:{} convert list<{}> error", json, clazz.toString());
        }
        return list;
    }

    /**
     * Json字符串转Map
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> key, Class<V> value) {
        Map<K, V> map = new HashMap<>();
        try {
            map = INSTANCE.readValue(json, new TypeReference<Map<K, V>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Json String:{} convert map<{}, {}> error", json, key.toString(), value.toString());
        }
        return map;
    }
}
