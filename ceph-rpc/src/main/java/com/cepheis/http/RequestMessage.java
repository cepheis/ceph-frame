package com.cepheis.http;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.entity.ContentType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMessage {

    private String apiName;

    private ContentType contentType;

    private Map<String, String> cookies = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();

    private Map<String, List<String>> parameters = Maps.newHashMap();

    public RequestMessage(String apiName) {
        this(apiName, ContentType.APPLICATION_JSON);
    }

    public RequestMessage(String apiName, ContentType contentType) {
        this.apiName = apiName;
        this.contentType = contentType == null ? ContentType.APPLICATION_JSON : contentType;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addCookie(String name, String value) {
        cookies.put(name, value);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addParameter(String name, String value) {
        List<String> values = parameters.get(name);
        if (values == null) {
            parameters.put(name, values = Lists.newLinkedList());
        }
        values.add(value);
    }

    public Map<String, List<String>> getParameters() {
        return parameters;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
