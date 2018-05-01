package com.cepheis.common.josn;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

public class JsonMapper {

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    private static final JsonMapper defaultMapper = new JsonMapper(Include.NON_NULL);

    private static final JsonMapper timeFormatMapper = new JsonMapper(Include.NON_NULL, DATE_FORMATTER);

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper mapper;

    public JsonMapper(Include inclusion) {
        mapper = new ObjectMapper();
        //设置输出时包含属性的风格
        mapper.setSerializationInclusion(inclusion);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //禁止使用int代表Enum的order()來反序列化Enum,非常危險
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static String to(Object object) {
        return defaultMapper.toJson(object);
    }

    public static <T> T from(String josn, Class<T> javaType) {
        return defaultMapper.fromJson(josn, javaType);
    }

    public static <T> T from(String josn, TypeReference<T> javaType) {
        return defaultMapper.fromJson(josn, javaType);
    }

    public JsonMapper(Include inclusion, String timeFormat) {
        this(inclusion);
        mapper.setDateFormat(new SimpleDateFormat(timeFormat));
    }

    public static JsonMapper buildNonNullMapper() {
        return defaultMapper;
    }

    public static JsonMapper buildNonNullTimeFormatMapper() {
        return timeFormatMapper;
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("Json转换出错", e);
            return null;
        }
    }

    public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Json转换出错", e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.error("Json转换出错", e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, TypeReference<T> javaType) {

        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.error("Json转换出错", e);
            return null;
        }
    }

    public <T> List<T> fromJsonToList(String jsonString, Class<T> clazz) {
        return fromJson(jsonString, constructParametricType(List.class, clazz));
    }
}
