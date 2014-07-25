package net.pixnet.sdk;

import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;
import android.webkit.WebView;

import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.OAuthLoginHelper;

public class PIXNET {
    private static final String URL_OAUTH2_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_OAUTH2_GRANT = "https://emma.pixnet.cc/oauth2/grant";
    private static final String URL_OAUTH1_REQUEST = "http://emma.pixnet.cc/oauth/request_token";
    private static final String URL_OAUTH1_ACCESS = "http://emma.pixnet.cc/oauth/access_token";

    public static void oAuth2Login(final Context context, final OnAccessTokenGotListener listener){
        WebView webView = new WebView(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(webView)
                .create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        OAuthLoginHelper helper=OAuthLoginHelper.newAoth2LoginHelper(context.getString(R.string.consumer_key), context.getString(R.string.consumer_secret), URL_OAUTH2_AUTH, URL_OAUTH2_GRANT);
        helper.setOAuthLoginListener(new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {
                dialog.dismiss();
            }

            @Override
            public void onVerify() {

            }

            @Override
            public void onAccessTokenGot(String token, String secret) {
                Helper.log("onAccessTokenGot");
                if(dialog.isShowing())
                    dialog.dismiss();
                listener.onAccessTokenGot(token,secret);
            }
        });
        helper.loginByOauth2(webView);
    }

    public static void oAuth1Login(final Context context, final OnAccessTokenGotListener listener){
        WebView webView=new WebView(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(webView)
                .create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        OAuthLoginHelper loginHelper=OAuthLoginHelper.newAoth1LoginHelper(context.getString(R.string.consumer_key), context.getString(R.string.consumer_secret), URL_OAUTH1_REQUEST, URL_OAUTH1_ACCESS);
        loginHelper.setOAuthLoginListener(new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {
                Helper.log("onRequestUrlGot");
            }

            @Override
            public void onVerify() {
                Helper.log("onVerify");
            }

            @Override
            public void onAccessTokenGot(String token, String secret){
                listener.onAccessTokenGot(token, secret);
                dialog.dismiss();
            }
        });
        loginHelper.loginByOauth1(webView);
    }

    public interface OnAccessTokenGotListener{
        void onAccessTokenGot(String token, String secret);
    }

}
