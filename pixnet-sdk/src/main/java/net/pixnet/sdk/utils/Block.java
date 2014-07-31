package net.pixnet.sdk.utils;

import android.content.Context;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Koi on 2014/7/31.
 */
public class Block {
    private RequestController rc;

    public Block(Context c) {
        rc = RequestController.getInstance();
        OAuthConnectionTool.OAuthVersion ver= PIXNET.getOAuthVersion(c);
        OAuthConnectionTool tool;
        if(ver== OAuthConnectionTool.OAuthVersion.VER_1){
            tool=OAuthConnectionTool.newOaut1ConnectionTool(c.getString(R.string.consumer_key), c.getString(R.string.consumer_secret));
            tool.setAccessTokenAndSecret(PIXNET.getOauthAccessToken(c), PIXNET.getOauthAccessSecret(c));
        }
        else{
            tool=OAuthConnectionTool.newOauth2ConnectionTool();
            tool.setAccessToken(PIXNET.getOauthAccessToken(c));
        }
        rc.setHttpConnectionTool(tool);
    }
    public void getBlockList(String format,Request.RequestCallback callback){
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blocks");
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
    public void addBlock(String user,String format,Request.RequestCallback callback){
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user",user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blocks/create");
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
    public void removeBlock(String user,String format,Request.RequestCallback callback){
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user",user));
        if (format != null) {
            params.add(new BasicNameValuePair("format", format));
        }

        Request request = new Request("https://emma.pixnet.cc/blocks/delete");
        request.setMethod(Request.Method.DELETE);
        request.setParams(params);
        request.setCallback(callback);

        rc.addRequest(request);
    }
}
