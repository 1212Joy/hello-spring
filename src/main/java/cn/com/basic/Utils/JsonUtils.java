package cn.com.basic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * Created by zhaijiayi on 2016/7/5.
 */
public class JsonUtils {
    public static  ObjectMapper mapper = new ObjectMapper();


    public static  <T> T toBeanList(String json,Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return mapper.readValue(json, javaType);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }


    public static String toJson(Object object) {
        try {
            return null == object?"":mapper.writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return StringUtils.isBlank(json)?null:mapper.readValue(json, clazz);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

}
