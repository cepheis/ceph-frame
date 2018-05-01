package com.cepheis.http;

import com.cepheis.http.exception.HttpStatusNotOkException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class JsonResponseHandler<T> implements ResponseHandler<T> {

    private Class<T> resultClass;

    public JsonResponseHandler(Class<T> resultClass) {
        this.resultClass = resultClass;
    }

    @Override
    public T handleResponse(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpStatusNotOkException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new ClientProtocolException("response no content return.");
        }
        String body = EntityUtils.toString(entity);

        return JSON.parseObject(body, resultClass);
    }
}
