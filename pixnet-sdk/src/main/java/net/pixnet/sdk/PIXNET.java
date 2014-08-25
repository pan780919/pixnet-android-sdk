package net.pixnet.sdk;

import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;
import android.webkit.WebView;

import net.pixnet.sdk.utils.AccountHelper;
import net.pixnet.sdk.utils.AlbumHelper;
import net.pixnet.sdk.utils.BlockHelper;
import net.pixnet.sdk.utils.BlogHelper;
import net.pixnet.sdk.utils.FriendHelper;
import net.pixnet.sdk.utils.GuestBookHelper;
import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.OAuthLoginHelper;
import net.pixnet.sdk.utils.OAuthConnectionTool.OAuthVersion;
import net.pixnet.sdk.proxy.DataProxy.DataProxyListener;

public class PIXNET {
    private static final String URL_OAUTH2_AUTH = "https://emma.pixnet.cc/oauth2/authorize";
    private static final String URL_OAUTH2_GRANT = "https://emma.pixnet.cc/oauth2/grant";
    private static final String URL_OAUTH1_REQUEST = "http://emma.pixnet.cc/oauth/request_token";
    private static final String URL_OAUTH1_ACCESS = "http://emma.pixnet.cc/oauth/access_token";

    /**
     * 取得 FriendHelper 來存取痞客邦與好友相關的 API
     * @param c Context
     * @param listener 使用 {@link net.pixnet.sdk.utils.FriendHelperListener} 獲得格式化的資訊，或使用 {@link net.pixnet.sdk.proxy.DataProxy.DataProxyListener} 自行處理原始資料
     * @return {@link net.pixnet.sdk.utils.FriendHelper}
     */
    public static FriendHelper getFriendHelper(Context c, DataProxyListener listener) {
        FriendHelper friendHelper = new FriendHelper();
        friendHelper.setContext(c);
        friendHelper.setListener(listener);
        return friendHelper;
    }

    /**
     * 取得 GuestBookHelper 來存取痞客邦與留言板相關的 API
     * @param c Context
     * @param listener 使用 {@link net.pixnet.sdk.utils.GuestBookHelperListener} 獲得格式化的資訊，或使用 {@link net.pixnet.sdk.proxy.DataProxy.DataProxyListener} 自行處理原始資料
     * @return {@link net.pixnet.sdk.utils.GuestBookHelper}
     */
    public static GuestBookHelper getGuestBookHelper(Context c, DataProxyListener listener) {
        GuestBookHelper guestBookHelper = new GuestBookHelper();
        guestBookHelper.setContext(c);
        guestBookHelper.setListener(listener);
        return guestBookHelper;
    }

    /**
     * 取得 BlockHelper 來存取痞客邦與黑名單相關的 API
     * @param c Context
     * @param listener 使用 {@link net.pixnet.sdk.utils.BlockHelperListener} 獲得格式化的資訊，或使用 {@link net.pixnet.sdk.proxy.DataProxy.DataProxyListener} 自行處理原始資料
     * @return {@link net.pixnet.sdk.utils.BlockHelper}
     */
    public static BlockHelper getBlockHelper(Context c, DataProxyListener listener) {
        BlockHelper blockHelper = new BlockHelper();
        blockHelper.setContext(c);
        blockHelper.setListener(listener);
        return blockHelper;
    }

    /**
     * 取得 BlogHelper 來存取痞客邦與部落格相關的 API
     * @param c Context
     * @param listener 使用 {@link net.pixnet.sdk.utils.BlogHelperListener} 獲得格式化的資訊，或使用 {@link net.pixnet.sdk.proxy.DataProxy.DataProxyListener} 自行處理原始資料
     * @return {@link net.pixnet.sdk.utils.BlockHelper}
     */
    public static BlogHelper getBlogHelper(Context c, String name, DataProxyListener listener) {
        BlogHelper blogHelper = new BlogHelper();
        blogHelper.setContext(c);
        blogHelper.setDefaultUserName(name);
        blogHelper.setListener(listener);
        return blogHelper;
    }

    /**
     * 取得 AlbumHelper 來存取痞客邦與相簿相關的 API
     * @param c Context
     * @param listener 使用 {@link net.pixnet.sdk.utils.AlbumHelperListener} 獲得格式化的資訊，或使用 {@link net.pixnet.sdk.proxy.DataProxy.DataProxyListener} 自行處理原始資料
     * @return {@link net.pixnet.sdk.utils.AlbumHelper}
     */
    public static AlbumHelper getAlbumHelper(Context c, String name, DataProxyListener listener) {
        AlbumHelper albumHelper = new AlbumHelper();
        albumHelper.setContext(c);
        albumHelper.setDefaultUserName(name);
        albumHelper.setListener(listener);
        return albumHelper;
    }

    /**
     * 取得 AccountHelper 來存取痞客邦與使用者帳號相關的 API
     * @param c Context
     * @param listener 使用 {@link net.pixnet.sdk.utils.AccountHelperListener} 獲得格式化的資訊，或使用 {@link net.pixnet.sdk.proxy.DataProxy.DataProxyListener} 自行處理原始資料
     * @return {@link net.pixnet.sdk.utils.AccountHelperListener}
     */
    public static AccountHelper getAccountHelper(Context c, DataProxyListener listener) {
        AccountHelper account = new AccountHelper();
        account.setContext(c);
        account.setListener(listener);
        return account;
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
            public void onRequestUrlGot() {
            }

            @Override
            public void onVerify() {
            }

            @Override
            public void onAccessTokenGot(String token, String secret) {
                listener.onAccessTokenGot(token, secret);
                dialog.dismiss();
                setOauthVersion(context, OAuthVersion.VER_1);
                setOauthAccessTokenAndSecret(context, token, secret);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
                dialog.dismiss();
            }
        });
        loginHelper.loginByOauth1(webView);
    }

    public static void oAuth2Login(final Context context, final OnAccessTokenGotListener listener) {
        WebView webView = new WebView(context);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(webView)
                .create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        String clientId = getConsumerKey(context);
        OAuthLoginHelper helper = OAuthLoginHelper.newAuth2LoginHelper(clientId, getConsumerSecret(context), URL_OAUTH2_AUTH, URL_OAUTH2_GRANT, getRedirectUri(clientId), new OAuthLoginHelper.OAuthLoginListener() {
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
                setOauthVersion(context, OAuthVersion.VER_2);
                setOauthAccessToken(context, token);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
                dialog.dismiss();
            }
        });
        helper.loginByOauth2(webView);
    }

    public static void xAuthLogin(final Context context, final OnAccessTokenGotListener listener, String name, String passwd) {
        OAuthLoginHelper loginHelper = OAuthLoginHelper.newXAuthLoginHelper(getConsumerKey(context), getConsumerSecret(context), URL_OAUTH1_ACCESS, new OAuthLoginHelper.OAuthLoginListener() {
            @Override
            public void onRequestUrlGot() {
            }

            @Override
            public void onVerify() {
            }

            @Override
            public void onAccessTokenGot(String token, String secret) {
                listener.onAccessTokenGot(token, secret);
                setOauthVersion(context, OAuthVersion.VER_1);
                setOauthAccessTokenAndSecret(context, token, secret);
            }

            @Override
            public void onError(String msg) {
                listener.onError(msg);
            }
        });
        loginHelper.loginByXauth(name, passwd);
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

    public static String getConsumerKey(Context c) {
        return c.getString(R.string.consumer_key);
    }

    public static String getConsumerSecret(Context c) {
        return c.getString(R.string.consumer_secret);
    }

    public static String getOauthAccessToken(Context c) {
        return Helper.getPrefString(c, "accessToken", null);
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
        void onAccessTokenGot(String token, String secret);

        void onError(String msg);
    }

}
