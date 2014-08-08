package net.pixnet.sdk.utils;

import android.text.TextUtils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.GroupList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Koi on 2014/8/8.
 */
public class Friend extends DataProxy {
    private static final String URL_NEWS = "http://emma.pixnet.cc/friend/news";
    private static final String URL_GROUP = "http://emma.pixnet.cc/friend/groups";
    private static final String URL_FRIENDSHIP = "http://emma.pixnet.cc/friendships";
    /**
     * 預設使用者名稱
     */
    private String defaultUserName = "emmademo";

    public String getDefaultUserName() {
        return defaultUserName;
    }

    public void setDefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    /**
     * 預設每頁幾筆資料
     */
    private int defaultPerPage = 20;

    public int getDefaultPerPage() {
        return defaultPerPage;
    }

    public void setDefaultPerPage(int defaultPerPage) {
        this.defaultPerPage = defaultPerPage;
    }

    /**
     * 預設是否每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳
     */
    private boolean defaultTrimUser = false;

    public boolean isDefaultTrimUser() {
        return defaultTrimUser;
    }

    public void setDefaultTrimUser(boolean defaultTrimUser) {
        this.defaultTrimUser = defaultTrimUser;
    }

    public void getFriendNews(String group_type, String group_id, String before_time) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(group_type))
            params.add(new BasicNameValuePair("group_type", group_type));
        if (!TextUtils.isEmpty(group_id))
            params.add(new BasicNameValuePair("group_id", group_id));
        if (!TextUtils.isEmpty(before_time))
            params.add(new BasicNameValuePair("before_time", before_time));
        performAPIRequest(true, URL_NEWS, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {

            }
        }, params);
    }

    public void getGroupList(int page, int per_page) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));
        performAPIRequest(true, URL_GROUP, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                GroupList res = new GroupList(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void addGroup(String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":name");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_GROUP, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void updateGroup(String id, String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":name");
            return;
        }
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_GROUP + "/" + id, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void removeGroup(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GROUP + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void getFriendshipList() {

    }

    public void addFriendship() {

    }

    public void removeFriendship() {

    }

    public void addFriendshipToGroup() {

    }

    public void removeFriendshipFromGroup() {

    }

    public void getSubscribedFriendship() {

    }

    public void addSubscription() {

    }

    public void removeSubscription() {

    }

    public void joinSubscriptionGroup() {

    }

    public void leaveSubscriptionGroup() {

    }

    public void getSubscriptionGroupList() {

    }

    public void addSubscriptionGroup() {

    }

    public void updateSubscriptionGroup() {

    }

    public void removeSubscriptionGroup() {

    }

    public void sortSubscriptionGroupList() {

    }
}
