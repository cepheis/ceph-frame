package com.cepheis.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

public class HttpClientTemplate {

    private int soTimeout;

    private int coTimeout;

    private ClientConfiguration clientConfiguration;

    private CloseableHttpClient closeableHttpClient;

    public HttpClientTemplate setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
        return this;
    }

    public void init() {

        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setExpectContinueEnabled(true)
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setSocketTimeout(soTimeout)
                .setConnectTimeout(coTimeout)
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .build();

        closeableHttpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setDefaultCookieStore(new BasicCookieStore())
                .setDefaultCredentialsProvider(new BasicCredentialsProvider())
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setConnectionManager(clientConfiguration.buildConnectionManager())
                .build();
    }

    public <T> T get(String apiName, Map<String, Object> param) throws URISyntaxException, IOException {
        return closeableHttpClient.execute(new HttpGet(apiName), response -> null);
    }

    public <T> T post() {
        return null;
    }

    public HttpClientTemplate setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
        return this;
    }

    public HttpClientTemplate setCoTimeout(int coTimeout) {
        this.coTimeout = coTimeout;
        return this;
    }
}
