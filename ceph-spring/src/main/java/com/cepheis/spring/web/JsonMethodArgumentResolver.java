package com.cepheis.spring.web;

import com.cepheis.spring.ann.JsonParam;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/6/16.
 */
public class JsonMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

    protected final static Logger LOGGER = LoggerFactory.getLogger(JsonMethodArgumentResolver.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final TypeFactory typeFactory = TypeFactory.defaultInstance();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        JsonParam ann = parameter.getParameterAnnotation(JsonParam.class);
        return ann != null ? new JsonParamNamedValueInfo(ann) : new JsonParamNamedValueInfo();
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) {
        String[] paramValues = request.getParameterValues(name);
        if (paramValues != null) {
            return convertObject(parameter, paramValues);
        }
        return null;
    }

    public <T> T fromJson(String jsonString, Type type) throws IOException {
        return mapper.readValue(jsonString, typeFactory.constructType(type));
    }

    public Object calcValue(MethodParameter methodParameter, String[] values) {
        if (values.length == 0) {
            return null;
        }
        if (values.length > 1) {
            throw new IllegalArgumentException("json param length must be 1");
        }
        try {
            return fromJson(values[0], methodParameter.getGenericParameterType());
        } catch (Exception e) {
            LOGGER.warn("param resolver failed", e);
            return values[0];
        }
    }

    private Object convertObject(MethodParameter methodParameter, String[] values) {
        Class<?> clz = methodParameter.getParameterType();
        if (BeanUtils.isSimpleProperty(clz)) {
            return values.length == 1 ? values[0] : values;
        }
        return calcValue(methodParameter, values);
    }

    private static class JsonParamNamedValueInfo extends NamedValueInfo {

        public JsonParamNamedValueInfo() {
            super("", false, ValueConstants.DEFAULT_NONE);
        }

        public JsonParamNamedValueInfo(JsonParam annotation) {
            super(annotation.name(), annotation.required(), annotation.defaultValue());
        }
    }
}
