package net.pixnet.sdk.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Blog {

    private RequestController rc;

    public Blog() {
        rc = RequestController.getInstance();
    }

    public void getAllArticleList(String user, String format, String blog_password, String page, String per_page, String category_id, String status, String is_top, String trim_user,Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (page != null) {
            params.add(new BasicNameValuePair("page", page));
        }
        if (per_page != null) {
            params.add(new BasicNameValuePair("per_page", per_page));
        }
        if (category_id != null) {
            params.add(new BasicNameValuePair("category_id", category_id));
        }
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (is_top != null) {
            params.add(new BasicNameValuePair("is_top", is_top));
        }
        if (trim_user != null) {
            params.add(new BasicNameValuePair("trim_user", trim_user));
        }

        Request request = new Request("http://emma.pixnet.cc/blog/articles");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }

    public void getArticle(String id, String user, String format, String blog_password, String article_password, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (article_password != null) {
            params.add(new BasicNameValuePair("article_password", article_password));
        }

        Request request = new Request("http://emma.pixnet.cc/blog/articles/" + id);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
    public void getArticleListByLatest(String user,String format,String blog_password,String limit,String trim_user,Request.RequestCallback callback){
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        if (trim_user != null) {
            params.add(new BasicNameValuePair("trim_user", trim_user));
        }

        Request request = new Request("http://emma.pixnet.cc/blog/articles/latest");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
    public void getArticleListByHot(String user,String format,String blog_password,String limit,String trim_user,Request.RequestCallback callback){
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (blog_password != null) {
            params.add(new BasicNameValuePair("blog_password", blog_password));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit));
        }
        if (trim_user != null) {
            params.add(new BasicNameValuePair("trim_user", trim_user));
        }

        Request request = new Request("http://emma.pixnet.cc/blog/articles/hot");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
    public void searchArticleList(String key,String user,String format,String site,String type,String page,String per_page,Request.RequestCallback callback){
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("user", user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }
        if (site != null) {
            params.add(new BasicNameValuePair("site", site));
        }
        if (type != null) {
            params.add(new BasicNameValuePair("type", type));
        }
        if (page != null) {
            params.add(new BasicNameValuePair("page", page));
        }
        if (per_page != null) {
            params.add(new BasicNameValuePair("per_page", per_page));
        }

        Request request = new Request("http://emma.pixnet.cc/blog/articles/search");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
    public void getBlogInfo(String user, Request.RequestCallback callback) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));

        Request request = new Request("http://emma.pixnet.cc/blog");
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

        Request request = new Request("https://emma.pixnet.cc/blog");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
}