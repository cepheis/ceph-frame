package com.cepheis.spring.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiangou.order.framework.rest.JsonData;
import com.tiangou.order.framework.rest.JsonObject;
import com.tiangou.order.framework.rest.JsonSuccess;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Order(Integer.MIN_VALUE)
@ControllerAdvice
public class JsonResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getMethodAnnotation(JsonIgnore.class) == null;
    }

    @Override
    public Object beforeBodyWrite(
            Object obj, MethodParameter methodParameter,
            MediaType mediaType, Class<? extends HttpMessageConverter<?>> converterType,
            ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (obj == null) {
            return new JsonSuccess();
        } else if (obj instanceof JsonObject) {
            return obj;
        } else {
            return new JsonData(obj);
        }
    }
}  