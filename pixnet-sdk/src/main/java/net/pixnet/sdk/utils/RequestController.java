package net.pixnet.sdk.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class RequestController {
    private Queue<Request> requestQueue = new LinkedList<Request>();
    private Thread worker;
    private OAuthHelper reqOauth;

    RequestController(String client_id, String client_secret) {
        reqOauth = new OAuthHelper(OAuthHelper.OAuthVersion.VER_2, client_id, client_secret);
        runWorker();
    }
    private void runWorker(){
        worker = new Thread(new Runnable() {
            public void run() {
                Request request;
                while ((request = requestQueue.poll()) != null) {
                    String httpMethod = request.getHttpMethod();
                    String response = "";
                    if (httpMethod == "GET") {
                        response = reqOauth.get(request.getRequestURL(), request.getParams());
                    } else if (httpMethod == "POST") {
                        response = reqOauth.post(request.getRequestURL(), request.getParams());
                    } else if (httpMethod == "DELECT") {
                        response = reqOauth.delete(request.getRequestURL(), request.getParams());
                    }
                    if (request.getHandler() != null) {
                        Handler handler = request.getHandler();
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("res", response);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    } else {
                        request.getCallback().callback(response);
                    }
                }

            }
        });
        worker.start();
    }
    void request(String requestUrl, ArrayList<NameValuePair> params, String httpMethod, Handler handler) {
        Request request = new Request(requestUrl, params, httpMethod, handler);
        requestQueue.offer(request);
        if(!worker.isAlive()){
            runWorker();
        }
    }

    void request(String requestUrl, ArrayList<NameValuePair> params, String httpMethod, RequestCallback callback) {
        Request request = new Request(requestUrl, params, httpMethod, callback);
        requestQueue.offer(request);
        if(!worker.isAlive()){
            worker.start();
        }
    }
}

class Request {
    private String requestURL;
    private ArrayList<NameValuePair> params;
    private String httpMethod;
    private Handler handler;
    private RequestCallback callback;

    Request(String requestURL, ArrayList<NameValuePair> params, String httpMethod, Handler handler) {
        this.requestURL = requestURL;
        this.params = params;
        this.httpMethod = httpMethod;
        this.handler = handler;
    }

    Request(String requestURL, ArrayList<NameValuePair> params, String httpMethod, RequestCallback callback) {
        this.requestURL = requestURL;
        this.params = params;
        this.httpMethod = httpMethod;
        this.callback = callback;
    }

    public RequestCallback getCallback() {
        return callback;
    }

    public Handler getHandler() {
        return handler;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public ArrayList<NameValuePair> getParams() {
        return params;
    }

    public String getHttpMethod() {
        return httpMethod;
    }
}

interface RequestCallback {
    void callback(String response);
}