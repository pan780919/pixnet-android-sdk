package net.pixnet.sdk.utils;


import android.text.TextUtils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.BlocksList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Block extends DataProxy {
    private static final String URL_BLOCK = "https://emma.pixnet.cc/blocks";

    public void getBlockList() {
        performAPIRequest(true, URL_BLOCK, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BlocksList res = new BlocksList(response);
                listener.onDataResponse(res);
            }
        });
    }

    public void addBlock(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(true, URL_BLOCK + "/create", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }

    public void removeBlock(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(true, URL_BLOCK + "/delete", Request.Method.DELETE, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                listener.onDataResponse(res);
            }
        }, params);
    }
}
