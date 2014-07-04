package net.pixnet.sdk.utils;

import android.os.Handler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Blog {
    private RequestController rc;

    Blog(RequestController rc) {
        this.rc = rc;
    }

    public void getBlogInfo(String user, Handler handler) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        rc.request("http://emma.pixnet.cc/blog", params, "GET", handler);
    }

    public void setBlogInfo(String accesstoken, String name, String description, String keyword, String site_category_id,Handler handler) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("access_token", accesstoken));
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
        rc.request("https://emma.pixnet.cc/blog", params, "POST", handler);
    }
}