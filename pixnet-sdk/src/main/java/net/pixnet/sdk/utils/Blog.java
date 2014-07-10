package net.pixnet.sdk.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Blog {

    private RequestController rc;

    public Blog() {
       rc=RequestController.getInstance();
    }

    public void getBlogInfo(String user, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));

        Request request=new Request("http://emma.pixnet.cc/blog");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void setBlogInfo(String access_token, String name, String description, String keyword, String site_category_id, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("access_token", access_token));
        if (name != null) {
            params.add(new BasicNameValuePair("name", name));
        }
        if (description != null) {
            params.add(new BasicNameValuePair("description", description));
        }
        if (keyword != null) {
            params.add(new BasicNameValuePair("keyword", keyword));
        }
        if (site_category_id != null) {
            params.add(new BasicNameValuePair("site_category_id", site_category_id));
        }

        Request request=new Request("https://emma.pixnet.cc/blog");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
}