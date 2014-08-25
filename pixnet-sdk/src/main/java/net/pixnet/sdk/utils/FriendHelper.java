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
import net.pixnet.sdk.response.User;

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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onGetFriendNews(new NewsList(response));
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onGetGroupList(new GroupList(response));
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onAddGroup(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onUpdateGroup(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onRemoveGroup(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onGetFriendshipList(new FriendshipList(response));
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onAddFriendship(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onRemoveFriendship(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onAddFriendshipToGroup(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onRemoveFriendshipFromGroup(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onGetSubscribedFriendship(new SubscriptionList(response));
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onAddSubscription(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onRemoveSubscription(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onJoinSubscriptionGroup(res);
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0)
                    listener.onDataResponse(res);
                else listener.onError(res.message);
            }
        }, params);
    }

    public void getSubscriptionGroupList() {
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onGetSubscriptionGroupList(new Subscription_groupList(response));
                }
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0)
                    listener.onDataResponse(res);
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0)
                    listener.onDataResponse(res);
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0)
                    listener.onDataResponse(res);
                else listener.onError(res.message);
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0){
                    if(listener.onDataResponse(res))
                        return;
                    else if(listener instanceof FriendHelperListener)
                        ((FriendHelperListener) listener).onSortSubscriptionGroupList(res);
                }
                else listener.onError(res.message);
            }
        }, params);
    }
}
