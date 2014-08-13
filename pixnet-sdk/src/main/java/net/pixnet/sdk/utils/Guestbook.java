package net.pixnet.sdk.utils;

import android.text.TextUtils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.Guestbook;
import net.pixnet.sdk.response.GuestbookList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Koi on 2014/8/8.
 */
public class GuestBook extends DataProxy {

    private static final String URL_GUESTBOOK = "http://emma.pixnet.cc/guestbook";

    public void getGuestbookList() {
        getGuestbookList(defaultUserName, null, null, defaultPerPage);
    }

    public void getGuestbookList(String user, String filter, String cursor, int per_page) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        if (!TextUtils.isEmpty(filter))
            params.add(new BasicNameValuePair("filter", filter));
        if (!TextUtils.isEmpty(cursor))
            params.add(new BasicNameValuePair("cursor", cursor));
        params.add(new BasicNameValuePair("per_page", String.valueOf(per_page)));
        performAPIRequest(false, URL_GUESTBOOK, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                GuestbookList res = new GuestbookList(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void addGuestbook(String user, String title, String body) {
        addGuestbook(user, title, body, null, null, null, null);
    }

    public void addGuestbook(String user, String title, String body, String auther, String url, String email, String is_open) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        if (title == null || TextUtils.isEmpty(title)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":title");
            return;
        }
        if (body == null || TextUtils.isEmpty(body)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":body");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("title", user));
        params.add(new BasicNameValuePair("body", user));
        if (!TextUtils.isEmpty(auther))
            params.add(new BasicNameValuePair("auther", auther));
        if (!TextUtils.isEmpty(url))
            params.add(new BasicNameValuePair("url", url));
        if (!TextUtils.isEmpty(email))
            params.add(new BasicNameValuePair("email", email));
        if (!TextUtils.isEmpty(is_open))
            params.add(new BasicNameValuePair("is_open", is_open));
        performAPIRequest(true, URL_GUESTBOOK, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void getGuestbook(String id) {
        getGuestbook(defaultUserName, id);
    }

    public void getGuestbook(String user, String id) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("id", id));
        performAPIRequest(false, URL_GUESTBOOK + "/" + id, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                Guestbook res = new Guestbook(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void replyGuestbook(String id, String reply) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        if (reply == null || TextUtils.isEmpty(reply)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":reply");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("reply", reply));
        params.add(new BasicNameValuePair("id", id));
        performAPIRequest(true, URL_GUESTBOOK + "/" + id + "/reply", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void setGuestbookVisibility(String id, boolean visible) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        String url;
        if (visible) {
            url = URL_GUESTBOOK + "/" + id + "/open";
        } else {
            url = URL_GUESTBOOK + "/" + id + "/close";
        }
        performAPIRequest(true, url, Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void markGuestbookSpam(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GUESTBOOK + "/" + id + "/mark_spam", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void markGuestbookHam(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GUESTBOOK + "/" + id + "/mark_ham", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void removeGuestbook(String id) {
        if (id == null || TextUtils.isEmpty(id)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":id");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        performAPIRequest(true, URL_GUESTBOOK + "/" + id, Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }
}
