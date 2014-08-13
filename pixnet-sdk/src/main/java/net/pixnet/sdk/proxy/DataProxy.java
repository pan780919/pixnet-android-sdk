package net.pixnet.sdk.proxy;

import android.content.Context;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.utils.ConnectionTool;
import net.pixnet.sdk.utils.HttpConnectionTool;
import net.pixnet.sdk.utils.OAuthConnectionTool;
import net.pixnet.sdk.utils.Request;
import net.pixnet.sdk.utils.Request.Method;
import net.pixnet.sdk.utils.Request.RequestCallback;
import net.pixnet.sdk.utils.RequestController;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract class DataProxy {

    protected Context c;
    protected DataProxyListener listener;

    /**
     * 預設使用者名稱
     */
    protected String defaultUserName="emmademo";
    public String getDefaultUserName() {
        return defaultUserName;
    }
    public void setDefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    /**
     * 預設每頁幾筆資料
     */
    protected int defaultPerPage =20;
    public int getDefaultPerPage() {
        return defaultPerPage;
    }
    public void setDefaultPerPage(int defaultPerPage) {
        this.defaultPerPage = defaultPerPage;
    }

    /**
     * 預設是否每篇文章都要回傳作者資訊, 如果設定為 true, 則就不回傳
     */
    protected boolean defaultTrimUser = false;
    public boolean isDefaultTrimUser() {
        return defaultTrimUser;
    }
    public void setDefaultTrimUser(boolean defaultTrimUser) {
        this.defaultTrimUser = defaultTrimUser;
    }

    public void setContext(Context context){
        c=context;
    }

    public void setListener(DataProxyListener listener){
        this.listener=listener;
    }

    protected ConnectionTool getConnectionTool(){
        boolean isLogin=PIXNET.isLogin(c);
        if(isLogin){
            OAuthConnectionTool tool;
            switch (PIXNET.getOAuthVersion(c)){
                case VER_1:
                    tool=OAuthConnectionTool.newOaut1ConnectionTool(PIXNET.getConsumerKey(c), PIXNET.getConsumerSecret(c));
                    tool.setAccessTokenAndSecret(PIXNET.getOauthAccessToken(c), PIXNET.getOauthAccessSecret(c));
                    return tool;
                case VER_2:
                    tool=OAuthConnectionTool.newOauth2ConnectionTool();
                    tool.setAccessToken(PIXNET.getOauthAccessToken(c));
                    return tool;
                default:
                    return null;
            }
        }
        else{
            return new HttpConnectionTool();
        }
    }

    protected void performAPIRequest(boolean authentication, String url, RequestCallback callback){
        performAPIRequest(authentication, url, callback, null);
    }
    protected void performAPIRequest(boolean authentication, String url, RequestCallback callback, List<NameValuePair> params){
        performAPIRequest(authentication, url, Method.GET, callback, params);
    }
    protected void performAPIRequest(boolean authentication, String url, Method method, RequestCallback callback){
        performAPIRequest(authentication, url, method, callback, null);
    }
    protected void performAPIRequest(boolean authentication, String url, Method method, RequestCallback callback, List<NameValuePair> params){
        if(authentication){
            boolean isLogin=PIXNET.isLogin(c);
            if(!isLogin){
                listener.onError(Error.LOGIN_NEED);
                return;
            }
        }

        Request r=new Request(url);
        r.setMethod(method);
        if(params!=null)
            r.setParams(params);
        r.setCallback(callback);

        RequestController rc=RequestController.getInstance();
        rc.setHttpConnectionTool(getConnectionTool());
        rc.addRequest(r);
    }

    public static boolean getJsonBoolean(JSONObject jo, String name){
        boolean b;
        try {
            b=jo.getBoolean(name);
        } catch (JSONException e) {
            try {
                int n=jo.getInt(name);
                b=n==0?false:true;
            } catch (JSONException e1) {
                try {
                    String s=jo.getString(name);
                    b=s.equals("0")?false:true;
                } catch (JSONException e2) {
                    return false;
                }
            }
        }
        return b;
    }

    public interface DataProxyListener{
        void onError(String msg);
        void onDataResponse(BasicResponse response);
    }

}
