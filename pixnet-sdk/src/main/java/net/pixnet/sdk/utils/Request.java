package net.pixnet.sdk.utils;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * The Request object recorded the url , params, httpmethod , handler ,onResponse , and wait in the queue
 */
public class Request {

    private String url;
    private Method method = Method.GET;
    private List<NameValuePair> params;
    private List<NameValuePair> headers;
    private RequestCallback callback;

    public enum Method{
        GET,
        POST,
        PUT,
        DELETE
    }

    public interface RequestCallback {
        void onResponse(String response);
    }

    Request(String url){
        setUrl(url);
    }

    public void setCallback(RequestCallback callback) {
        this.callback = callback;
    }

    public RequestCallback getCallback() {
        return callback;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

    public List<NameValuePair> getParams() {
        return params;
    }

    /**
     * set http method, default is GET
     * @param method
     */
    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setHeaders(List<NameValuePair> headers) {
        this.headers = headers;
    }

    public List<NameValuePair> getHeaders() {
        return headers;
    }
}
