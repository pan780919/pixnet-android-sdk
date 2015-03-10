package net.pixnet.sdk;

import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;
import android.webkit.WebView;

import net.pixnet.sdk.proxy.DataProxy.DataProxyListener;
import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.OAuthConnectionTool.OAuthVersion;
import net.pixnet.sdk.utils.OAuthLoginHelper;
import net.pixnet.sdk.utils.PixnetApiHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class PIXNET {
    private static final String URL_OAUTH2_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_OAUTH2_GRANT = "https://emma.pixnet.cc/oauth2/grant";
    private static final String URL_OAUTH1_REQUEST = "http://emma.pixnet.cc/oauth/request_token";
    private static final String URL_OAUTH1_ACCESS = "http://emma.pixnet.cc/oauth/access_token";

    public static PixnetApiHelper getApiHelper(Context c, DataProxyListener listener){
        PixnetApiHelper helper = new PixnetApiHelper();
        helper.setContext(c);
        helper.setListener(listener);
        return helper;
    }

    public static void oAuth1Login(final Context context, final OnAccessTokenGotListener listener) {
        WebView webView = new WebView(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(webView)
                .create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        OAuthLoginHelper loginHelper = OAuthLoginHelper.newAuth1LoginHelper(getConsumerKey(context), getConsumerSecret(context), URL_OAUTH1_REQUEST, URL_OAUTH1_ACCESS, new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {}

            @Override
            public void onVerify() {}

            @Override
            public void onAccessTokenGot(String token, String secret) {
                listener.onAccessTokenGot(token, secret);
                dialog.dismiss();
                setOauthVersion(context, OAuthVersion.VER_1);
                setOauthAccessTokenAndSecret(context, token, secret);
            }

            @Override
            public void onAccessTokenGot(String token, String refreshToken, int expires) {}

            @Override
            public void onError(String msg) {
                listener.onError(msg);
                dialog.dismiss();
            }
        });
        loginHelper.loginByOauth1(webView);
    }

    public static void oAuth2Login(final Context context, final OnAccessTokenGotListener listener) {
        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("login_theme", "mobileapp"));
        oAuth2Login(context, params, listener);
    }

    public static void oAuth2OpenIdLogin(final Context context, final OnAccessTokenGotListener listener) {
        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("login_theme", "mobileapp_openid"));
        oAuth2Login(context, params, listener);
    }

    private static void oAuth2Login(final Context context, List<NameValuePair> theme, final OnAccessTokenGotListener listener) {
        WebView webView = new WebView(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(webView)
                .create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        String clientId = getConsumerKey(context);
        String clientSecret = getConsumerSecret(context);
        OAuthLoginHelper helper = OAuthLoginHelper.newAuth2LoginHelper(clientId, clientSecret, URL_OAUTH2_AUTH, URL_OAUTH2_GRANT, getRedirectUri(clientId), new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {
                dialog.dismiss();
            }

            @Override
            public void onVerify() {

            }

            @Override
            public void onAccessTokenGot(String token, String secret) {
            }

            @Override
            public void onAccessTokenGot(String token, String refreshToken, int expires) {
                dialog.dismiss();
                listener.onAccessTokenGot(token, refreshToken, expires);
                setOauthVersion(context, OAuthVersion.VER_2);
                setOauthAccessToken(context, token);
                setOauthRefreshToken(context, refreshToken);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
                dialog.dismiss();
            }
        });
        helper.loginByOauth2(webView, theme);
    }

    public static void xAuthLogin(final Context context, final OnAccessTokenGotListener listener, String name, String passwd) {
        OAuthLoginHelper loginHelper = OAuthLoginHelper.newXAuthLoginHelper(getConsumerKey(context), getConsumerSecret(context), URL_OAUTH1_ACCESS, new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {
                Helper.log("onRequestUrlGot");
            }

            @Override
            public void onVerify() {
                Helper.log("onVerify");
            }

            @Override
            public void onAccessTokenGot(String token, String secret) {
                Helper.log("onAccessTokenGot");
                listener.onAccessTokenGot(token, secret);
                setOauthVersion(context, OAuthVersion.VER_1);
                setOauthAccessTokenAndSecret(context, token, secret);
            }

            @Override
            public void onAccessTokenGot(String token, String refreshToken, int expires) {}

            @Override
            public void onError(String msg) {
                listener.onError(msg);
            }
        });
        loginHelper.loginByXauth(name, passwd);
    }

    public static void refreshToken(String refreshToken, final Context context, final OnAccessTokenGotListener listener){
        String clientId = getConsumerKey(context);
        String clientSecret = getConsumerSecret(context);
        OAuthLoginHelper helper = OAuthLoginHelper.newAuth2RefreshHelper(clientId, clientSecret, URL_OAUTH2_GRANT, new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {}

            @Override
            public void onVerify() {}

            @Override
            public void onAccessTokenGot(String token, String secret) {}

            @Override
            public void onAccessTokenGot(String token, String refreshToken, int expires) {
                listener.onAccessTokenGot(token, refreshToken, expires);
                setOauthVersion(context, OAuthVersion.VER_2);
                setOauthAccessToken(context, token);
                setOauthRefreshToken(context, refreshToken);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
            }
        });
        helper.refreshAccessToken(refreshToken);
    }

    public static void logout(Context c) {
        Helper.putPrefString(c, "accessSecret", null);
        Helper.putPrefString(c, "accessToken", null);
    }

    private static String getRedirectUri(String clientId) {
        return "pixnetapi-" + clientId + "://callback";
    }

    public static OAuthVersion getOAuthVersion(Context c) {
        return Helper.getPrefInt(c, "oauthVer", 2) == 1 ?
                OAuthVersion.VER_1 : OAuthVersion.VER_2;
    }

    public static void setOauthVersion(Context c, OAuthVersion ver) {
        Helper.putPrefInt(c, "oauthVer", ver == OAuthVersion.VER_1 ? 1 : 2);
    }

    public static void setOauthAccessTokenAndSecret(Context c, String token, String secret) {
        setOauthAccessToken(c, token);
        Helper.putPrefString(c, "accessSecret", secret);
    }

    public static void setOauthAccessToken(Context c, String token) {
        Helper.putPrefString(c, "accessToken", token);
    }

    public static void setOauthRefreshToken(Context c, String token) {
        Helper.putPrefString(c, "refreshToken", token);
    }

    public static String getConsumerKey(Context c) {
        return c.getString(R.string.consumer_key);
    }

    public static String getConsumerSecret(Context c) {
        return c.getString(R.string.consumer_secret);
    }

    public static String getOauthAccessToken(Context c) {
        return Helper.getPrefString(c, "accessToken", null);
    }

    public static String getOauthRefreshToken(Context c) {
        return Helper.getPrefString(c, "refreshToken", null);
    }

    public static String getOauthAccessSecret(Context c) {
        return Helper.getPrefString(c, "accessSecret", null);
    }

    public static boolean isLogin(Context c) {
        if (getOauthAccessToken(c) == null)
            return false;
        else return true;
    }

    public interface OnAccessTokenGotListener {
        /**
         * on OAuth 1.0/X Access Token got
         * @param token
         * @param secret
         */
        void onAccessTokenGot(String token, String secret);

        /**
         * on OAuth 2.0 Access Token got
         * @param token
         * @param refreshToken
         * @param expires 多少時間後將會過期，以秒為單位
         */
        void onAccessTokenGot(String token, String refreshToken, int expires);

        void onError(String msg);
    }

}
