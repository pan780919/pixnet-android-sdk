package net.pixnet.sdk;

import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;
import android.webkit.WebView;

import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.OAuthLoginHelper;
import net.pixnet.sdk.utils.OAuthConnectionTool.OAuthVersion;

public class PIXNET {
    private static final String URL_OAUTH2_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_OAUTH2_GRANT = "https://emma.pixnet.cc/oauth2/grant";
    private static final String URL_OAUTH1_REQUEST = "http://emma.pixnet.cc/oauth/request_token";
    private static final String URL_OAUTH1_ACCESS = "http://emma.pixnet.cc/oauth/access_token";

    public static void oAuth1Login(final Context context, final OnAccessTokenGotListener listener){
        WebView webView=new WebView(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(webView)
                .create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        OAuthLoginHelper loginHelper=OAuthLoginHelper.newAoth1LoginHelper(context.getString(R.string.consumer_key), context.getString(R.string.consumer_secret), URL_OAUTH1_REQUEST, URL_OAUTH1_ACCESS, new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {
            }

            @Override
            public void onVerify() {
            }

            @Override
            public void onAccessTokenGot(String token, String secret){
                listener.onAccessTokenGot(token, secret);
                dialog.dismiss();
                setOAuthVersion(context, OAuthVersion.VER_1);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
                dialog.dismiss();
            }
        });
        loginHelper.loginByOauth1(webView);
    }

    public static void oAuth2Login(final Context context, final OnAccessTokenGotListener listener){
        WebView webView = new WebView(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(webView)
                .create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        String clientId=context.getString(R.string.consumer_key);
        OAuthLoginHelper helper=OAuthLoginHelper.newAoth2LoginHelper(clientId, context.getString(R.string.consumer_secret), URL_OAUTH2_AUTH, URL_OAUTH2_GRANT, getRedirectUri(clientId), new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {
                dialog.dismiss();
            }

            @Override
            public void onVerify() {

            }

            @Override
            public void onAccessTokenGot(String token, String secret) {
                dialog.dismiss();
                listener.onAccessTokenGot(token, secret);
                setOAuthVersion(context, OAuthVersion.VER_2);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
                dialog.dismiss();
            }
        });
        helper.loginByOauth2(webView);
    }

    private static String getRedirectUri(String clientId) {
        return "pixnetapi-"+clientId+"://callback";
    }

    public static OAuthVersion getOAuthVersion(Context c){
        return Helper.getPrefInt(c, "oauthVer", 2)==1?
                OAuthVersion.VER_1 : OAuthVersion.VER_2;
    }

    public static void setOAuthVersion(Context c, OAuthVersion ver){
        Helper.putPrefInt(c, "oauthVer", ver==OAuthVersion.VER_1?1:2);
    }

    public interface OnAccessTokenGotListener{
        void onAccessTokenGot(String token, String secret);
        void onError(String msg);
    }

}
