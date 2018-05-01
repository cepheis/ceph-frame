package com.cepheis.http;

import com.cepheis.http.config.HttpConfig;
import org.apache.http.Consts;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This example demonstrates how to customize and configure the most common aspects
 * of HTTP request execution and connection management.
 */
public class ClientConfiguration {

    private HttpConfig httpConfig;

    public HttpConfig getHttpConfig() {
        return httpConfig == null ? httpConfig = new HttpConfig() : httpConfig;
    }

    public ClientConfiguration setHttpConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
        return this;
    }

    protected void startConnMonitor(PoolingHttpClientConnectionManager connManager) {
        HttpConfig config = getHttpConfig();
        ScheduledExecutorService executorService =
                Executors.newSingleThreadScheduledExecutor((r) -> new Thread(r));
        executorService.scheduleWithFixedDelay(
                new IdleConnectionMonitorThread(connManager, config.getIdleInterval()), 1, 5, TimeUnit.MILLISECONDS);
    }

    public PoolingHttpClientConnectionManager buildConnectionManager() {

        HttpConfig config = getHttpConfig();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(SSLContexts.createSystemDefault()))
                .build();

        PoolingHttpClientConnectionManager connManager =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry, new SystemDefaultDnsResolver());

        connManager.setValidateAfterInactivity(1000);

        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoKeepAlive(config.isSoKeepAlive())
                .build();

        connManager.setDefaultSocketConfig(socketConfig);

        //connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);

        MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxLineLength(config.getMaxLineLength())
                .setMaxHeaderCount(config.getMaxHeaderCount())
                .build();

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setCharset(Consts.UTF_8)
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setMessageConstraints(messageConstraints)
                .build();

        connManager.setDefaultConnectionConfig(connectionConfig);

        //connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);

        connManager.setMaxTotal(config.getMaxTotalConn());

        connManager.setDefaultMaxPerRoute(config.getMaxPerRoute());

        return connManager;


        //connManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), 20);

//        CookieStore cookieStore = new BasicCookieStore();
//        // Use custom credentials provider if necessary.
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        // Create global request configuration
//        RequestConfig defaultRequestConfig = RequestConfig.custom()
//                .setExpectContinueEnabled(true)
//                .setCookieSpec(CookieSpecs.DEFAULT)
//                .setSocketTimeout(config.getSoTimeout())
//                .setConnectTimeout(config.getCoTimeout())
//                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
//                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
//                .build();
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setConnectionManager(connManager)
//                .setDefaultCookieStore(cookieStore)
//                .setDefaultCredentialsProvider(credentialsProvider)
//                //.setProxy(new HttpHost("myproxy", 8080))
//                .setDefaultRequestConfig(defaultRequestConfig)
//                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
//                .build();

//        return httpClient;

//        try {
//            HttpGet httpget = new HttpGet("http://httpbin.org/get");
//
//            RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
//                    .setSocketTimeout(3000)
//                    .setConnectTimeout(1000)
//                    .setConnectionRequestTimeout(5000)
//                    //.setProxy(new HttpHost("myotherproxy", 8080))
//                    .build();
//
//            httpget.setConfig(requestConfig);
//
//            HttpClientContext context = HttpClientContext.create();
//
//            context.setCookieStore(cookieStore);
//
//            context.setCredentialsProvider(credentialsProvider);
//
//            CloseableHttpResponse response = httpClient.execute(httpget, context);
//            try {
//                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                System.out.println(EntityUtils.toString(response.getEntity()));
//                System.out.println("----------------------------------------");
//
//                // Once the request has been executed the local context can
//                // be used to examine updated state and various objects affected
//                // by the request execution.
//
//                // Last executed request
//                context.getRequest();
//                // Execution route
//                context.getHttpRoute();
//                // Target auth state
//                context.getTargetAuthState();
//                // Proxy auth state
//                context.getTargetAuthState();
//                // Cookie origin
//                context.getCookieOrigin();
//                // Cookie spec used
//                context.getCookieSpec();
//                // User security token
//                context.getUserToken();
//
//            } finally {
//                response.close();
//            }
//        } finally {
//            httpclient.close();
//        }
    }
}
