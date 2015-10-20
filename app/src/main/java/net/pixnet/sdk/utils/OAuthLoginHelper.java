package net.pixnet.sdk.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pixnet.sdk.utils.OAuthConnectionTool.OAuthVersion;

public class OAuthLoginHelper {

    private String xauthUrl_access;
    private String oauth1Url_request;
    private String oauth1Url_access;
    private String oauth2Url_auth;
    private String oauth2Url_grant;
    private String key;
    private String secret;
    private String oauthToken;
    private String oauthSecret;
    private String access_token;
    private String token_secret;

    private OAuth1LoginListener listener1;
    private OAuth2LoginListener listener2;
    private WebView webView;
    private String accessUrl;
    private String redirect_uri="http://oob";

    public static OAuthLoginHelper newXAuthLoginHelper(String consumerKey, String consumerSecret, String accessUrl, OAuth1LoginListener listener){
        OAuthLoginHelper helper=new OAuthLoginHelper();
        helper.key =consumerKey;
        helper.secret =consumerSecret;
        helper.xauthUrl_access=accessUrl;
        helper.listener1=listener;
        return helper;
    }

    public static OAuthLoginHelper newAuth1LoginHelper(String consumerKey, String consumerSecret, String requestUrl, String accessUrl, OAuth1LoginListener listener){
        OAuthLoginHelper helper=new OAuthLoginHelper();
        helper.key =consumerKey;
        helper.secret =consumerSecret;
        helper.oauth1Url_request=requestUrl;
        helper.oauth1Url_access=accessUrl;
        helper.listener1=listener;
        return helper;
    }

    public static OAuthLoginHelper newAuth2LoginHelper(String clientId, String clientSecret, String authUrl, String grantUrl, String redirectUri, OAuth2LoginListener listener){
        OAuthLoginHelper helper=new OAuthLoginHelper();
        helper.key =clientId;
        helper.secret =clientSecret;
        helper.oauth2Url_auth=authUrl;
        helper.oauth2Url_grant=grantUrl;
        helper.setRedirectUri(redirectUri);
        helper.listener2=listener;
        return helper;
    }

    public static OAuthLoginHelper newAuth2RefreshHelper(String clientId, String clientSecret, String grantUrl, OAuth2LoginListener listener){
        OAuthLoginHelper helper=new OAuthLoginHelper();
        helper.key =clientId;
        helper.secret =clientSecret;
        helper.oauth2Url_grant=grantUrl;
        helper.listener2=listener;
        return helper;
    }

    public void setRedirectUri(String uri){
        redirect_uri=uri;
    }

    public void loginByXauth(String userName, String passwd) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("x_auth_mode", "client_auth"));
        params.add(new BasicNameValuePair("x_auth_password", passwd));
        params.add(new BasicNameValuePair("x_auth_username", userName));

        List<FileNameValuePair> files=new ArrayList<FileNameValuePair>();

        Request request = new Request(xauthUrl_access);
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                HashMap<String, String> resParams = HttpConnectionTool.parseParamsByResponse(response);
                String token = resParams.get("oauth_token");
                String secret = resParams.get("oauth_token_secret");
                listener1.onAccessTokenGot(token, secret);
            }
        });
        RequestController rc = RequestController.getInstance();
        rc.setHttpConnectionTool(getConnectionTool(OAuthVersion.VER_1));
        rc.addRequest(request);
    }

    public void loginByOauth1(WebView view){
        webView=view;
        getUrlForOauth1Request();
    }

    public void loginByOauth2(WebView view){
        loginByOauth2(view, null);
    }

    public void loginByOauth2(WebView view, List<NameValuePair> params){
        webView=view;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri=Uri.parse(url);
                String error=uri.getQueryParameter("error");
                if(error!=null){
                    listener2.onError(error);
                    return true;
                }
                if(url.startsWith(redirect_uri)){
                    String code=uri.getQueryParameter("code");
                    if (code!=null) {
                        webView.setWebViewClient(null);
                        getOauth2AccessToken(code);
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl(getOauth2RequestUrl(params));
    }

    public void getUrlForOauth1Request(){
        String url=oauth1Url_request;
        Request request = new Request(url);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                Helper.log("on response");
                Helper.log(response);
                HashMap<String, String> map= HttpConnectionTool.parseParamsByResponse(response);
                oauthToken=map.get("oauth_token");
                oauthSecret=map.get("oauth_token_secret");
                String url=map.get("xoauth_request_auth_url");
                accessUrl= HttpConnectionTool.decodeUrl(url);
                listener1.onRequestUrlGot();

                if(webView!=null){
                    oauth1Verify();
                }
            }
        });

        RequestController rc=RequestController.getInstance();
        rc.setHttpConnectionTool(getConnectionTool(OAuthVersion.VER_1));
        rc.addRequest(request);
    }

    public void oauth1Verify(){
        if(accessUrl==null || webView==null)
            return;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Jsi(), "android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri=Uri.parse(url);
                String error=uri.getQueryParameter("error");
                if(error!=null){
                    listener1.onError(error);
                    return true;
                }
                if(url.startsWith(redirect_uri)){
                    String verifier = uri.getQueryParameter("oauth_verifier");
                    if (verifier!=null) {
                        webView.setWebViewClient(null);
                        getOauth1AccessToken(verifier);
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:window.android.access(document.getElementById('oauth_verifier').innerHTML);");
            }
        });
        webView.loadUrl(accessUrl);
        listener1.onVerify();
    }

    public class Jsi{
        @SuppressWarnings("unused")
        @JavascriptInterface
        public void access(String verifier){
            if(verifier.length()==6)
                getOauth1AccessToken(verifier);
        }
    }

    public void getOauth1AccessToken(String verifier) {
        OAuthConnectionTool oauthHelper= (OAuthConnectionTool) getConnectionTool(OAuthVersion.VER_1);
        oauthHelper.setAccessTokenAndSecret(oauthToken, oauthSecret);

        Request r=new Request(oauth1Url_access);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("oauth_verifier", verifier));
        r.setParams(params);
        r.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                HashMap<String, String> map = HttpConnectionTool.parseParamsByResponse(response);
                access_token = map.get("oauth_token");
                token_secret = map.get("oauth_token_secret");
                listener1.onAccessTokenGot(access_token, token_secret);
            }
        });

        RequestController rc=RequestController.getInstance();
        rc.setHttpConnectionTool(oauthHelper);
        rc.addRequest(r);
    }

    /**
     * @param code Code from RequestUrl
     */
    public void getOauth2AccessToken(String code) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        params.add(new BasicNameValuePair("client_id", key));
        params.add(new BasicNameValuePair("client_secret", secret));

        Request request = new Request(oauth2Url_grant);
        request.setParams(params);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    String accessToken=job.getString("access_token");
                    String refreshToken=job.getString("refresh_token");
                    int expires=job.getInt("expires_in");
                    listener2.onAccessTokenGot(accessToken, refreshToken, expires);
                }catch(JSONException e){
                    e.printStackTrace();
                    listener2.onError(e.getMessage());
                }
            }
        });
        RequestController rc = RequestController.getInstance();
        rc.setHttpConnectionTool(getConnectionTool(OAuthVersion.VER_2));
        rc.addRequest(request);
    }

    /**
     * 在 access_token expire 之前要記得 refresh token.
     * @param refreshToken 在 Grant:create refresh token 階段拿到的 refresh_token. 如果有使用過 PIXNET OAuth1.0a,則可拿在那時用到的 access_token 來使用
     */
    public void refreshAccessToken(String refreshToken){
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "refresh_token"));
        params.add(new BasicNameValuePair("refresh_token", refreshToken));
        params.add(new BasicNameValuePair("client_id", key));
        params.add(new BasicNameValuePair("client_secret", secret));

        Request request = new Request(oauth2Url_grant);
        request.setParams(params);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                JSONObject job;
                try {
                    job = new JSONObject(response);
                    String error=job.getString("error");
                    if(!TextUtils.isEmpty(error)){
                        listener2.onError(error);
                    }
                    listener2.onAccessTokenGot(job.getString("access_token"), job.getString("refresh_token"), job.getInt("expires_in"));
                }catch(JSONException e){
                    e.printStackTrace();
                    listener2.onError(e.getMessage());
                }
            }
        });
        RequestController rc = RequestController.getInstance();
        rc.setHttpConnectionTool(getConnectionTool(OAuthVersion.VER_2));
        rc.addRequest(request);
    }

    private ConnectionTool getConnectionTool(OAuthVersion version) {
        ConnectionTool tool;
        switch (version){
            case VER_1:
                tool=OAuthConnectionTool.newOaut1ConnectionTool(key, secret);
                break;
            case VER_2:
                tool=new HttpConnectionTool();
                break;
            default:
                tool=null;
        }
        return tool;
    }

    /**
     * The url that can auth and return the code for getOauth1AccessToken token
     *
     * @return formatted request url
     */
    private String getOauth2RequestUrl(List<NameValuePair> params) {
        String url = oauth2Url_auth
                + "?client_id=" + key
                + "&redirect_uri=" + redirect_uri
                + "&response_type=code";

        if(params!=null){
            for(NameValuePair p : params){
                url+="&"+p.getName()+"="+p.getValue();
            }
        }

        return url;
    }

    public interface OAuth1LoginListener {
        void onRequestUrlGot();
        void onVerify();
        void onAccessTokenGot(String token, String secret);
        void onError(String msg);
    }

    public interface OAuth2LoginListener {
        void onAccessTokenGot(String token, String refreshToken, long expires);
        void onError(String msg);
    }
}
