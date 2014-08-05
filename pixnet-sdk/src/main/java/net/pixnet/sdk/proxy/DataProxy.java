package net.pixnet.sdk.proxy;

import android.content.Context;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.response.BasicResponse;
import net.pixnet.sdk.utils.ConnectionTool;
import net.pixnet.sdk.utils.HttpConnectionTool;
import net.pixnet.sdk.utils.OAuthConnectionTool;

public abstract class DataProxy {

    protected Context c;
    protected DataProxyListener listener;

    public void setContext(Context context){
        c=context;
    }

    public void setListener(DataProxyListener listener){
        this.listener=listener;
    }

    protected ConnectionTool getConnectionTool(boolean authentication){
        if(authentication){
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

    public interface DataProxyListener{
        void onError(String msg);
        void onDataResponse(BasicResponse response);
    }

}
