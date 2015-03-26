package net.pixnet.sdk.utils;

import android.content.Context;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.proxy.Error;

public class OauthRequestController extends RequestController {
    
    private static OauthRequestController INSTANCE;
    private Context context;
    
    /**
     * constructor
     * @return the instance of OauthRequestController
     */
    synchronized
    public static OauthRequestController getInstance(Context context){
        if(INSTANCE==null)
            INSTANCE = new OauthRequestController(context);
        return INSTANCE;
    }

    private OauthRequestController(Context c){
        context=c;
    }

    @Override
    protected void onResponse(final Request request, final String response) {
        if(response== Error.TOKEN_EXPIRED){
            String refreshToken=PIXNET.getOauthRefreshToken(context);
            PIXNET.refreshToken(refreshToken, context, new PIXNET.OnAccessTokenGotListener() {

                @Override
                public void onAccessTokenGot(String token, String secret) {}

                @Override
                public void onAccessTokenGot(String token, String refreshToken, long expires) {
                    PIXNET.setOauthAccessToken(context, token);
                    PIXNET.setOauthRefreshToken(context, refreshToken);
                    PIXNET.setOauthTokenExpireTime(context, expires);
                    addRequest(request);
                    runWorker();
                }

                @Override
                public void onError(String msg) {
                    OauthRequestController.super.onResponse(request, response);
                }
            });
        }
        else super.onResponse(request, response);
    }
}
