package net.pixnet.sdk.utils;


import android.text.TextUtils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.BlocksList;
import net.pixnet.sdk.proxy.Error;
import net.pixnet.sdk.response.NotificationList;
import net.pixnet.sdk.response.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class BlockHelper extends DataProxy {
    private static final String URL_BLOCK = "https://emma.pixnet.cc/blocks";

    /**
     * 列出黑名單
     */
    public void getBlockList() {
        performAPIRequest(true, URL_BLOCK, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res = new BasicResponse(response);
                if (res.error == 0) {
                    if (listener.onDataResponse(res))
                        return;
                    else if (listener instanceof BlockHelperListener)
                        ((BlockHelperListener) listener).onGetBlockList(new BlocksList(response));
                } else listener.onError(res.message);
            }
        });
    }

    /**
     * 新增黑名單
     * @param user 欲加入黑名單的使用者名稱
     */
    public void addBlock(String... user) {
        if(user==null || user.length<1){
            listener.onError(Error.MISS_PARAMETER + ":user");
            return;
        }
        String userstr="";
        for(String str : user){
            if(!TextUtils.isEmpty(str)) {
                if(userstr.length()>0)
                    userstr+=",";
                userstr += str;
            }
        }
        if(userstr.length()<1){
            listener.onError(Error.MISS_PARAMETER + ":user");
            return;
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("api_version", "2"));
        params.add(new BasicNameValuePair("user", userstr));
        performAPIRequest(true, URL_BLOCK + "/create", Request.Method.POST, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                BasicResponse res=new BasicResponse(response);
                if(res.error==0)
                    listener.onDataResponse(res);
                else listener.onError(res.message);
            }
        }, params);
    }

    /**
     * 移除黑名單
     * @param user 欲移除黑名單的使用者名稱
     */
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
                BasicResponse res=new BasicResponse(response);
                if(res.error==0)
                    listener.onDataResponse(res);
                else listener.onError(res.message);
            }
        }, params);
    }
}
