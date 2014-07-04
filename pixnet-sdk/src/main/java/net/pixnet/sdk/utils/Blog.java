package net.pixnet.sdk.utils;

import android.os.Handler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Blog {
    private RequestController rc;
    Blog(RequestController rc){
        this.rc=rc;
    }
    public void getBlogInfo(String user,Handler handler){
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user",user));
        rc.request("http://emma.pixnet.cc/blog",params,"GET",handler);
    }
}