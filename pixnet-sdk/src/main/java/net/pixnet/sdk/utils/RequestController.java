package net.pixnet.sdk.utils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Control the request queue
 */
public class RequestController {
    private Queue<Request> requestQueue = new LinkedList<Request>();
    private OAuthHelper reqOauth;
    private int taskLimit = 3;
    private int taskSize = 0;

    /**
     * @param client_id
     * @param client_secret
     */
    RequestController(String client_id, String client_secret) {
        reqOauth = new OAuthHelper(OAuthHelper.OAuthVersion.VER_2, client_id, client_secret);
    }

    /**
     * Set Tasks limits
     */
    public void setLimit(int taskLimit){
        this.taskLimit = taskLimit;
    }
    /**
     * Create a AsyncTask to check if their are request in the queue
     */
    private void controller() {
        Request request;
        while (taskSize < taskLimit) {
            if ((request = requestQueue.poll()) != null) {
                taskSize++;
                runWorker(request);
            } else {
                break;
            }
        }
    }

    /**
     * Controller to handle responses
     *
     * @param request
     * @param response
     */
    private void controller(Request request, String response) {
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
        taskSize--;
        controller();
    }

    /**
     * Worker AsyncTask instead of Thread
     */
    private class Worker extends AsyncTask<Request, Void, String> {
        Request request;

        protected String doInBackground(Request... requestArray) {
            request = requestArray[0];
            String response = "";
            if (request != null) {
                String httpMethod = request.getHttpMethod();
                if (httpMethod == "GET") {
                    response = reqOauth.get(request.getRequestURL(), request.getParams());
                } else if (httpMethod == "POST") {
                    response = reqOauth.post(request.getRequestURL(), request.getParams());
                } else if (httpMethod == "DELECT") {
                    response = reqOauth.delete(request.getRequestURL(), request.getParams());
                }
            }
            return response;
        }

        protected void onPostExecute(String response) {
            controller(request, response);
        }
    }

    private void runWorker(Request request) {
        new Worker().execute(request);
    }

    /**
     * Get user request and put into the queue / Handler
     *
     * @param requestUrl
     * @param params
     * @param httpMethod
     * @param handler
     */
    void request(String requestUrl, ArrayList<NameValuePair> params, String httpMethod, Handler handler) {
        Request request = new Request(requestUrl, params, httpMethod, handler);
        requestQueue.offer(request);
        controller();
    }

    /**
     * Get user request and put into the queue / RequestCallback
     *
     * @param requestUrl
     * @param params
     * @param httpMethod
     * @param callback
     */
    void request(String requestUrl, ArrayList<NameValuePair> params, String httpMethod, RequestCallback callback) {
        Request request = new Request(requestUrl, params, httpMethod, callback);
        requestQueue.offer(request);
        controller();
    }
}

/**
 * The Request object recorded the url , params, httpmethod , handler ,callback , and wait in the queue
 */
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