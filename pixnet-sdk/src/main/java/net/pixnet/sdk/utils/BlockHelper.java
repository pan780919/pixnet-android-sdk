package net.pixnet.sdk.utils;


import android.text.TextUtils;

import net.pixnet.sdk.proxy.DataProxy;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.response.BlocksList;
import net.pixnet.sdk.proxy.Error;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BlockHelper extends DataProxy {
    private static final String URL_BLOCK = "https://emma.pixnet.cc/blocks";

    @Override
    protected boolean handleBasicResponse(String response) {
        if(super.handleBasicResponse(response))
            return true;
        if(listener instanceof BlockHelperListener)
            return false;
        return true;
    }

    /**
     * 列出黑名單
     */
    public void getBlockList() {
        performAPIRequest(true, URL_BLOCK, new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                if(handleBasicResponse(response))
                    return;
                BlocksList parsedResponse;
                try {
                    parsedResponse=new BlocksList(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((BlockHelperListener) listener).onGetBlockList(parsedResponse);
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
                if(handleBasicResponse(response))
                    return;
                BasicResponse parsedResponse;
                try {
                    parsedResponse=new BasicResponse(response);
                } catch (JSONException e) {
                    listener.onError(Error.DATA_PARSE_FAILED);
                    return;
                }
                ((BlockHelperListener) listener).onAddBlock(parsedResponse);
            }
        }, params);
    }

    /**
     * 移除黑名單
     * @param user 欲移除黑名單的使用者名稱
     */
    public void deleteBlock(String user) {
        if (user == null || TextUtils.isEmpty(user)) {
            listener.onError(net.pixnet.sdk.proxy.Error.MISS_PARAMETER + ":user");
            return;
        }
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        performAPIRequest(true, URL_BLOCK + "/delete", Request.Method.DELETE, new Request.RequestCallback() {
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
                ((BlockHelperListener) listener).onDeleteBlock(parsedResponse);
            }
        }, params);
    }
}
