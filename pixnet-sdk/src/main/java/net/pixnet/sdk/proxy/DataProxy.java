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

import java.util.List;

public abstract class DataProxy {

    protected Context c;
    protected DataProxyListener listener;

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

    public interface DataProxyListener{
        void onError(String msg);
        void onDataResponse(BasicResponse response);
    }

}
