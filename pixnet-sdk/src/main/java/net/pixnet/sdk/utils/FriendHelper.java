package net.pixnet.sdk.utils;

import android.text.TextUtils;

import net.pixnet.sdk.proxy.*;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.FriendshipList;
import net.pixnet.sdk.response.GroupList;
import net.pixnet.sdk.response.NewsList;
import net.pixnet.sdk.response.SubscriptionGroupList;
import net.pixnet.sdk.response.SubscriptionList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

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

    @Override
    protected boolean handleBasicResponse(String response) {
        if(super.handleBasicResponse(response))
            return true;
        if(listener instanceof FriendHelperListener)
            return false;
        return true;
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
                if(handleBasicResponse(response))
                    return;
                NewsList parsedResponse;
                try {
                    parsedResponse=new NewsList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onGetFriendNews(parsedResponse);
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
                if(handleBasicResponse(response))
                    return;
                GroupList parsedResponse;
                try {
                    parsedResponse=new GroupList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onGetGroupList(parsedResponse);
            }
        }, params);
    }

    public void addGroup(String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_GROUP, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onAddGroup(parsedResponse);
            }
        }, params);
    }

    public void updateGroup(String id, String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_GROUP + "/" + id, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onUpdateGroup(parsedResponse);
            }
        }, params);
    }

    public void removeGroup(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GROUP + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onRemoveGroup(parsedResponse);
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
                if(handleBasicResponse(response))
                    return;
                FriendshipList parsedResponse;
                try {
                    parsedResponse=new FriendshipList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onGetFriendshipList(parsedResponse);
            }
        }, params);
    }

    public void addFriendship(String user_name) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        performAPIRequest(true, URL_FRIENDSHIP, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onAddFriendship(parsedResponse);
            }
        }, params);
    }

    public void removeFriendship(String user_name) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        performAPIRequest(true, URL_FRIENDSHIP + "/delete", Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onRemoveFriendship(parsedResponse);
            }
        }, params);
    }

    public void addFriendshipToGroup(String user_name, String group_id) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (group_id == null || TextUtils.isEmpty(group_id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_FRIENDSHIP + "/append_group", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onAddFriendshipToGroup(parsedResponse);
            }
        }, params);
    }

    public void removeFriendshipFromGroup(String user_name, String group_id) {
        if (user_name == null || TextUtils.isEmpty(user_name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (group_id == null || TextUtils.isEmpty(group_id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_FRIENDSHIP + "/remove_group", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onRemoveFriendshipFromGroup(parsedResponse);
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
                if(handleBasicResponse(response))
                    return;
                SubscriptionList parsedResponse;
                try {
                    parsedResponse=new SubscriptionList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onGetSubscribedFriendship(parsedResponse);
            }
        }, params);
    }

    public void addSubscription(String user, String group_id) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(group_id))
            params.add(new BasicNameValuePair("group_id", group_id));
        performAPIRequest(true, URL_SUBSCRIPTION, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onAddSubscription(parsedResponse);
            }
        }, params);
    }

    public void removeSubscription(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onRemoveSubscription(parsedResponse);
            }
        }, params);
    }

    public void joinSubscriptionGroup(String user, String group_ids) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (group_ids == null || TextUtils.isEmpty(group_ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("group_ids", group_ids));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user + "/join_subscription_group", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onJoinSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void leaveSubscriptionGroup(String user, String group_ids) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (group_ids == null || TextUtils.isEmpty(group_ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("group_ids", group_ids));
        performAPIRequest(true, URL_SUBSCRIPTION + "/" + user + "/leave_subscription_group", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onLeaveSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void getSubscriptionGroupList() {
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                SubscriptionGroupList parsedResponse;
                try {
                    parsedResponse=new SubscriptionGroupList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onGetSubscriptionGroupList(parsedResponse);
            }
        });
    }

    public void addSubscriptionGroup(String name) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onAddSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void updateSubscriptionGroup(String name, String id) {
        if (name == null || TextUtils.isEmpty(name)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/" + id, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onUpdateSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void removeSubscriptionGroup(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onRemoveSubscriptionGroup(parsedResponse);
            }
        }, params);
    }

    public void sortSubscriptionGroupList(String ids) {
        if (ids == null || TextUtils.isEmpty(ids)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER);
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ids", ids));
        performAPIRequest(true, URL_SUBSCRIPTION_GROUP + "/position", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((FriendHelperListener) listener).onSortSubscriptionGroupList(parsedResponse);
            }
        }, params);
    }
}
