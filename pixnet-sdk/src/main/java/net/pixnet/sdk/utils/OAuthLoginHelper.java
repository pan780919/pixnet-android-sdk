package net.pixnet.sdk.utils;

import android.net.Uri;
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

    private OAuthLoginListener listener;
    private WebView webView;
    private String accessUrl;
    private String redirect_uri="http://oob";

    public static OAuthLoginHelper newAoth1LoginHelper(String consumerKey, String consumerSecret, String requestUrl, String accessUrl, OAuthLoginListener listener){
        OAuthLoginHelper helper=new OAuthLoginHelper();
        helper.key =consumerKey;
        helper.secret =consumerSecret;
        helper.oauth1Url_request=requestUrl;
        helper.oauth1Url_access=accessUrl;
        helper.listener=listener;
        return helper;
    }

    public static OAuthLoginHelper newAoth2LoginHelper(String clientId, String clientSecret, String authUrl, String grantUrl, String redirectUri, OAuthLoginListener listener){
        OAuthLoginHelper helper=new OAuthLoginHelper();
        helper.key =clientId;
        helper.secret =clientSecret;
        helper.oauth2Url_auth=authUrl;
        helper.oauth2Url_grant=grantUrl;
        helper.setRedirectUri(redirectUri);
        helper.listener=listener;
        return helper;
    }

    public void setRedirectUri(String uri){
        redirect_uri=uri;
    }

    public void loginByXauth(String userName, String passwd, String accessTokenUrl, final OAuthLoginListener listener) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("x_auth_mode", "client_auth"));
        params.add(new BasicNameValuePair("x_auth_password", passwd));
        params.add(new BasicNameValuePair("x_auth_username", userName));

        Request request = new Request(accessTokenUrl);
        request.setMethod(Request.Method.POST);
        request.setParams(params);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                HashMap<String, String> resParams = HttpConnectionTool.parseParamsByResponse(response);
                String token = resParams.get("oauth_token");
                String secret = resParams.get("oauth_token_secret");
                listener.onAccessTokenGot(token, secret);
            }
        });
        RequestController rc = RequestController.getInstance();
        rc.setHttpConnectionTool(getOAuthConnectionTool(OAuthVersion.VER_1));
        rc.addRequest(request);
    }
    public void loginByOauth1(WebView view){
        webView=view;
        getUrlForOauth1Request();
    }

    public void loginByOauth2(WebView view){
        webView=view;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri=Uri.parse(url);
                String error=uri.getQueryParameter("error");
                if(error!=null){
                    listener.onError(error);
                    return true;
                }
                if(url.startsWith(redirect_uri)){
                    String code=uri.getQueryParameter("code");
                    if (code!=null) {
                        webView.setWebViewClient(null);
                        listener.onRequestUrlGot();
                        getOauth2AccessToken(code);
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl(getOauth2RequestUrl());
    }

    public void getUrlForOauth1Request(){
        Request request = new Request(oauth1Url_request);
        request.setCallback(new Request.RequestCallback() {
            @Override
            public void onResponse(String response) {
                HashMap<String, String> map= HttpConnectionTool.parseParamsByResponse(response);
                oauthToken=map.get("oauth_token");
                oauthSecret=map.get("oauth_token_secret");
                String url=map.get("xoauth_request_auth_url");
                accessUrl= HttpConnectionTool.decodeUrl(url);
                listener.onRequestUrlGot();

                if(webView!=null){
                    oauth1Verify();
                }
            }
        });

        RequestController rc=RequestController.getInstance();
        rc.setHttpConnectionTool(getOAuthConnectionTool(OAuthVersion.VER_1));
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
                    listener.onError(error);
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
        listener.onVerify();
    }

    public class Jsi{
        @SuppressWarnings("unused")
        @JavascriptInterface
        public void access(String verifier){
            getOauth1AccessToken(verifier);
            webView.setWebViewClient(null);
        }
    }

    public void getOauth1AccessToken(String verifier) {
        OAuthConnectionTool oauthHelper= getOAuthConnectionTool(OAuthVersion.VER_1);
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
                listener.onAccessTokenGot(access_token, token_secret);
            }
        });

        RequestController rc=RequestController.getInstance();
        rc.setHttpConnectionTool(oauthHelper);
        rc.addRequest(r);
    }

    /**
     * @param code Code from RequestUrl
     * @return Set code and Get AccessToken
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
                    listener.onAccessTokenGot(job.getString("access_token"), "OAuth2");
                }catch(JSONException e){
                    e.printStackTrace();
                    listener.onError(e.getMessage());
                }
            }
        });
        RequestController rc = RequestController.getInstance();
        rc.setHttpConnectionTool(getOAuthConnectionTool(OAuthVersion.VER_2));
        rc.addRequest(request);
    }

    private OAuthConnectionTool getOAuthConnectionTool(OAuthVersion version) {
        OAuthConnectionTool tool;
        switch (version){
            case VER_1:
                tool=OAuthConnectionTool.newOaut1ConnectionTool(key, secret);
                break;
            case VER_2:
                tool=OAuthConnectionTool.newOauth2ConnectionTool();
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
    private String getOauth2RequestUrl() {
        return oauth2Url_auth
                + "?client_id=" + key
                + "&redirect_uri=" + redirect_uri
                + "&response_type=code";
    }

    public interface OAuthLoginListener {
        void onRequestUrlGot();
        void onVerify();
        void onAccessTokenGot(String token, String secret);
        void onError(String msg);
    }
}
