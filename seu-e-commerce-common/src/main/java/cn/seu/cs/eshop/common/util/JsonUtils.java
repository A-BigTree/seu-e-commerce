package cn.seu.cs.eshop.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/10
 */
@Slf4j
public class JsonUtils {

    public final static ObjectMapper INSTANCE = new ObjectMapper();
    public final static ObjectMapper UNDERSCORES_INSTANCE = new ObjectMapper();

    static {
        INSTANCE.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        UNDERSCORES_INSTANCE.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        UNDERSCORES_INSTANCE.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    /**
     * Json字符串转对象
     */
    public static <T> T jsonToObject(ObjectMapper mapper, String json, Class<T> clazz) {
        T res = null;
        try {
            res = mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Json String:{} convert error, e:", json, e);
        }
        return res;
    }

    public static <R, T> R jsonToObject(ObjectMapper mapper, String json, Class<R> clazz1, Class<T> clazz2) {
        R res = null;
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(clazz1, clazz2);
            res = mapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("Json String:{} convert error, e:", json, e);
        }
        return res;
    }

    /**
     * 对象转Json字符
     */
    public static String objectToJson(ObjectMapper mapper, Object o) {
        String res = StringUtils.EMPTY;
        try {
            res = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Object:{} convert error", o);
        }
        return res;
    }

    /**
     * Json字符串转列表
     */
    public static <T> List<T> jsonToList(ObjectMapper mapper, String json, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            list = mapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            log.error("Json String:{} convert list<{}> error", json, clazz.toString());
        }
        return list;
    }

    /**
     * Json字符串转Map
     */
    public static <K, V> Map<K, V> jsonToMap(ObjectMapper mapper, String json, Class<K> key, Class<V> value) {
        Map<K, V> map = new HashMap<>();
        try {
            MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, key, value);
            map = mapper.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            log.error("Json String:{} convert map<{}, {}> error", json, key.toString(), value.toString());
        }
        return map;
    }


    /**
     * Json字符串转对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        return jsonToObject(INSTANCE, json, clazz);
    }

    public static <R, T> R jsonToObject(String json, Class<R> clazz1, Class<T> clazz2) {
        return jsonToObject(INSTANCE, json, clazz1, clazz2);
    }

    /**
     * 对象转Json字符
     */
    public static String objectToJson(Object o) {
        return objectToJson(INSTANCE, o);
    }

    /**
     * Json字符串转列表
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        return jsonToList(INSTANCE, json, clazz);
    }

    /**
     * Json字符串转Map
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> key, Class<V> value) {
        return jsonToMap(INSTANCE, json, key, value);
    }

    public static <T> T snakeCaseJsonToObject(String json, Class<T> clazz) {
        return jsonToObject(UNDERSCORES_INSTANCE, json, clazz);
    }

    public static <R, T> R snakeCaseJsonToObject(String json, Class<R> clazz1, Class<T> clazz2) {
        return jsonToObject(UNDERSCORES_INSTANCE, json, clazz1, clazz2);
    }

    public static String objectToSnakeCaseJson(Object o) {
        return objectToJson(UNDERSCORES_INSTANCE, o);
    }

    public static <T> List<T> snakeCaseJsonToList(String json, Class<T> clazz) {
        return jsonToList(UNDERSCORES_INSTANCE, json, clazz);
    }

    public static <K, V> Map<K, V> snakeCaseJsonToMap(String json, Class<K> key, Class<V> value) {
        return jsonToMap(UNDERSCORES_INSTANCE, json, key, value);
    }
}
