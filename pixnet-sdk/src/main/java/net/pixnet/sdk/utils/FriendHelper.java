package net.pixnet.sdk.utils;

import android.text.TextUtils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.FriendshipList;
import net.pixnet.sdk.response.GroupList;
import net.pixnet.sdk.response.News;
import net.pixnet.sdk.response.NewsList;
import net.pixnet.sdk.response.SubscriptionList;
import net.pixnet.sdk.response.Subscription_groupList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Koi on 2014/8/8.
 */
public class FriendHelper extends DataProxy {
    private static final String URL_NEWS = "https://emma.pixnet.cc/friend/news";
    private static final String URL_GROUP = "https://emma.pixnet.cc/friend/groups";
    private static final String URL_FRIENDSHIP = "https://emma.pixnet.cc/friendships";
    private static final String URL_SUBSCRIPTION = "https://emma.pixnet.cc/friend/subscriptions";
    private static final String URL_SUBSCRIPTION_GROUP = "https://emma.pixnet.cc/friend/subscription_groups";
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
                Helper.log(response);
                NewsList res = new NewsList(response);
                listener.onDataResponse(res);
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

    public void getFriendshipList(String cursor, String cursor_name, String bidirectional) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!TextUtils.isEmpty(cursor))
            params.add(new BasicNameValuePair("cursor", cursor));
        if (!TextUtils.isEmpty(cursor_name))
            params.add(new BasicNameValuePair("cursor_name", cursor_name));
        if (!TextUtils.isEmpty(bidirectional))
            params.add(new BasicNameValuePair("bidirectional", bidirectional));
        performAPIRequest(true, URL_FRIENDSHIP, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                FriendshipList res = new FriendshipList(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void addFriendship(String user_name) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user_name");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        performAPIRequest(true, URL_FRIENDSHIP, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void removeFriendship(String user_name) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user_name");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        performAPIRequest(true, URL_FRIENDSHIP + "/delete", Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void addFriendshipToGroup(String user_name, String group_id) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user_name");
            return;
        }
        if (group_id == null || TextUtils.isEmpty(group_id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":group_id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_FRIENDSHIP + "/append_group", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void removeFriendshipFromGroup(String user_name, String group_id) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user_name");
            return;
        }
        if (group_id == null || TextUtils.isEmpty(group_id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":group_id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_FRIENDSHIP + "/remove_group", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void getSubscribedFriendship(int page, int per_page) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("page", String.valueOf(page)));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));
        performAPIRequest(true, URL_SUBSCRIPTION, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                Helper.log(response);
                SubscriptionList res =new SubscriptionList(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void addSubscription(String user, String group_id) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(group_id))
            params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_SUBSCRIPTION, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void removeSubscription(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void joinSubscriptionGroup(String user, String group_ids) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        if (group_ids == null || TextUtils.isEmpty(group_ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":group_ids");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("group_ids", group_ids));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user + "/join_subscription_group", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void leaveSubscriptionGroup(String user, String group_ids) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        if (group_ids == null || TextUtils.isEmpty(group_ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":group_id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("group_ids", group_ids));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user + "/leave_subscription_group", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void getSubscriptionGroupList() {
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                Subscription_groupList res = new Subscription_groupList(response);
                listener.onDataResponse(res);
            }
        });
    }

    public void addSubscriptionGroup(String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":name");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void updateSubscriptionGroup(String name, String id) {
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
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/" + id, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void removeSubscriptionGroup(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void sortSubscriptionGroupList(String ids) {
        if (ids == null || TextUtils.isEmpty(ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":ids");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ids", ids));
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/position", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }
}
