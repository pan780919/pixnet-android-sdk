package net.pixnet.sdk.utils;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RequestControler {
    private Queue<Request> requestQueue = new LinkedList<Request>();
    private Thread worker;
    private OAuthHelper reqOauth;

    RequestControler(String client_id, String client_secret) {
        reqOauth = new OAuthHelper(OAuthHelper.OAuthVersion.VER_2, client_id, client_secret);
        worker = new Thread(new Runnable() {
            public void run() {
                Request request;
                while (true) {
                    if ((request = requestQueue.poll()) != null) {
                        String httpMethod = request.getHttpMethod();
                        if (httpMethod == "GET") {
                            System.out.println(reqOauth.get(request.getRequestURL(), request.getParams()));
                        } else if (httpMethod == "POST") {
                            System.out.println(reqOauth.post(request.getRequestURL(), request.getParams()));
                        } else if (httpMethod == "DELECT") {
                            System.out.println(reqOauth.delete(request.getRequestURL(), request.getParams()));
                        }
                    }
                }
            }
        });
        worker.start();
    }

    void request(String requestUrl, ArrayList<NameValuePair> params, String httpMethod) {
        Request request = new Request(requestUrl, params, httpMethod);
        requestQueue.offer(request);
    }
}

class Request {
    private String requestURL;
    private ArrayList<NameValuePair> params;
    private String httpMethod;

    Request(String requestURL, ArrayList<NameValuePair> params, String httpMethod) {
        this.requestURL = requestURL;
        this.params = params;
        this.httpMethod = httpMethod;
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