package net.pixnet.sdk.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Control the request queue
 */
public class RequestController {

    private static RequestController INSTANCE;
    protected static final int DEF_TASK_LIMIT = 3;
    protected int taskLimit = DEF_TASK_LIMIT;

    protected Queue<Request> requestQueue;
    protected List<Worker> taskList;
    protected ConnectionTool httpConn;

    /**
     * constructor
     * @return the instance of RequestController
     */
    synchronized
    public static RequestController getInstance(){
        if(INSTANCE==null)
            INSTANCE = new RequestController();
        return INSTANCE;
    }

    protected RequestController(){
        requestQueue = new LinkedList<Request>();
        taskList = new ArrayList<Worker>();
    }

    /**
     * Set Tasks limits
     */
    public void setLimit(int taskLimit){
        this.taskLimit = taskLimit;
    }

    public void setHttpConnectionTool(ConnectionTool tool){
        httpConn=tool;
    }

    protected HttpConnectionTool getHttpConnectionTool(){
        if(httpConn==null){
            httpConn = new HttpConnectionTool();
        }
        return (HttpConnectionTool) httpConn;
    }

    protected int getTaskSize(){
        return taskList.size();
    }

    public void addRequest(Request request){
       if(requestQueue.offer(request)){
           runWorker();
       }
    }

    synchronized
    protected void runWorker(){
        if(getTaskSize() < taskLimit){
            Request request=requestQueue.poll();
            if(request!=null){
                Worker worker=new Worker();
                taskList.add(worker);
                worker.execute(request);
            }
        }
    }

    protected void onResponse(Request request, String response){
        Request.RequestCallback callback=request.getCallback();
        if(callback!=null)
            callback.onResponse(response);
        runWorker();
    }

    /**
     * Worker AsyncTask instead of Thread
     */
    protected class Worker extends AsyncTask<Request, Void, String> {
        Request request;

        protected String doInBackground(Request... requestArray) {
            String response;
            request = requestArray[0];
            if (request != null) {
                try {
                    response=getHttpConnectionTool().performRequest(request);
                } catch (IOException e) {
                    e.printStackTrace();
                    response=null;
                }
            }
            else{
                response=null;
            }
            return response;
        }

        protected void onPostExecute(String response) {
            taskList.remove(this);
            onResponse(request, response);
        }
    }
}

